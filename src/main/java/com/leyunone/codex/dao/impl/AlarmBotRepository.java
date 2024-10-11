package com.leyunone.codex.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.AlarmBot;
import com.leyunone.codex.dao.mapper.AlarmBotMapper;
import com.leyunone.codex.model.query.BotQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/8 10:55
 */
@Repository
public class AlarmBotRepository extends BaseRepository<AlarmBotMapper, AlarmBot> {

    public void deleteAll() {
        LambdaQueryWrapper<AlarmBot> lambda = new QueryWrapper<AlarmBot>().lambda();
        this.baseMapper.delete(lambda);
    }

    public List<AlarmBot> selectIntervalTimeBot(Long startTime, Long endTime) {
        LambdaQueryWrapper<AlarmBot> lambda = new QueryWrapper<AlarmBot>().lambda();
//        lambda.gt(AlarmBot::getNextTriggerTime, startTime);
        lambda.le(AlarmBot::getNextTriggerTime, endTime);
        lambda.eq(AlarmBot::getStatus,1);
        return this.baseMapper.selectList(lambda);
    }

    public Page<AlarmBot> selectPage(BotQuery query) {
        Page<AlarmBot> page = new Page<>(query.getPageIndex(), query.getPageSize());
        LambdaQueryWrapper<AlarmBot> lambda = new QueryWrapper<AlarmBot>().lambda();
        lambda.orderByDesc(AlarmBot::getCreateTime);
        return this.baseMapper.selectPage(page, lambda);
    }

    public void updateNull(String id) {
        LambdaUpdateWrapper<AlarmBot> lambda = new UpdateWrapper<AlarmBot>().lambda();
        lambda.set(AlarmBot::getBugReopenCondition, null);
        lambda.set(AlarmBot::getSubmitCondition, null);
        lambda.set(AlarmBot::getCodeCondition, null);
        lambda.set(AlarmBot::getBugCountCondition, null);
        lambda.set(AlarmBot::getBugRateCondition, null);
        lambda.set(AlarmBot::getAlarmObject, null);
        lambda.set(AlarmBot::getNextTriggerTime,null);
        lambda.eq(AlarmBot::getId, id);
        this.baseMapper.update(null, lambda);
    }
}
