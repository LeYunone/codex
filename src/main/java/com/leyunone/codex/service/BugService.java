package com.leyunone.codex.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.RealProjectRelationDao;
import com.leyunone.codex.dao.entry.*;
import com.leyunone.codex.dao.impl.*;
import com.leyunone.codex.model.query.BugQuery;
import com.leyunone.codex.model.query.CodeQuery;
import com.leyunone.codex.model.vo.BugDetailVO;
import com.leyunone.codex.model.vo.BugSumInfo;
import com.leyunone.codex.model.vo.BugVO;
import com.leyunone.codex.model.vo.CodeInfoVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/6 9:29
 */
@Service
public class BugService {

    private final BugRepository bugRepository;
    private final RealUserRepository realUserRepository;
    private final GroupRepository groupRepository;
    private final ProjectUserRepository projectUserRepository;
    private final CodeStatisticsService codeStatisticsService;
    private final RealProjectRelationDao realProjectRelationDao;

    public BugService(BugRepository bugRepository, RealUserRepository realUserRepository, GroupRepository groupRepository, ProjectUserRepository projectUserRepository, CodeStatisticsService codeStatisticsService, RealProjectRelationDao realProjectRelationDao) {
        this.bugRepository = bugRepository;
        this.realUserRepository = realUserRepository;
        this.groupRepository = groupRepository;
        this.projectUserRepository = projectUserRepository;
        this.codeStatisticsService = codeStatisticsService;
        this.realProjectRelationDao = realProjectRelationDao;
    }

    public BugSumInfo getDetailList(BugQuery query) {
        if (ObjectUtil.isNotNull(query.getGroupId())) {
            List<Group> groups = groupRepository.selectGroupUser(CollectionUtil.newArrayList(query.getGroupId()));
            query.setRealUserNames(groups.stream().map(Group::getRealUserName).collect(Collectors.toList()));
        }
        Page<Bug> bugPage = bugRepository.selectPage(query);

        List<BugDetailVO> collect = bugPage.getRecords().stream().map(bug -> {
            BugDetailVO bugDetailVO = new BugDetailVO();
            BeanUtil.copyProperties(bug, bugDetailVO);
            return bugDetailVO;
        }).collect(Collectors.toList());
        Page<BugDetailVO> page = new Page<>(bugPage.getCurrent(), bugPage.getSize());
        page.setTotal(bugPage.getTotal());
        page.setRecords(collect);

        BugSumInfo bugSumInfo = new BugSumInfo();
        List<Bug> bugs = bugRepository.selectList(query);
        bugSumInfo.setBugDetails(page);
        bugSumInfo.setBugCount(bugs.size());
        bugSumInfo.setCloseCount(bugs.stream().filter(t -> StringUtils.isNotBlank(t.getEndTime())).count());
        bugSumInfo.setReopenCount(bugs.stream().filter(t -> "1".equals(t.getBugReopen())).count());
        bugSumInfo.setNoBugCount(bugs.stream().filter(t -> "非缺陷".equals(t.getStatus())).count());
        return bugSumInfo;
    }

    public Page<BugVO> getUserList(BugQuery query) {
        if (ObjectUtil.isNotNull(query.getGroupId())) {
            List<Group> groups = groupRepository.selectGroupUser(CollectionUtil.newArrayList(query.getGroupId()));
            query.setRealUserNames(groups.stream().map(Group::getRealUserName).collect(Collectors.toList()));
        }

        //人员
        Page<Bug> bugPage = bugRepository.selectUserPage(query);

        List<String> realUserNames = bugPage.getRecords().stream().map(Bug::getResolveName).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(realUserNames)) return new Page<>();
        List<RealUser> realUsers = realUserRepository.selectByNames(realUserNames);
        List<Integer> userIds = realUsers.stream().map(RealUser::getRealUserId).collect(Collectors.toList());
        CodeQuery codeQuery = new CodeQuery();
        codeQuery.setRealUserIds(userIds);
        codeQuery.setStartDate(query.getStartDate());
        codeQuery.setEndDate(query.getEndDate());
        codeQuery.setLimit(query.getLimit());
        Page<CodeInfoVO> codeStatistics = codeStatisticsService.getCodeStatistics(codeQuery);
        List<CodeInfoVO> records = codeStatistics.getRecords();
        Map<String, CodeInfoVO> codeMap = records.stream().collect(Collectors.toMap(CodeInfoVO::getRealUserName, x -> x, (x1, x2) -> x1));

