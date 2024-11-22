package com.leyunone.codex.dao.impl;

import com.leyunone.codex.dao.BugBiDao;
import com.leyunone.codex.dao.entry.BugBI;
import com.leyunone.codex.dao.mapper.BugBiMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BugBiRepository extends BaseRepository<BugBiMapper, BugBI> implements BugBiDao {

}
