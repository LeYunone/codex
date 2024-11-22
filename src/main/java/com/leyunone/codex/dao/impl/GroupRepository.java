package com.leyunone.codex.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.GroupDao;
import com.leyunone.codex.dao.entry.Group;
import com.leyunone.codex.dao.mapper.GroupMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRepository extends BaseRepository<GroupMapper, Group> implements GroupDao {

    @Override
    public Group selectByGroupName(String groupName) {
        LambdaQueryWrapper<Group> lambda = new QueryWrapper<Group>().lambda();
        lambda.eq(Group::getGroupName, groupName);
        return this.baseMapper.selectOne(lambda);
    }
    
    @Override
    public Page<Group> selectPage(Integer groupId,Integer index,Integer size){
        LambdaQueryWrapper<Group> lambda = new QueryWrapper<Group>().lambda();
        lambda.eq(ObjectUtil.isNotNull(groupId),Group::getGroupId,groupId);
        Page<Group> page = new Page<>(index,size);
        return this.baseMapper.selectPage(page,lambda);
    }

    @Override
    public List<Group> selectGroupUser(List<Integer> groupIds) {
        return this.baseMapper.selectGroupUser(groupIds);
    }
}
