package com.leyunone.codex.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.RealProjectDao;
import com.leyunone.codex.dao.entry.RealProject;
import com.leyunone.codex.dao.mapper.RealProjectNameMapper;
import com.leyunone.codex.model.query.ProjectRelationQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RealProjectRepository extends BaseRepository<RealProjectNameMapper, RealProject> implements RealProjectDao {

    @Override
    public Page<RealProject> selectPage(ProjectRelationQuery query) {
        LambdaQueryWrapper<RealProject> lambda = new QueryWrapper<RealProject>().lambda();
        lambda.like(StringUtils.isNotBlank(query.getRealProjectName()), RealProject::getRealProjectName, query.getRealProjectName());
        lambda.eq(RealProject::getIsRelation, 1);
        Page<RealProject> page = new Page<>(query.getPageIndex(), query.getPageSize());
        return this.baseMapper.selectPage(page, lambda);
    }

    @Override
    public void updateRelation(List<String> projectNames,Integer status) {
        LambdaUpdateWrapper<RealProject> lambda = new UpdateWrapper<RealProject>().lambda();
        lambda.in(RealProject::getRealProjectName, projectNames);
        lambda.set(RealProject::getIsRelation, status);
        this.baseMapper.update(null, lambda);
    }
}
