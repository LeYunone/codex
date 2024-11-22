package com.leyunone.codex.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.Group;

import java.util.List;

public interface GroupDao extends BaseDao<Group> {

    Group selectByGroupName(String groupName);

    Page<Group> selectPage(Integer groupId, Integer index, Integer size);

    List<Group> selectGroupUser(List<Integer> groupIds);
}
