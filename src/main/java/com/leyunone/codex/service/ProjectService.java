package com.leyunone.codex.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.RealProjectRelationDao;
import com.leyunone.codex.dao.ProjectDao;
import com.leyunone.codex.dao.ProjectUserDao;
import com.leyunone.codex.dao.entry.RealProjectRelation;
import com.leyunone.codex.model.query.ProjectUserQuery;
import com.leyunone.codex.model.vo.ProjectUserVO;
import com.leyunone.codex.model.vo.ProjectVO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/9 14:57
 */
@Service
public class ProjectService {

    private final ProjectDao projectDao;
    private final ProjectUserDao projectUserDao;
    private final RealProjectRelationDao realProjectRelationDao;

    public ProjectService(ProjectDao projectDao, ProjectUserDao projectUserDao, RealProjectRelationDao realProjectRelationDao) {
        this.projectDao = projectDao;
        this.projectUserDao = projectUserDao;
        this.realProjectRelationDao = realProjectRelationDao;
    }

    public Page<ProjectVO> getProjectCon(ProjectUserQuery projectUserQuery) {
        Page<ProjectVO> ppage = projectDao.selectProjectList(projectUserQuery);
        List<ProjectVO> projects = ppage.getRecords();
        if (CollectionUtil.isNotEmpty(projects)) {
            List<String> projectIds = projects.stream().map(ProjectVO::getProjectId).collect(Collectors.toList());
            List<RealProjectRelation> realProjectRelations = realProjectRelationDao.selectByProjectIds(projectIds);
            Map<String, List<RealProjectRelation>> relationMap = realProjectRelations.stream().collect(Collectors.groupingBy(RealProjectRelation::getProjectId));
            projects.forEach(p -> {
                if (relationMap.containsKey(p.getProjectId())) {
                    p.setRealProjectNames(relationMap.get(p.getProjectId()).stream().map(RealProjectRelation::getRealProjectName).collect(Collectors.toList()));
                }
            });
            List<ProjectUserVO> userByProject = projectUserDao.getUserByProject(projectIds);
            //userId - project 关系
            Map<String, List<ProjectUserVO>> puMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(userByProject)) {
                puMap = userByProject.stream().collect(Collectors.groupingBy(ProjectUserVO::getProjectId));
            }

            for (String projectId : puMap.keySet()) {
                List<ProjectUserVO> projectUsers = puMap.get(projectId);
                //根据realUserName + projectId 去重
                Iterator<ProjectUserVO> iterator = projectUsers.iterator();
                Set<String> userName = new HashSet<>();
                while ((iterator.hasNext())) {
                    ProjectUserVO next = iterator.next();
                    if (StringUtils.isNotBlank(next.getRealUserName())) {
                        //去重
                        if (userName.contains(next.getRealUserName())) {
                            iterator.remove();
                        } else {
                            userName.add(next.getRealUserName());
                        }
                    }
                }
            }

            Map<String, List<ProjectUserVO>> finalPuMap = puMap;
            projects.forEach((t) -> {
                List<ProjectUserVO> projectUserVOS = finalPuMap.get(t.getProjectId());
                t.setUsers(projectUserVOS);
                t.setCreateStr(DateUtil.format(t.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
            });
        }
        return ppage;
    }
}
