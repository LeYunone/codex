package com.leyunone.codex.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.BugDao;
import com.leyunone.codex.dao.entry.Bug;
import com.leyunone.codex.dao.mapper.BugMapper;
import com.leyunone.codex.model.query.BugQuery;
import com.leyunone.codex.model.query.BugTopQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BugRepository extends BaseRepository<BugMapper, Bug> implements BugDao {

    @Override
    public Page<Bug> selectPage(BugQuery query) {
        Page<Bug> page = new Page<>(query.getPageIndex(), query.getPageSize());
        return this.baseMapper.selectPage(page, this.getCon(query));
    }

    @Override
    public List<Bug> selectList(BugQuery query) {
        return this.baseMapper.selectList(this.getCon(query));
    }

    private LambdaQueryWrapper<Bug> getCon(BugQuery query) {
        LambdaQueryWrapper<Bug> lambda = new QueryWrapper<Bug>().lambda();
        lambda.like(StringUtils.isNotBlank(query.getRealUserName()), Bug::getResolveName, query.getRealUserName());
        lambda.gt(StringUtils.isNotBlank(query.getStartDate()), Bug::getCreateTime, query.getStartDate());
        lambda.lt(StringUtils.isNotBlank(query.getEndDate()), Bug::getCreateTime, query.getEndDate());
        lambda.eq(StringUtils.isNotBlank(query.getStatus()), Bug::getStatus, query.getStatus());
        lambda.eq(StringUtils.isNotBlank(query.getBugReopen()), Bug::getBugReopen, query.getBugReopen());
        lambda.eq(StringUtils.isNotBlank(query.getBugProjectName()), Bug::getProjectName, query.getBugProjectName());
        lambda.like(StringUtils.isNotBlank(query.getBugId()), Bug::getBugId, query.getBugId());
        lambda.eq(StringUtils.isNotBlank(query.getDepartment()), Bug::getDepartment, query.getDepartment());
        lambda.eq(StringUtils.isNotBlank(query.getBugType2()), Bug::getBugType2, query.getBugType2());
        lambda.like(StringUtils.isNotBlank(query.getBugTitle()), Bug::getBugTitle, query.getBugTitle());
        lambda.in(CollectionUtil.isNotEmpty(query.getRealUserNames()), Bug::getResolveName, query.getRealUserNames());
        lambda.likeRight(StringUtils.isNotBlank(query.getVersionPrefix()),Bug::getBugVersion,query.getVersionPrefix());
        lambda.likeLeft(StringUtils.isNotBlank(query.getVersionSuffix()),Bug::getBugVersion,query.getVersionSuffix());
        lambda.orderByDesc(Bug::getCreateTime);
        return lambda;
    }

    @Override
    public List<Bug> selectByUserName(BugQuery query) {
        LambdaQueryWrapper<Bug> lambda = new QueryWrapper<Bug>().lambda();
        lambda.in(Bug::getResolveName, query.getUserNames());
        lambda.gt(StringUtils.isNotBlank(query.getStartDate()), Bug::getCreateTime, query.getStartDate());
        lambda.lt(StringUtils.isNotBlank(query.getEndDate()), Bug::getCreateTime, query.getEndDate());
        lambda.eq(StringUtils.isNotBlank(query.getStatus()), Bug::getStatus, query.getStatus());
        lambda.eq(StringUtils.isNotBlank(query.getBugProjectName()), Bug::getProjectName, query.getBugProjectName());
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public List<Bug> selectCountAlarmGroupByUser(String startDate, Integer bug, Integer conType) {
        return this.baseMapper.selectCountAlarmGroupByUser(startDate, bug, conType);
    }

    @Override
    public List<String> selectProjectByUser(String startDate, List<String> userNames) {
        return this.baseMapper.selectProjectByUser(startDate, userNames);
    }

    @Override
    public List<Bug> selectCountAlarmGroupByProject(String startDate, Integer bug, Integer conType, List<String> projectNames) {
        return this.baseMapper.selectCountAlarmGroupByProject(startDate, bug, conType, projectNames);
    }

    @Override
    public List<Bug> selectReopenAlarm(String startDate, Double reopen, Integer conType) {
        return this.baseMapper.selectReopenAlarm(startDate, reopen, conType);
    }

    @Override
    public Page<Bug> selectUserPage(BugQuery query) {
        Page<Bug> page = new Page<>(query.getPageIndex(), query.getPageSize());
        return this.baseMapper.selectUserPage(query, page);
    }

    @Override
    public List<Bug> selectUserList(BugQuery query) {
        return this.baseMapper.selectUserPage(query);
    }

    @Override
    public void deleteAll() {
        LambdaQueryWrapper<Bug> lambda = new QueryWrapper<Bug>().lambda();
        this.baseMapper.delete(lambda);
    }

    @Override
    public Page<Bug> selectProjectPage(BugQuery query) {
        Page<Bug> page = new Page<>(query.getPageIndex(), query.getPageSize());
        return this.baseMapper.selectProjectPage(query, page);
    }

    @Override
    public List<Bug> selectBugTopGroupByProjectName(BugQuery query) {
        return this.baseMapper.selectBugTopGroupByProjectName(query);
    }

    @Override
    public List<Bug> selectBugTopGroupByUserName(BugTopQuery query) {
        return this.baseMapper.selectBugTopGroupByUserName(query);
    }
}
