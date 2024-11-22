package com.leyunone.codex.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.codex.dao.RealProjectRelationDao;
import com.leyunone.codex.dao.entry.RealProjectRelation;
import com.leyunone.codex.dao.mapper.RealProjectRelationMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RealProjectRelationRepository extends BaseRepository<RealProjectRelationMapper, RealProjectRelation> implements RealProjectRelationDao {

    @Override
    public void deleteByProjectNames(List<String> projectNames) {
        LambdaQueryWrapper<RealProjectRelation> lambda = new QueryWrapper<RealProjectRelation>().lambda();
        lambda.in(RealProjectRelation::getRealProjectName, projectNames);
        this.baseMapper.delete(lambda);
    }

    @Override
    public void deleteByProjectIds(List<String> projectIds) {
        LambdaQueryWrapper<RealProjectRelation> lambda = new QueryWrapper<RealProjectRelation>().lambda();
        lambda.in(RealProjectRelation::getProjectId, projectIds);
        this.baseMapper.delete(lambda);
    }

    @Override
    public List<RealProjectRelation> selectByProjectIds(List<String> projectIds) {
        LambdaQueryWrapper<RealProjectRelation> lambda = new QueryWrapper<RealProjectRelation>().lambda();
        lambda.in(RealProjectRelation::getProjectId, projectIds);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public List<RealProjectRelation> selectByProjectNames(List<String> projectNames) {
        LambdaQueryWrapper<RealProjectRelation> lambda = new QueryWrapper<RealProjectRelation>().lambda();
        lambda.in(RealProjectRelation::getRealProjectName, projectNames);
        return this.baseMapper.selectList(lambda);
    }
}
