package com.leyunone.codex.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyunone.codex.dao.entry.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper extends BaseMapper<Group> {

    List<Group> selectGroupUser(@Param("groupIds") List<Integer> groupIds);
}
