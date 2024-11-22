package com.leyunone.codex.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.codex.dao.GroupUserDao;
import com.leyunone.codex.dao.entry.GroupUser;
import com.leyunone.codex.dao.mapper.GroupUserMapper;
import com.leyunone.codex.model.query.CodeTimeQuery;
import com.leyunone.codex.model.vo.GroupUserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupUserRepository extends BaseRepository<GroupUserMapper, GroupUser> implements GroupUserDao {

    @Override
    public int deleteByGroupId(Integer id) {
        LambdaQueryWrapper<GroupUser> lambda = new QueryWrapper<GroupUser>().lambda();
        lambda.eq(GroupUser::getGroupId,id);
        return this.baseMapper.delete(lambda);
    }

    @Override
    public int deleteByUserId(Integer userId) {
        LambdaQueryWrapper<GroupUser> lambda = new QueryWrapper<GroupUser>().lambda();
        lambda.eq(GroupUser::getRealUserId,userId);
        return this.baseMapper.delete(lambda);
    }

    @Override
    public List<GroupUser> selectRealUserByGroupId(Integer groupId) {
        LambdaQueryWrapper<GroupUser> lambda = new QueryWrapper<GroupUser>().lambda();
        lambda.eq(GroupUser::getGroupId,groupId);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public List<GroupUserVO> selectByUserIds(List<Integer> realUserId) {
        return this.baseMapper.selectByUserIds(realUserId);
    }
    
    @Override
    public List<String> selectGroupByUser(List<String> userNames) {
        return this.baseMapper.selectGroupByUser(userNames);
    }
}
