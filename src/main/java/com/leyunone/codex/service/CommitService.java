package com.leyunone.codex.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.CommitDao;
import com.leyunone.codex.dao.RealUserRelationDao;
import com.leyunone.codex.dao.entry.Commit;
import com.leyunone.codex.model.query.CommitQuery;
import com.leyunone.codex.util.ProjectUtils;
import com.leyunone.codex.util.UserNameUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 16:17
 */
@Service
public class CommitService {

    private final CommitDao commitDao;
    private final RealUserRelationDao realUserRelationDao;

    public CommitService(CommitDao commitDao, RealUserRelationDao realUserRelationDao) {
        this.commitDao = commitDao;
        this.realUserRelationDao = realUserRelationDao;
    }

    public Page<Commit> queryCommitCodeX(CommitQuery query) {
        if (ObjectUtil.isNotNull(query.getRealUserId())) {
            List<String> userIds = realUserRelationDao.selectUserIdByRealUserId(query.getRealUserId());
            if (CollectionUtil.isEmpty(userIds)) {
                //为关联账号 空赋值
                userIds.add("-1");
            }
            query.setUserIds(userIds);
        }
        
        Page<Commit> commitPage = commitDao.selectByPage(query);
        List<Commit> records = commitPage.getRecords();

        Map<String, String> realUserNames = UserNameUtils.getUserRealNames(records.stream().map(Commit::getUserId).collect(Collectors.toList()));
        Map<String, String> projectNames = ProjectUtils.getProjectName();
        records.forEach((t) -> {
            if (realUserNames.containsKey(t.getUserId())) {
                t.setUserName(realUserNames.get(t.getUserId()));
            }
            t.setCommitDateStr(DateUtil.format(t.getCommitDate(), "yyyy-MM-dd HH:mm:ss"));
            t.setProjectName(projectNames.get(t.getProjectId()));
        });
        return commitPage;
    }
}
