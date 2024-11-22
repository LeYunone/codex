package com.leyunone.codex.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.ProjectDao;
import com.leyunone.codex.dao.ProjectUserDao;
import com.leyunone.codex.dao.RealProjectDao;
import com.leyunone.codex.dao.RealProjectRelationDao;
import com.leyunone.codex.dao.entry.Project;
import com.leyunone.codex.dao.entry.ProjectUser;
import com.leyunone.codex.dao.entry.RealProject;
import com.leyunone.codex.dao.entry.RealProjectRelation;
import com.leyunone.codex.model.ResponseCode;
import com.leyunone.codex.model.dto.ProjectRelationDTO;
import com.leyunone.codex.model.query.ProjectRelationQuery;
import com.leyunone.codex.model.vo.ProjectRelationVO;
import com.leyunone.codex.util.AssertUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/29 9:59
 */
@Service
public class ProjectRelationService {

    private final RealProjectRelationDao realProjectRelationDao;
    private final RealProjectDao realProjectDao;
    private final ProjectUserDao projectUserDao;
    private final ProjectDao projectDao;

    public ProjectRelationService(RealProjectRelationDao realProjectRelationDao, RealProjectDao realProjectDao, ProjectUserDao projectUserDao, ProjectDao projectDao) {
        this.realProjectRelationDao = realProjectRelationDao;
        this.realProjectDao = realProjectDao;
        this.projectUserDao = projectUserDao;
        this.projectDao = projectDao;
    }

    public void relationRealProject(ProjectRelationDTO projectRelationDTO) {
        if (ObjectUtil.isNotNull(projectRelationDTO.getType()) && projectRelationDTO.getType() == 0 && CollectionUtil.isNotEmpty(projectRelationDTO.getRealProjectNames())) {
            //新增
            List<RealProjectRelation> realProjectRelations = realProjectRelationDao.selectByProjectNames(projectRelationDTO.getRealProjectNames());
            AssertUtil.isFalse(CollectionUtil.isNotEmpty(realProjectRelations), ResponseCode.PROJECT_IS_EXIST);
        }
        List<String> projectIds = projectRelationDTO.getProjectIds();
        List<String> realProjectNames = projectRelationDTO.getRealProjectNames();

        if (CollectionUtil.isEmpty(projectIds) && CollectionUtil.isNotEmpty(realProjectNames)) {
            realProjectRelationDao.deleteByProjectNames(realProjectNames);
            realProjectDao.updateRelation(realProjectNames, 0);
        } else if (CollectionUtil.isNotEmpty(projectIds) && CollectionUtil.isEmpty(realProjectNames)) {
            //git项目关联为空
            realProjectRelationDao.deleteByProjectIds(projectIds);
        } else {
            List<RealProjectRelation> realProjectRelations = new ArrayList<>();

            if (ObjectUtil.isNotNull(projectRelationDTO.getType()) && (projectRelationDTO.getType() == 1 || projectRelationDTO.getType() == 0)) {
                //真实项目关联git  realProjectNames = 1
                realProjectRelations.addAll(projectIds.stream().map(projectId -> {
                    RealProjectRelation realProjectRelation = new RealProjectRelation();
                    realProjectRelation.setProjectId(projectId);
                    realProjectRelation.setRealProjectName(realProjectNames.get(0));
                    return realProjectRelation;
                }).collect(Collectors.toList()));
                realProjectRelationDao.deleteByProjectNames(realProjectNames);
            } else {
                //git项目列表关联 projectIds = 1
                realProjectRelations.addAll(realProjectNames.stream().map(realProjectName -> {
                    RealProjectRelation realProjectRelation = new RealProjectRelation();
                    realProjectRelation.setProjectId(projectIds.get(0));
                    realProjectRelation.setRealProjectName(realProjectName);
                    return realProjectRelation;
                }).collect(Collectors.toList()));
                realProjectRelationDao.deleteByProjectIds(projectIds);
            }
            realProjectRelationDao.insertOrUpdateBatch(realProjectRelations);
            realProjectDao.updateRelation(realProjectNames, 1);
        }
    }

    public Page<ProjectRelationVO> getRelationList(ProjectRelationQuery query) {
        Page<RealProject> realProjectNamePage = realProjectDao.selectPage(query);
        if (CollectionUtil.isEmpty(realProjectNamePage.getRecords())) return new Page<>();

        List<String> realProjectNames = realProjectNamePage.getRecords().stream().map(RealProject::getRealProjectName).collect(Collectors.toList());
        List<RealProjectRelation> relations = realProjectRelationDao.selectByProjectNames(realProjectNames);

        Map<String, List<RealProjectRelation>> relationMap = relations.stream().collect(Collectors.groupingBy(RealProjectRelation::getRealProjectName));
        List<String> projectIds = relations.stream().map(RealProjectRelation::getProjectId).collect(Collectors.toList());
        Map<String, Project> projectMap = new HashMap<>();
        Map<String, List<ProjectUser>> userMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(projectIds)) {
            List<Project> projects = projectDao.selectByIds(projectIds);
            projectMap = projects.stream().collect(Collectors.toMap(Project::getProjectId, Function.identity()));
            List<ProjectUser> projectUsers = projectUserDao.selectUserByProjectIds(projectIds);
            userMap = projectUsers.stream().collect(Collectors.groupingBy(ProjectUser::getProjectId));
        }


        Page<ProjectRelationVO> page = new Page<>();
        page.setTotal(realProjectNamePage.getTotal());
        Map<String, Project> finalProjectMap = projectMap;
        Map<String, List<ProjectUser>> finalUserMap = userMap;
        page.setRecords(realProjectNamePage.getRecords().stream().map(p -> {
            ProjectRelationVO projectRelationVO = new ProjectRelationVO();
            List<RealProjectRelation> realProjectRelations = relationMap.get(p.getRealProjectName());
            if (CollectionUtil.isNotEmpty(realProjectRelations)) {
                projectRelationVO.setProjectNames(realProjectRelations.stream().map(t -> finalProjectMap.get(t.getProjectId()).getProjectName()).collect(Collectors.toList()));

                projectRelationVO.setProjectIds(realProjectRelations.stream().map(RealProjectRelation::getProjectId).collect(Collectors.toList()));

                Set<String> userNames = new HashSet<>();
                realProjectRelations.forEach(t -> {
                    if (finalUserMap.containsKey(t.getProjectId())) {
                        userNames.addAll(finalUserMap.get(t.getProjectId()).stream().filter(d -> StringUtils.isNotBlank(d.getRealUserName())).map(ProjectUser::getRealUserName).collect(Collectors.toList()));
                    }
                });
                projectRelationVO.setRealUserNames(CollectionUtil.newArrayList(userNames));
            }

            projectRelationVO.setDepartment(p.getDepartment());
            projectRelationVO.setRealProjectName(p.getRealProjectName());
            return projectRelationVO;
        }).collect(Collectors.toList()));
        return page;
    }
}
