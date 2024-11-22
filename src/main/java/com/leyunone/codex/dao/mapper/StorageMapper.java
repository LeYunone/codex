package com.leyunone.codex.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyunone.codex.dao.entry.Storage;
import com.leyunone.codex.model.query.StorageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StorageMapper extends BaseMapper<Storage> {

    List<Storage> selectListCon(@Param("con") StorageQuery query);
}
