package com.leyunone.codex.dao;

import com.leyunone.codex.dao.entry.GroupUser;
import com.leyunone.codex.model.query.CodeTimeQuery;
import com.leyunone.codex.model.vo.GroupUserVO;

import java.util.List;

public interface GroupUserDao extends BaseDao<GroupUser> {


    int deleteByGroupId(Integer id);

    int deleteByUserId(Integer userId);

    List<GroupUser> selectRealUserByGroupId(Integer groupId);

    List<GroupUserVO> selectByUserIds(List<Integer> userId);

    List<String> selectGroupByUser(List<String> userNames);


}
