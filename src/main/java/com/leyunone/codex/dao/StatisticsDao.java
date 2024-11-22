package com.leyunone.codex.dao;


import com.leyunone.codex.dao.entry.Statistics;

import java.util.List;

public interface StatisticsDao extends BaseDao<Statistics> {

    int updateUserCodeTotal0();

    List<Statistics> selectByUserIds(List<String> ids);
    
    int updateCodeTotal();
}
