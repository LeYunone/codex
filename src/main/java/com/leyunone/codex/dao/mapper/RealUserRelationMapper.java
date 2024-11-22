package com.leyunone.codex.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyunone.codex.dao.entry.RealUserRelation;
import com.leyunone.codex.model.vo.RealUserRelationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RealUserRelationMapper extends BaseMapper<RealUserRelation> {

    List<RealUserRelationVO> selectByRealUserIds(@Param("ids") List<Integer> realUserIds);

    List<String> selectUserIdByRealUserId(Integer realUserIds);

    List<RealUserRelation> selectByUserIds(@Param("ids") List<String> userIds);
}
