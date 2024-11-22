package com.leyunone.codex.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyunone.codex.dao.entry.GroupUser;
import com.leyunone.codex.model.query.CodeTimeQuery;
import com.leyunone.codex.model.vo.GroupUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupUserMapper extends BaseMapper<GroupUser> {
    
    List<GroupUserVO> selectByUserIds(@Param("ids") List<Integer> realUserId);
    
    List<String> selectGroupByUser(@Param("users") List<String> userNames);
}
