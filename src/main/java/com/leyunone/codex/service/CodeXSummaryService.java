package com.leyunone.codex.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.control.SystemController;
import com.leyunone.codex.dao.*;
import com.leyunone.codex.dao.entry.Branches;
import com.leyunone.codex.dao.entry.ProjectUser;
import com.leyunone.codex.dao.entry.Storage;
import com.leyunone.codex.dao.mapper.CommitMapper;
import com.leyunone.codex.model.ResponseCell;
import com.leyunone.codex.model.bo.BranchesBO;
import com.leyunone.codex.model.bo.CommitBO;
import com.leyunone.codex.model.bo.ProjectBO;
import com.leyunone.codex.model.bo.UserBO;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.Project;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 代码统计总结服务
 */
@Service
public class CodeXSummaryService {

    public static final Logger logger = LoggerFactory.getLogger(CodeXSummaryService.class);

    private final ProjectDao projectDao;
    private final BranchesDao branchesDao;
    private final UserDao userDao;
    private final ProjectUserDao projectUserDao;
    private final SqlSessionTemplate sqlSessionTemplate;
    private final StorageDao storageDao;

    public CodeXSummaryService(ProjectDao projectDao, BranchesDao branchesDao, UserDao userDao, ProjectUserDao projectUserDao, SqlSessionTemplate sqlSessionTemplate, StorageDao storageDao) {
        this.projectDao = projectDao;
        this.branchesDao = branchesDao;
        this.userDao = userDao;
        this.projectUserDao = projectUserDao;
        this.sqlSessionTemplate = sqlSessionTemplate;
        this.storageDao = storageDao;
    }


    private Storage getStorage(String url) {
        Storage storage = new Storage();
        storage.setStorageUrl(url);
        storage.setStorageName(url);
        List<Storage> storages = storageDao.selectByUrl(url);
        if (CollectionUtil.isEmpty(storages)) {
            return storageDao.saveStorage(storage);
        }
        return storages.get(0);
    }

    /**
     * 全统计 项目 分支 每次提交
     */
    public void summaryCodeX(String url,String startTime) {
        SystemController.status = 1;
        //拿到仓库的编号
        Storage storage = this.getStorage(url);
        if(StringUtils.isBlank(storage.getToken())){
            return;
        }
        GitLabAPIService gitLabAPIService = GitLabAPIService.buildGitApiService(new GitLabApi(url, storage.getToken()));

        //当前可见所有项目
        ResponseCell<List<ProjectBO>, List<Project>> responseCell = gitLabAPIService.resoleProjects();
        this.projectNew(responseCell.getDataBO(), storage);

        Map<Integer, List<BranchesBO>> projectTobranches = new HashMap<>();
        List<Branches> branches = new ArrayList<>();
        for (Project project : responseCell.getDataApi()) {
            List<BranchesBO> branchesBOS = gitLabAPIService.resoleBranches(project);
            branchesBOS.forEach((t) -> {
                Branches build = Branches.builder()
                        .branchesId(project.getId() + "#" + storage.getStorageId() + "#" + t.getBranchName())
                        .projectId(String.valueOf(project.getId())).branchesName(t.getBranchName()).build();
                branches.add(build);
            });
            projectTobranches.put(project.getId(), branchesBOS);
        }

        //TODO 分支
        this.branchesNew(branches, storage);

        Map<String, UserBO> userMap = new ConcurrentHashMap<>();
        Map<String, ProjectUser> projectUserMap = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<List<Integer>> partition = ListUtil.partition(CollectionUtil.newArrayList(projectTobranches.keySet()), 8);
        final CountDownLatch latch = new CountDownLatch(partition.size());
        for(List<Integer> threadProjectIds : partition){
            executorService.submit(() -> {
                for (Integer projectId : threadProjectIds) {
                    logger.info( " ======== {}, is start get Commit,startTime:{}",projectId,startTime);

                    List<CommitBO> commitBOList = gitLabAPIService.resoleCommits(projectId, null, startTime);
                    commitBOList.forEach(commitBO-> {
                        commitBO.setProjectId(String.valueOf(projectId));
                        commitBO.setStorageId(storage.getStorageId());
                        String userId = commitBO.getUserName() + "#" + storage.getStorageId();
                        //遍历提交记录时，绑定项目和成员的关系
                        if (!projectUserMap.containsKey(projectId + "#" + commitBO.getUserName())) {
                            ProjectUser projectUser = new ProjectUser();
                            projectUser.setId(commitBO.getUserName() + "#" + projectId);
                            projectUser.setProjectId(String.valueOf(projectId));
                            projectUser.setUserId(userId);
                            projectUserMap.put(projectId + "#" + commitBO.getUserName(), projectUser);
                        }

                        //累加用户
                        if (userMap.containsKey(commitBO.getUserName())) {
                        } else {
                            UserBO userBO = new UserBO();
                            userBO.setUserId(userId);
                            userBO.setUserEmail(commitBO.getCommitterEmail());
                            userBO.setUserName(commitBO.getUserName());
                            userBO.setStorageId(storage.getStorageId());

                            userMap.put(userBO.getUserName(), userBO);
                        }
                    });
                    commitNew(commitBOList, storage);
                }
                latch.countDown();
                logger.info("==================>>> TTTTTTTTT thread <<<<<====================");
                logger.info("====================="+latch.getCount()+"=======================");
                logger.info("==================>>> TTTTTTTTT thread <<<<<====================");
            });
        }
        logger.info("==================>>> wait main thread <<<<<====================");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ;
        this.userNew(CollectionUtil.newArrayList(userMap.values()));
//        //全量同步提交记录
//        this.commitNew(commits, storage);
        this.projectUserNew(CollectionUtil.newArrayList(projectUserMap.values()), storage);
        SystemController.status = 0;
    }

