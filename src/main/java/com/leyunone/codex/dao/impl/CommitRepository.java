package com.leyunone.codex.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.CommitDao;
import com.leyunone.codex.dao.entry.Commit;
import com.leyunone.codex.dao.mapper.CommitMapper;
import com.leyunone.codex.model.bo.CommitBO;
import com.leyunone.codex.model.query.CodeQuery;
import com.leyunone.codex.model.query.CodeTimeQuery;
import com.leyunone.codex.model.query.CommitQuery;
import com.leyunone.codex.model.vo.CodeInfoVO;
import com.leyunone.codex.model.vo.CommitVO;
import com.leyunone.codex.model.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class CommitRepository extends BaseRepository<CommitMapper, Commit> implements CommitDao {

    @Override
    public Page<Commit> selectByPage(CommitQuery commitQuery) {
        Page<Commit> page = new Page(commitQuery.getPageIndex(), commitQuery.getPageSize());
        LambdaQueryWrapper<Commit> lambda = new QueryWrapper<Commit>().lambda();
        lambda.in(CollectionUtil.isNotEmpty(commitQuery.getUserIds()), Commit::getUserId, commitQuery.getUserIds());
        lambda.eq(StringUtils.isNotBlank(commitQuery.getProjectId()), Commit::getProjectId, commitQuery.getProjectId());
        lambda.ge(StringUtils.isNotBlank(commitQuery.getStartDate()), Commit::getCommitDate, commitQuery.getStartDate());
        lambda.eq(StringUtils.isNotBlank(commitQuery.getCommitId()), Commit::getCommitId, commitQuery.getCommitId());
        lambda.le(StringUtils.isNotBlank(commitQuery.getEndDate()), Commit::getCommitDate, commitQuery.getEndDate());
        lambda.eq(ObjectUtil.isNotNull(commitQuery.getStorageId()), Commit::getStorageId, commitQuery.getStorageId());
        lambda.lt(ObjectUtil.isNotNull(commitQuery.getLimit()), Commit::getTotal, commitQuery.getLimit());
        lambda.orderByDesc(Commit::getCommitDate);
        return this.baseMapper.selectPage(page, lambda);
    }

    @Override
    public List<UserVO> selectSumGroupUser(String startDate, String endDate) {
        return this.baseMapper.selectSumGroupUser(startDate, endDate);
    }

    @Override
    public void saveBatch(List<CommitBO> commits) {
        this.baseMapper.batchInsert(commits);
    }

    @Override
    public Date selectLastDate(String url) {
        return this.baseMapper.selectLastDate(url);
    }

    @Override
    public List<CommitVO> selectProjectCodeGroupUser(CodeTimeQuery query) {
        return this.baseMapper.selectProjectCodeGroupUser(query);
    }

    @Override
    public List<CommitVO> selectProjectCodeGroupUserMonth(CodeTimeQuery query) {
        return this.baseMapper.selectProjectCodeGroupUserMonth(query);
    }

    @Override
    public List<String> preDate(String date) {
        return this.baseMapper.preDate(date);
    }

    @Override
    public List<UserVO> selectByUser(CodeTimeQuery query) {
        return this.baseMapper.selectByUser(query);
    }

    @Override
    public List<CommitVO> selectProjectCodeTime(CodeTimeQuery query) {
        return this.baseMapper.selectProjectTimeCode(query);
    }

    @Override
    public List<CommitVO> selectProjectCodeTimeMonth(CodeTimeQuery query) {
        return this.baseMapper.selectProjectTimeCodeMonth(query);
    }

    @Override
    public List<CommitVO> selectByProject(CodeTimeQuery codeTimeQuery) {
        return this.baseMapper.selectByProject(codeTimeQuery);
    }

    @Override
    public List<CodeInfoVO> selectCodeStatistics(CodeQuery query) {
        return this.baseMapper.selectCodeStatistics(query);
    }

    @Override
    public List<CodeInfoVO> selectProjectNumber(CodeQuery query) {
        return this.baseMapper.selectProjectNumber(query);
    }
} 
