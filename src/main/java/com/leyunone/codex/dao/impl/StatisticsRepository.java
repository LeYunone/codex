package com.leyunone.codex.dao.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.leyunone.codex.dao.StatisticsDao;
import com.leyunone.codex.dao.entry.Statistics;
import com.leyunone.codex.dao.mapper.StatisticsMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatisticsRepository extends BaseRepository<StatisticsMapper, Statistics> implements StatisticsDao {

    @Override
    public int updateUserCodeTotal0() {
        LambdaUpdateWrapper<Statistics> lambda = new UpdateWrapper<Statistics>().lambda();
        lambda.set(Statistics::getCodeTotal,0);
        lambda.set(Statistics::getCodeAdditions,0);
        lambda.set(Statistics::getCodeDeletions,0);
        return this.baseMapper.update(null,lambda);
    }

    @Override
    public List<Statistics> selectByUserIds(List<String> ids) {
        LambdaUpdateWrapper<Statistics> lambda = new UpdateWrapper<Statistics>().lambda();
        lambda.in(Statistics::getUserId,ids);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public int updateCodeTotal() {
        return 0;
    }
}
