package com.leyunone.codex.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.RealUserRelationDao;
import com.leyunone.codex.dao.entry.RealUserRelation;
import com.leyunone.codex.dao.mapper.RealUserRelationMapper;
import com.leyunone.codex.model.query.CodeQuery;
import com.leyunone.codex.model.vo.RealUserRelationVO;
import com.leyunone.codex.model.vo.RealUserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RealUserRelationRepository extends BaseRepository<RealUserRelationMapper, RealUserRelation> implements RealUserRelationDao {

    @Override
    public int deleteByRealUserId(Integer realUserId) {
        LambdaQueryWrapper<RealUserRelation> lambda = new QueryWrapper<RealUserRelation>().lambda();
        lambda.eq(RealUserRelation::getRealUserId,realUserId);
        return this.baseMapper.delete(lambda);
    }

    @Override
    public List<RealUserRelationVO> selectByRealUserIds(List<Integer> realUserIds) {
        return this.baseMapper.selectByRealUserIds(realUserIds);
    }
    
    @Override
    public List<String> selectUserIdByRealUserId(Integer realUserId) {
        return this.baseMapper.selectUserIdByRealUserId(realUserId);
    }

    @Override
    public List<RealUserRelation> selectByUserIds(List<String> userIds) {
        return this.baseMapper.selectByUserIds(userIds);
    }
    
    @Override
    public List<RealUserRelation> selectByName(List<String> userName) {
        LambdaQueryWrapper<RealUserRelation> lambda = new QueryWrapper<RealUserRelation>().lambda();
        lambda.in(RealUserRelation::getRealUserName,userName);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public Page<RealUserVO> selectCodeUser(CodeQuery query) {
        return null;
    }
}
