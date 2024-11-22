package com.leyunone.codex.dao.impl;

import com.leyunone.codex.dao.BranchesDao;
import com.leyunone.codex.dao.entry.Branches;
import com.leyunone.codex.dao.mapper.BranchesMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BranchesRepository extends BaseRepository<BranchesMapper, Branches> implements BranchesDao {
    
}