        List<BugVO> bugVos = bugPage.getRecords().stream().map(bug -> {
            BugVO bugVO = new BugVO();
            bugVO.setName(bug.getResolveName());
            CodeInfoVO codeInfoVO = codeMap.get(bug.getResolveName());
            if (ObjectUtil.isNotNull(codeInfoVO)) {
                bugVO.setCodeTotal(codeInfoVO.getTotal());
                bugVO.setAdditions(codeInfoVO.getAdditions());
                bugVO.setDeletions(codeInfoVO.getDeletions());
            }
            bugVO.setBugNumber(bug.getCount());
            bugVO.setFixNumber(bug.getFixCount());
            bugVO.setDoingNumber(bugVO.getBugNumber() - bugVO.getFixNumber());
            bugVO.setReopenNumber(bug.getReopenCount());
            return bugVO;
        }).collect(Collectors.toList());
        Page<BugVO> result = new Page<>(bugPage.getCurrent(), bugPage.getSize());
        result.setTotal(bugPage.getTotal());
        result.setRecords(bugVos);
        return result;
    }

    public Page<BugVO> getGroupList(BugQuery query) {
        //部门
        Page<Group> groupPage = groupRepository.selectPage(query.getGroupId(), query.getPageIndex(), query.getPageSize());
        List<Group> groups = groupRepository.selectGroupUser(groupPage.getRecords().stream().map(Group::getGroupId).collect(Collectors.toList()));
        query.setUserNames(groups.stream().map(Group::getRealUserName).collect(Collectors.toList()));
        List<Bug> bugs = bugRepository.selectByUserName(query);
        CodeQuery codeQuery = new CodeQuery();
        codeQuery.setRealUserIds(groups.stream().map(Group::getRealUserId).collect(Collectors.toList()));
        codeQuery.setStartDate(query.getStartDate());
        codeQuery.setEndDate(query.getEndDate());
        codeQuery.setLimit(query.getLimit());

        Page<CodeInfoVO> codeStatistics = codeStatisticsService.getCodeStatistics(codeQuery);
        List<CodeInfoVO> records = codeStatistics.getRecords();
        Map<String, CodeInfoVO> codeMap = records.stream().collect(Collectors.toMap(CodeInfoVO::getRealUserName, x -> x, (x1, x2) -> x1));

        Map<String, List<Bug>> bugMap = bugs.stream().collect(Collectors.groupingBy(Bug::getResolveName));
        Map<Integer, List<Group>> groupMap = groups.stream().collect(Collectors.groupingBy(Group::getGroupId));


        List<BugVO> bugVos = groupPage.getRecords().stream().map(group -> {
            BugVO bugVO = new BugVO();
            bugVO.setName(group.getGroupName());
            bugVO.setGroupId(group.getGroupId());
            List<Group> groupUsers = groupMap.get(group.getGroupId());
            if (CollectionUtil.isNotEmpty(groupUsers)) {
                groupUsers.forEach(gu -> {
                    List<Bug> bs = bugMap.get(gu.getRealUserName());
                    CodeInfoVO codeInfoVO = codeMap.get(gu.getRealUserName());
                    if (CollectionUtil.isNotEmpty(bs)) {
                        if (ObjectUtil.isNotNull(codeInfoVO)) {
                            bugVO.setCodeTotal(bugVO.getCodeTotal() + codeInfoVO.getTotal());
                            bugVO.setDeletions(bugVO.getDeletions() + codeInfoVO.getDeletions());
                            bugVO.setAdditions(bugVO.getAdditions() + codeInfoVO.getAdditions());
                        }
                        bugVO.setBugNumber(bugVO.getBugNumber() + bs.size());
                        bugVO.setFixNumber(bugVO.getFixNumber() + (int) bs.stream().filter(t -> "验证通过".equals(t.getStatus()) | "非缺陷".equals(t.getStatus())).count());
                        bugVO.setReopenNumber(bugVO.getReopenNumber() + (int) bs.stream().filter(t -> "1".equals(t.getBugReopen())).count());
                    }
                });
            }
            bugVO.setDoingNumber(bugVO.getBugNumber() - bugVO.getFixNumber());
            return bugVO;
        }).collect(Collectors.toList());
        Page<BugVO> result = new Page<>(groupPage.getCurrent(), groupPage.getSize());
        result.setTotal(groupPage.getTotal());
        result.setRecords(bugVos);
        return result;
    }

    public Page<BugVO> getProjectList(BugQuery query) {
        //项目
        Page<Bug> bugPage = bugRepository.selectProjectPage(query);

        List<String> projectNames = bugPage.getRecords().stream().map(Bug::getProjectName).collect(Collectors.toList());
        List<RealProjectRelation> realProjectRelations = realProjectRelationDao.selectByProjectNames(projectNames);
        Map<String, List<RealProjectRelation>> projectMap = realProjectRelations.stream().collect(Collectors.groupingBy(RealProjectRelation::getRealProjectName));

        List<String> projectIds = realProjectRelations.stream().map(RealProjectRelation::getProjectId).collect(Collectors.toList());
        Map<String, List<ProjectUser>> projectUserMap = new HashMap<>();
        Map<String, CodeInfoVO> codeMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(projectIds)) {
            List<ProjectUser> projectUsers = projectUserRepository.selectUserByProjectIds(projectIds);
            projectUserMap = projectUsers.stream().collect(Collectors.groupingBy(ProjectUser::getProjectId));
            CodeQuery codeQuery = new CodeQuery();
            codeQuery.setRealUserIds(projectUsers.stream().filter(t -> ObjectUtil.isNotNull(t.getRealUserName())).map(ProjectUser::getRealUserId).collect(Collectors.toList()));
            codeQuery.setStartDate(query.getStartDate());
            codeQuery.setEndDate(query.getEndDate());
            codeQuery.setLimit(query.getLimit());
            Page<CodeInfoVO> codeStatistics = codeStatisticsService.getCodeStatistics(codeQuery);
            List<CodeInfoVO> records = codeStatistics.getRecords();
            codeMap = records.stream().collect(Collectors.toMap(CodeInfoVO::getRealUserName, x -> x, (x1, x2) -> x1));
        }

        Map<String, List<ProjectUser>> finalProjectUserMap = projectUserMap;
        Map<String, CodeInfoVO> finalCodeMap = codeMap;
        List<BugVO> bugVos = bugPage.getRecords().stream().map(bug -> {
            BugVO bugVO = new BugVO();
            bugVO.setName(bug.getProjectName());
            List<RealProjectRelation> relation = projectMap.get(bug.getProjectName());
            if (CollectionUtil.isNotEmpty(relation)) {
                List<String> currentProjectIds = relation.stream().map(RealProjectRelation::getProjectId).collect(Collectors.toList());

                currentProjectIds.forEach(projectId -> {
                    List<ProjectUser> projectUser = finalProjectUserMap.get(projectId);
                    if (CollectionUtil.isNotEmpty(projectUser)) {
                        projectUser.forEach(user -> {
                            CodeInfoVO codeInfoVO = finalCodeMap.get(user.getRealUserName());
                            if (ObjectUtil.isNotNull(codeInfoVO)) {
                                bugVO.setCodeTotal(bugVO.getCodeTotal() + codeInfoVO.getTotal());
                                bugVO.setAdditions(bugVO.getAdditions() + codeInfoVO.getAdditions());
                                bugVO.setDeletions(bugVO.getDeletions() + codeInfoVO.getDeletions());
                            }
                        });
                    }
                });
            }
            bugVO.setBugNumber(bug.getCount());
            bugVO.setFixNumber(bug.getFixCount());
            bugVO.setReopenNumber(bug.getReopenCount());
            bugVO.setDoingNumber(bugVO.getBugNumber() - bugVO.getFixNumber());
            return bugVO;
        }).collect(Collectors.toList());
        Page<BugVO> result = new Page<>(bugPage.getCurrent(), bugPage.getSize());
        result.setTotal(bugPage.getTotal());
        result.setRecords(bugVos);
        return result;
    }

    public void sync() {
        /**
         * TODO 同步存有bug信息的系统数据库
         */
    }
}
