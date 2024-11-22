package com.leyunone.codex.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.*;
import com.leyunone.codex.dao.entry.ProjectUser;
import com.leyunone.codex.dao.entry.RealUser;
import com.leyunone.codex.model.query.CodeQuery;
import com.leyunone.codex.model.vo.CodeInfoVO;
import com.leyunone.codex.model.vo.GroupUserVO;
import com.leyunone.codex.model.vo.RealUserRelationVO;
import com.leyunone.codex.util.UniqueSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/8 10:44
 */
@Service
@RequiredArgsConstructor
public class CodeStatisticsService {

    private final RealUserDao realUserDao;
    private final RealUserRelationDao realUserRelationDao;
    private final CommitDao commitDao;
    private final ProjectUserDao projectUserDao;

    public Page<CodeInfoVO> getCodeStatistics(CodeQuery query) {
        /**
         * 确定人员
         */
        List<RealUser> realUsers;
        Page<RealUser> realUserPage = new Page<>();
        Integer index = query.getPageIndex();
        Integer size = query.getPageSize();
        if (CollectionUtil.isNotEmpty(query.getRealUserIds())) {
            realUsers = realUserDao.selectByIds(query.getRealUserIds());
            index = 1;
            size = 1000;
        } else {
            realUserPage = realUserDao.selectCodeUser(query);
            realUsers = realUserPage.getRecords();
        }
        List<Integer> realUserIds = realUsers.stream().map(RealUser::getRealUserId).collect(Collectors.toList());
        
        List<RealUserRelationVO> realUserRelationVOS = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(realUserIds)) {
            realUserRelationVOS = realUserRelationDao.selectByRealUserIds(realUserIds);
        }
        List<String> userIds = realUserRelationVOS.stream().map(RealUserRelationVO::getUserId).collect(Collectors.toList());
        query.setUserIds(userIds);
        Map<Integer, List<RealUserRelationVO>> userMap = realUserRelationVOS.stream().collect(Collectors.groupingBy(RealUserRelationVO::getRealUserId));
        List<CodeInfoVO> codeInfoVOS = new ArrayList<>();
        List<CodeInfoVO> projectInfoVos = new ArrayList<>();
        
        if (CollectionUtil.isNotEmpty(userIds)) {
            codeInfoVOS = commitDao.selectCodeStatistics(query);
            projectInfoVos = commitDao.selectProjectNumber(query);
        }

        Map<String, CodeInfoVO> codeMap = codeInfoVOS.stream().collect(Collectors.toMap(CodeInfoVO::getUserId, Function.identity()));
        Map<String, List<CodeInfoVO>> projectMap = projectInfoVos.stream().collect(Collectors.groupingBy(CodeInfoVO::getUserId));

        Page<CodeInfoVO> page = new Page<>(index, size);
        List<CodeInfoVO> result = realUsers.stream().map(user -> {
            CodeInfoVO codeInfoVO = new CodeInfoVO();
            codeInfoVO.setRealUserName(user.getRealUserName());
            codeInfoVO.setGroupName(user.getGroupName());
            long total = 0L;
            long deletions = 0L;
            long add = 0L;
            long projectNumber = 0L;
            if (userMap.containsKey(user.getRealUserId())) {
                List<String> currentUserIds = userMap.get(user.getRealUserId()).stream().map(RealUserRelationVO::getUserId).collect(Collectors.toList());
                for (String currentUserId : currentUserIds) {
                    if (codeMap.containsKey(currentUserId)) {
                        CodeInfoVO currentCodeInfo = codeMap.get(currentUserId);
                        total += currentCodeInfo.getTotal();
                        deletions += currentCodeInfo.getDeletions();
                        add += currentCodeInfo.getAdditions();
                    }
                    if (projectMap.containsKey(currentUserId)) {
                        List<CodeInfoVO> codeInfoVOS1 = projectMap.get(currentUserId);
                        projectNumber += codeInfoVOS1.size();
                    }
                }
            }
            codeInfoVO.setProjectNumber(projectNumber);
            codeInfoVO.setTotal(total);
            codeInfoVO.setDeletions(deletions);
            codeInfoVO.setAdditions(add);
            return codeInfoVO;
        }).collect(Collectors.toList());
        page.setRecords(result);
        page.setTotal(realUserPage.getTotal());
        return page;
    }

    public CodeInfoVO getCodeTotal(CodeQuery query) {
        List<RealUser> realUser = realUserDao.selectCodeUserList(query);
        List<Integer> realUserIds = realUser.stream().map(RealUser::getRealUserId).collect(Collectors.toList());
        List<RealUserRelationVO> realUserRelationVOS = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(realUserIds)) {
            realUserRelationVOS = realUserRelationDao.selectByRealUserIds(realUserIds);
        }
        
        List<String> userIds = realUserRelationVOS.stream().map(RealUserRelationVO::getUserId).collect(Collectors.toList());
        query.setUserIds(userIds);
        List<CodeInfoVO> codeInfoVOS = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(query.getUserIds())) {
            codeInfoVOS = commitDao.selectCodeStatistics(query);
        }
        CodeInfoVO codeInfoVO = new CodeInfoVO();
        codeInfoVO.setAdditions(codeInfoVOS.parallelStream().mapToLong(CodeInfoVO::getAdditions).sum());
        codeInfoVO.setDeletions(codeInfoVOS.parallelStream().mapToLong(CodeInfoVO::getDeletions).sum());
        codeInfoVO.setTotal(codeInfoVOS.parallelStream().mapToLong(CodeInfoVO::getTotal).sum());

        List<ProjectUser> projectUsers = projectUserDao.selectByUserIds(userIds);
        UniqueSet<String, ProjectUser> uniqueSet = new UniqueSet<>(ProjectUser::getProjectId);
        uniqueSet.addAll(projectUsers);
        codeInfoVO.setProjectNumber((long) CollectionUtil.newArrayList(uniqueSet).size());
        return codeInfoVO;


    }
}
