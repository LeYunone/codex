package com.leyunone.codex.service;

import com.leyunone.codex.rule.enter.AlarmRuleEnterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * :)
 * 定时机器人服务
 *
 * @Author pengli
 * @Date 2024/10/9 17:57
 */
@Service
public class BotTimingService {

    private final Logger logger = LoggerFactory.getLogger(BotTimingService.class);
    private final AlarmRuleEnterService alarmRuleEnterService;
    private final ThreadPoolExecutor botPool;


    private volatile Map<Integer, List<String>> ringData = new ConcurrentHashMap<>();

    public BotTimingService(AlarmRuleEnterService alarmRuleEnterService, ThreadPoolExecutor botPool) {
        this.alarmRuleEnterService = alarmRuleEnterService;
        this.botPool = botPool;
    }


    public void pushTimeRing(int ringSecond, String botId) {
        // push async ring
        List<String> ringItemData = ringData.computeIfAbsent(ringSecond, k -> new ArrayList<>());
        ringItemData.add(botId);
        logger.debug(">>>>>>>>>>> xxl-job, schedule push time-ring : " + ringSecond + " = " + Arrays.asList(ringItemData));
    }

    /**
     * 每秒执行一次 等待机器人执行队列
     */
    @Scheduled(cron = "* 0/1 * * * ?")
    public void timingJob() {
        // second data
        List<String> ringItemData = new ArrayList<>();
        // 避免处理耗时太长，跨过刻度，向前校验一个刻度；
        int nowSecond = Calendar.getInstance().get(Calendar.SECOND);
        for (int i = 0; i < 2; i++) {
            List<String> tmpData = ringData.remove((nowSecond + 60 - i) % 60);
            if (tmpData != null) {
                ringItemData.addAll(tmpData);
            }
        }

        // ring trigger
        logger.debug(">>>>>>>>>>> xxl-job, time-ring beat : " + nowSecond + " = " + Collections.singletonList(ringItemData));
        if (ringItemData.size() > 0) {
            // do trigger
            for (String botId : ringItemData) {
                // do trigger
                botPool.execute(()->{
                    alarmRuleEnterService.run(botId);
                });
                
            }
            // clear
            ringItemData.clear();
        }
    }


}
