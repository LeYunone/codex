package com.leyunone.codex.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.RealUser;
import com.leyunone.codex.model.query.CodeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RealUserMapper extends BaseMapper<RealUser> {

    Page<RealUser> selectCodeUser(@Param("con") CodeQuery query, Page<RealUser> page);

    List<RealUser> selectCodeUser(@Param("con") CodeQuery query);

}
