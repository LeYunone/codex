package com.leyunone.codex.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.RealUserDao;
import com.leyunone.codex.dao.entry.RealUser;
import com.leyunone.codex.dao.mapper.RealUserMapper;
import com.leyunone.codex.model.query.CodeQuery;
import com.leyunone.codex.model.query.RealUserQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RealUserRepository extends BaseRepository<RealUserMapper, RealUser> implements RealUserDao {
    
    @Override
    public Page<RealUser> selectPage(RealUserQuery query) {
        LambdaQueryWrapper<RealUser> lambda = new QueryWrapper<RealUser>().lambda();
        lambda.like(StringUtils.isNotBlank(query.getRealUserName()), RealUser::getRealUserName,query.getRealUserName());
        lambda.in(CollectionUtil.isNotEmpty(query.getRealUserIds()), RealUser::getRealUserId,query.getRealUserIds());
        Page<RealUser> page = new Page<>(query.getPageIndex(),query.getPageSize());
        return this.baseMapper.selectPage(page,lambda);
    }

    @Override
    public List<RealUser> selectByNames(List<String> userNames) {
        LambdaQueryWrapper<RealUser> lambda = new QueryWrapper<RealUser>().lambda();
        lambda.in(RealUser::getRealUserName,userNames);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public RealUser selectByName(String userName) {
        LambdaQueryWrapper<RealUser> lambda = new QueryWrapper<RealUser>().lambda();
        lambda.eq(RealUser::getRealUserName,userName);
        return this.baseMapper.selectOne(lambda);
    }

    @Override
    public Page<RealUser> selectCodeUser(CodeQuery query) {
        Page<RealUser> page = new Page<>(query.getPageIndex(),query.getPageSize());
        return this.baseMapper.selectCodeUser(query,page);
    }


    @Override
    public List<RealUser> selectCodeUserList(CodeQuery query) {
        return this.baseMapper.selectCodeUser(query);
    }
}
