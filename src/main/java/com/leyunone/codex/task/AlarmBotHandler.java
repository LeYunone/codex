package com.leyunone.codex.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.dao.entry.AlarmBot;
import com.leyunone.codex.dao.impl.AlarmBotRepository;
import com.leyunone.codex.rule.enter.AlarmRuleEnterService;
import com.leyunone.codex.service.BotService;
import com.leyunone.codex.service.BotTimingService;
import com.leyunone.codex.util.JobScheduleUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/8 11:00
 */
@Component
public class AlarmBotHandler extends IJobHandler {

    private final AlarmBotRepository alarmBotRepository;
    private final BotTimingService botTimingService;

    public AlarmBotHandler(AlarmBotRepository alarmBotRepository, BotTimingService botTimingService) {
        this.alarmBotRepository = alarmBotRepository;
        this.botTimingService = botTimingService;
    }

    /**
     * 告警机器人
     */
//    @XxlJob(value = "alarm_bot")
    @Override
    @Scheduled(cron = "* * * * * ?")
    public void execute() {
        long nowTime = System.currentTimeMillis();
        List<AlarmBot> alarmBots = alarmBotRepository.selectIntervalTimeBot(nowTime, nowTime + JobScheduleUtil.INTERVALTIME);
        if (CollectionUtil.isNotEmpty(alarmBots)) {
            /**
             * 1-5秒内 通过n/10 hash分片分流到每秒处理的队列中，然后由各队列执行器下发具体规则引擎逻辑：
             */
            for (AlarmBot alarmBot : alarmBots) {
                boolean pushJob = false;
                Date current = new Date();
                if (nowTime > alarmBot.getNextTriggerTime() + JobScheduleUtil.INTERVALTIME) {
                    //超时 10s
                } else if (nowTime > alarmBot.getNextTriggerTime()) {
                    //还在第二个周期内[队列前数量过多处理时间超过了5s间隔] 可接受触发
                    if (alarmBot.getStatus() == 1 && nowTime + JobScheduleUtil.INTERVALTIME > alarmBot.getNextTriggerTime()) {
                        pushJob = true;
                        current = new Date(alarmBot.getNextTriggerTime());
                    }
                } else {
                    pushJob = true;
                    current = new Date(alarmBot.getNextTriggerTime());
                }
                if (pushJob) {
                    int ringSecond = (int) ((alarmBot.getNextTriggerTime() / 1000) % 60);
                    botTimingService.pushTimeRing(ringSecond, alarmBot.getId());
                    //更新下一个时间
                }
                this.refreshNextValidTime(alarmBot, current);
            }
            alarmBotRepository.updateBatchById(alarmBots);
        }
    }

    private void refreshNextValidTime(AlarmBot alarmBot, Date fromTime) {
        Date nextValidTime = JobScheduleUtil.generateNextValidTime(alarmBot.getCron(), fromTime);
        if (ObjectUtil.isNull(nextValidTime)) {
            return;
        }
        alarmBot.setNextTriggerTime(nextValidTime.getTime());
    }
}
