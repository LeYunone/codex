package com.leyunone.codex.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.AlarmBot;
import com.leyunone.codex.dao.impl.AlarmBotRepository;
import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.JobInfo;
import com.leyunone.codex.model.ResponseCode;
import com.leyunone.codex.model.dto.AlarmBotDTO;
import com.leyunone.codex.model.query.BotQuery;
import com.leyunone.codex.model.vo.AlarmBotVO;
import com.leyunone.codex.util.AssertUtil;
import com.leyunone.codex.util.JobScheduleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/29 16:44
 */
@Service
public class BotService {

    @Autowired
    private AlarmBotRepository alarmBotRepository;
//    @Autowired
//    private XxlJobService xxlJobService;

    @Transactional(rollbackFor = Exception.class)
    public void saveBot(AlarmBotDTO bot) {

        JobInfo jobInfo = JobInfo.builder().createTime(LocalDateTime.now()).updateTime(LocalDateTime.now())
                .jobDesc("预警机器人" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                .author("admin").scheduleType("CRON").scheduleTime(bot.getCron()).status(bot.isStatus() ? 1 : 0).build();

        AlarmBot alarmBot = new AlarmBot();
        alarmBot.setId(bot.getId());
        alarmBot.setBotUrl(bot.getBotUrl());
        alarmBot.setAlarmObject(JSONObject.toJSONString(bot.getAlarmObject()));
        alarmBot.setAlarmAll(bot.getAlarmAll());
        if (CollectionUtil.isNotEmpty(bot.getAlarmConditions())) {
            bot.getAlarmConditions().forEach(b -> {
                String s = JSONObject.toJSONString(b);
                switch (b.getKey()) {
                    case 1:
                        alarmBot.setBugCountCondition(s);
                        break;
                    case 2:
                        alarmBot.setBugReopenCondition(s);
                        break;
                    case 3:
                        alarmBot.setBugRateCondition(s);
                        break;
                    case 4:
                        alarmBot.setSubmitCondition(s);
                        break;
                    case 5:
                        alarmBot.setCodeCondition(s);
                    default:
                }
            });
        }
        alarmBot.setContent(bot.getContent());
        alarmBot.setCron(bot.getCron());
        alarmBot.setTriggerType(bot.getTriggerType());
        alarmBot.setStatus(bot.isStatus() ? 1 : 0);
        alarmBot.setAlarmType(bot.getAlarmType());
        if (StringUtils.isNotBlank(bot.getCron())) {
            alarmBot.setNextTriggerTime(JobScheduleUtil.generateNextValidTime(bot.getCron(), new Date(System.currentTimeMillis() + JobScheduleUtil.INTERVALTIME)).getTime());
        }
        if (ObjectUtil.isNotNull(alarmBot.getId())) {
//            xxlJobService.updateJob(jobInfo);
            alarmBotRepository.updateNull(alarmBot.getId());
        } else {
            //新增
            alarmBot.setId(UUID.randomUUID().toString());
            alarmBot.setCreateTime(LocalDateTime.now());
//            String s = xxlJobService.addJob(jobInfo);
        }
        alarmBotRepository.saveOrUpdate(alarmBot);
    }

    public Page<AlarmBotVO> botPage(BotQuery query) {
        Page<AlarmBot> alarmBotPage = alarmBotRepository.selectPage(query);
        Page<AlarmBotVO> bots = new Page<>(alarmBotPage.getCurrent(), alarmBotPage.getSize());
        bots.setRecords(alarmBotPage.getRecords().stream().map(bot -> {
            AlarmBotVO alarmBot = new AlarmBotVO();
            try {
                alarmBot.setId(bot.getId());
                alarmBot.setBotUrl(bot.getBotUrl());
                alarmBot.setAlarmObject(StringUtils.isNotBlank(bot.getAlarmObject()) ? JSONObject.parseObject(bot.getAlarmObject(), AlarmBotVO.ObjectInfo.class) : null);
                alarmBot.setAlarmAll(bot.getAlarmAll());
                List<AlarmBotVO.Condition> conditions = new ArrayList<>();
                conditions.add(StringUtils.isNotBlank(bot.getBugCountCondition()) ? JSONObject.parseObject(bot.getBugCountCondition(), AlarmBotVO.Condition.class) : null);
                conditions.add(StringUtils.isNotBlank(bot.getBugReopenCondition()) ? JSONObject.parseObject(bot.getBugReopenCondition(), AlarmBotVO.Condition.class) : null);
                conditions.add(StringUtils.isNotBlank(bot.getBugRateCondition()) ? JSONObject.parseObject(bot.getBugRateCondition(), AlarmBotVO.Condition.class) : null);
                conditions.add(StringUtils.isNotBlank(bot.getSubmitCondition()) ? JSONObject.parseObject(bot.getSubmitCondition(), AlarmBotVO.Condition.class) : null);
                conditions.add(StringUtils.isNotBlank(bot.getCodeCondition()) ? JSONObject.parseObject(bot.getCodeCondition(), AlarmBotVO.Condition.class) : null);
                alarmBot.setAlarmConditions(conditions.stream().filter(ObjectUtil::isNotNull).collect(Collectors.toList()));
                alarmBot.setContent(bot.getContent());
                alarmBot.setCron(bot.getCron());
                alarmBot.setTriggerType(bot.getTriggerType());
                alarmBot.setAlarmType(bot.getAlarmType());
                alarmBot.setStatus(bot.getStatus() == 1);
            } catch (Exception e) {
                return null;
            }
            return alarmBot;
        }).filter(ObjectUtil::isNotNull).collect(Collectors.toList()));
        bots.setTotal(alarmBotPage.getTotal());
        return bots;
    }

    public void chanceStatus(String id, boolean status) {
        AlarmBot alarmBot = alarmBotRepository.selectById(id);
        AssertUtil.isFalse(ObjectUtil.isNull(alarmBot), ResponseCode.BOT_NOT_EXIST);
//        if (status) {
//            xxlJobService.startJob(String.valueOf(alarmBot.getXxlJobId()));
//        } else {
//            xxlJobService.pauseJob(String.valueOf(alarmBot.getXxlJobId()));
//        }
        if (status && StringUtils.isNotBlank(alarmBot.getCron())) {
            //启动
            alarmBot.setNextTriggerTime(JobScheduleUtil.generateNextValidTime(alarmBot.getCron(), new Date(System.currentTimeMillis() + JobScheduleUtil.INTERVALTIME)).getTime());
        }
        alarmBot.setStatus(status ? 1 : 0);
        alarmBotRepository.updateById(alarmBot);
    }

    public void deleteBot(String id) {
        AlarmBot alarmBot = alarmBotRepository.selectById(id);
        AssertUtil.isFalse(ObjectUtil.isNull(alarmBot), ResponseCode.BOT_NOT_EXIST);
//        xxlJobService.removeJob(String.valueOf(alarmBot.getXxlJobId()));
        alarmBotRepository.deleteById(id);
    }
}
