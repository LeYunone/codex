package com.leyunone.codex.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.codex.dao.StorageDao;
import com.leyunone.codex.dao.entry.Storage;
import com.leyunone.codex.dao.mapper.StorageMapper;
import com.leyunone.codex.model.query.StorageQuery;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StorageRepository extends BaseRepository<StorageMapper, Storage> implements StorageDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
    
    @Override
    public Storage saveStorage(Storage entity) {
        this.baseMapper.insert(entity);
        return entity;
    }

    @Override
    public List<Storage> selectByUrl(String url) {
        LambdaQueryWrapper<Storage> lambda = new QueryWrapper<Storage>().lambda();
        lambda.eq(Storage::getStorageUrl,url);
        return this.baseMapper.selectList(lambda);
    }

}