    /**
     * 全量更新项目 非事务
     *
     * @param projects
     */
    private void projectNew(List<ProjectBO> projects, Storage storage) {
        logger.info(" =========== projects start add,{}",projects.size());
        //重命名项目id
        projects.forEach((t) -> {
            t.setProjectId(t.getProjectId() + "#" + storage.getStorageId());
            t.setStorageId(storage.getStorageId());
        });
        try {
            projectDao.insertOrUpdateBatch(projects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("============= projects add success");
    }

    /**
     * 全量更新项目下分支 非事务
     *
     * @param branches
     */
    private void branchesNew(List<Branches> branches, Storage storage) {
        logger.info(" ================== branches start add,{}",branches.size());

        //重命名项目id
        branches.forEach((t) -> t.setProjectId(t.getProjectId() + "#" + storage.getStorageId()));
        try {
            branchesDao.insertOrUpdateBatch(branches);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(" ===================== branches add success");
    }

    /**
     * 全量更新所有人的提交状态 非事务
     *
     * @param users
     */
    private void userNew(List<UserBO> users) {
        logger.info(" ================= users start add,{}",users.size());
        try {
            userDao.saveUser(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(" ==================== users add success");
    }

    /**
     * 全量更新所有提交 非事务
     *
     * @param commitBOS
     */
    public void commitNew(List<CommitBO> commitBOS, Storage storage) {
        logger.info(" ==================== commit start add,{}",commitBOS.size());

        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        //可以执行批量操作的sqlSession
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            CommitMapper mapper = openSession.getMapper(CommitMapper.class);
            List<CommitBO> list = new ArrayList<>();
            for (CommitBO commitBO : commitBOS) {
                //重命名项目id
                commitBO.setProjectId(commitBO.getProjectId() + "#" + storage.getStorageId());
                commitBO.setUserId(commitBO.getUserName() + "#" + storage.getStorageId());
                list.add(commitBO);
                if (list.size() == 1000) {
                    mapper.batchInsert(list);
                    openSession.commit();
                    list.clear();
                }
            }
            if (list.size() > 0) {
                mapper.batchInsert(list);
            }
            openSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            openSession.clearCache();
            openSession.close();
        }
        logger.info(" ====================== commit add success");
    }

    private void projectUserNew(List<ProjectUser> projectUsers, Storage storage) {
        logger.info(" ======================== ProjectUser start add,{}",projectUsers.size());

        //重命名项目id
        projectUsers.forEach((t) -> t.setProjectId(t.getProjectId() + "#" + storage.getStorageId()));
        try {
            projectUserDao.insertOrUpdateBatch(projectUsers);
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info(" ==================== ProjectUser add success");
    }
}
