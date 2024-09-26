package com.leyunone.codex.task;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.dao.entry.AlarmBot;
import com.leyunone.codex.dao.impl.AlarmBotRepository;
import com.leyunone.codex.rule.enter.AlarmRuleEnterService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

    private final AlarmRuleEnterService alarmRuleEnterService;
    private final ThreadPoolExecutor botPool;
    private final AlarmBotRepository alarmBotRepository;

    private static final Integer intervalTime = 5000;

    public AlarmBotHandler(AlarmRuleEnterService alarmRuleEnterService, ThreadPoolExecutor botPool, AlarmBotRepository alarmBotRepository) {
        this.alarmRuleEnterService = alarmRuleEnterService;
        this.botPool = botPool;
        this.alarmBotRepository = alarmBotRepository;
    }

    /**
     * 告警机器人
     */
    @XxlJob(value = "alarm_bot")
    @Override
    @Scheduled(cron = "1")
    public void execute() {

        long start = System.currentTimeMillis();
        List<AlarmBot> alarmBots = alarmBotRepository.selectIntervalTimeBot(start, start + intervalTime);
        if (CollectionUtil.isNotEmpty(alarmBots)) {
            /**
             * 1-5秒内 通过n/10 hash分片分流到每秒处理的队列中，然后由各队列执行器下发具体规则引擎逻辑：
             * TODO 仿造xxljob定时任务处理模式
             */
        }
        String jobParam = XxlJobHelper.getJobParam();
        if (StringUtils.isBlank(jobParam)) return;
        try {
            alarmRuleEnterService.run(jobParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
