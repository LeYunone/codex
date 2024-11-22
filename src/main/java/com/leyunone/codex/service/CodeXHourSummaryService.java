//package com.leyunone.codex.service;
//
//import com.leyunone.codex.dao.entry.Branches;
//import com.leyunone.codex.dao.entry.Commit;
//import com.leyunone.codex.dao.entry.ProjectUser;
//import com.leyunone.codex.dao.mapper.CommitMapper;
//import com.leyunone.codex.model.ResponseCell;
//import com.leyunone.codex.model.bo.BranchesBO;
//import com.leyunone.codex.model.bo.CommitBO;
//import com.leyunone.codex.model.bo.ProjectBO;
//import com.leyunone.codex.model.bo.UserBO;
//import com.leyunone.codex.dao.CommitDao;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.gitlab4j.api.GitLabApi;
//import org.gitlab4j.api.models.Project;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
///**
// * 代码统计总结服务
// */
//@Service
//public class CodeXHourSummaryService {
//
//    @Autowired
//    private CommitDao commitDao;
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//
//    /**
//     * 前置处理
//     */
//    public void preResole() {
//        List<Commit> commits = commitDao.selectByCon(null);
//    }
//
//    /**
//     * 全统计 项目 分支 每次提交
//     */
//    public void summaryCodeX(String url, String token) {
//        GitLabAPIService gitLabAPIService = GitLabAPIService.buildGitApiService(new GitLabApi(url, token));
//
//        //当前可见所有项目
//        ResponseCell<List<ProjectBO>, List<Project>> responseCell = gitLabAPIService.resoleProjects();
//
//        Map<Integer, List<BranchesBO>> projectTobranches = new HashMap<>();
//        List<Branches> branches = new ArrayList<>();
//        for (Project project : responseCell.getDataApi()) {
//            List<BranchesBO> branchesBOS = gitLabAPIService.resoleBranches(project);
//            branchesBOS.forEach((t) -> {
//                branches.add(Branches.builder().branchesId(project.getId() + "#" + t.getBranchName()).projectId(String.valueOf(project.getId())).branchesName(t.getBranchName()).build());
//            });
//            projectTobranches.put(project.getId(), branchesBOS);
//        }
//
//        //查找最后的提交时间
//        Date lastDate = commitDao.selectLastDate(url);
//
//        Map<String, UserBO> userMap = new HashMap<>();
//        Map<String, ProjectUser> projectUserMap = new HashMap<>();
//        List<CommitBO> commits = new ArrayList<>();
//        for (Integer projectId : projectTobranches.keySet()) {
//            List<BranchesBO> branchesBOS = projectTobranches.get(projectId);
//            for (BranchesBO branchesBO : branchesBOS) {
//                List<CommitBO> commitBOS = gitLabAPIService.resoleCommits(projectId, branchesBO.getBranchName(), lastDate);
//                commitBOS.forEach((t) -> t.setProjectId(String.valueOf(projectId)));
//                for (CommitBO commitBO : commitBOS) {
//                    //遍历提交记录时，绑定项目和成员的关系
//                    if (!projectUserMap.containsKey(projectId + "#" + commitBO.getCommitterName())) {
//                        ProjectUser projectUser = new ProjectUser();
//                        projectUser.setId(commitBO.getCommitterName() + "#" + projectId);
//                        projectUser.setProjectId(String.valueOf(projectId));
//                        projectUser.setUserName(commitBO.getCommitterName());
//                        projectUserMap.put(projectId + "#" + commitBO.getCommitterName(), projectUser);
//                    }
//
//                    //累加用户
//                    if (userMap.containsKey(commitBO.getCommitterName())) {
//                        UserBO userBO = userMap.get(commitBO.getCommitterName());
//                        userBO.setCodeAdditions(userBO.getCodeAdditions() + commitBO.getAdditions());
//                        userBO.setCodeDeletions(userBO.getCodeDeletions() + commitBO.getDeletions());
//                        userBO.setCodeTotal(userBO.getCodeTotal() + commitBO.getTotal());
//                    } else {
//                        UserBO userBO = new UserBO();
//                        userBO.setCodeAdditions(commitBO.getAdditions());
//                        userBO.setCodeDeletions(commitBO.getDeletions());
//                        userBO.setCodeTotal(commitBO.getTotal());
//                        userBO.setUserEmail(commitBO.getCommitterEmail());
//                        userBO.setUserName(commitBO.getCommitterName());
//                        userMap.put(userBO.getUserName(), userBO);
//                    }
//                }
//                commits.addAll(commitBOS);
//            }
//        }
//        //全量同步提交记录
//        this.commitNew(commits);
//    }
//
//
//    /**
//     * 全量更新所有提交 非事务
//     *
//     * @param commitBOS
//     */
//    private void commitNew(List<CommitBO> commitBOS) {
//        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
//        //可以执行批量操作的sqlSession
//        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
//        try {
//            CommitMapper mapper = openSession.getMapper(CommitMapper.class);
//            List<CommitBO> list = new ArrayList<>();
//            for (CommitBO commitBO : commitBOS) {
//                list.add(commitBO);
//                if (list.size() == 1000) {
//                    mapper.batchInsert(list);
//                    openSession.commit();
//                    list.clear();
//                }
//            }
//            if (list.size() > 0) {
//                mapper.batchInsert(list);
//            }
//            openSession.commit();
//        } finally {
//            openSession.clearCache();
//            openSession.close();
//        }
//    }
//}
