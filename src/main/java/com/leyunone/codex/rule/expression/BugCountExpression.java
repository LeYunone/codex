package com.leyunone.codex.rule.expression;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.codex.dao.entry.Bug;
import com.leyunone.codex.dao.impl.BugRepository;
import com.leyunone.codex.model.dto.AlarmBotDTO;
import com.leyunone.codex.rule.bean.AlarmResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/8 17:52
 */
@Service
public class BugCountExpression extends AlarmExpressionHandler {

    private final BugRepository bugRepository;

    public BugCountExpression(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    @Override
    public boolean checkExpression(AlarmResult alarmResult, AlarmBotDTO.Condition condition) {
        //数量预警
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String startDate = LocalDateTime.now().plusDays(-condition.getDay()).format(dateTimeFormatter);
        List<Bug> bugs = bugRepository.selectCountAlarmGroupByUser(startDate, Integer.parseInt(condition.getCondition()), condition.getConType());
        String c = condition.getConType() == 1 ? "=" : condition.getConType() == 2 ? ">" : "<";
        boolean isAlarm = CollectionUtil.isNotEmpty(bugs);
        if (isAlarm) {
            Set<String> users = bugs.stream().map(Bug::getResolveName).collect(Collectors.toSet());
            users.removeIf(t -> !alarmResult.getSeeUser().contains(t));
            alarmResult.getAlarmData().add("监测Bug数量" + c + condition.getCondition());
            alarmResult.getAlarmUser().addAll(users);
            if (CollectionUtil.isNotEmpty(users)) {
                alarmResult.getRealProjectNames().addAll(bugRepository.selectProjectByUser(startDate, CollectionUtil.newArrayList(users)));
            }
        }
        return isAlarm;
    }

    @Override
    public String getKey() {
        return "1";
    }
}
