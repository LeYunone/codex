package com.leyunone.codex.dao;


import com.leyunone.codex.dao.entry.Storage;

import java.util.List;

public interface StorageDao extends BaseDao<Storage> {
    
    Storage saveStorage(Storage storage);
    
    List<Storage> selectByUrl(String url);
}
