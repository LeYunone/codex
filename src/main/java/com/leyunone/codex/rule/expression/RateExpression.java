package com.leyunone.codex.rule.expression;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.codex.dao.entry.Bug;
import com.leyunone.codex.dao.impl.BugRepository;
import com.leyunone.codex.dao.impl.CommitRepository;
import com.leyunone.codex.model.dto.AlarmBotDTO;
import com.leyunone.codex.model.query.BugQuery;
import com.leyunone.codex.model.query.CodeTimeQuery;
import com.leyunone.codex.model.vo.UserVO;
import com.leyunone.codex.rule.bean.AlarmResult;
import com.leyunone.codex.util.CollectionFunctionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/8 17:52
 */
@Service
public class RateExpression extends AlarmExpressionHandler {

    private final BugRepository bugRepository;
    private final CommitRepository commitRepository;

    public RateExpression(BugRepository bugRepository, CommitRepository commitRepository) {
        this.bugRepository = bugRepository;
        this.commitRepository = commitRepository;
    }

    @Override
    public boolean checkExpression(AlarmResult alarmResult, AlarmBotDTO.Condition condition) {
        //缺陷率预警
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String startDate = LocalDateTime.now().plusDays(-condition.getDay()).format(dateTimeFormatter);
        String nowDate = LocalDateTime.now().format(dateTimeFormatter);

        BugQuery query = new BugQuery();
        query.setStartDate(startDate);
        query.setEndDate(nowDate);
        List<Bug> bugs = bugRepository.selectUserList(query);
        Map<String, Bug> bugMap = CollectionFunctionUtils.mapTo(bugs, Bug::getResolveName);
        CodeTimeQuery codeTimeQuery = new CodeTimeQuery();
        codeTimeQuery.setStartDate(startDate);
        codeTimeQuery.setEndDate(nowDate);
        codeTimeQuery.setLimit(1000);
        codeTimeQuery.setRealUserNames(CollectionUtil.newArrayList(bugMap.keySet()));
        List<UserVO> userVOS = commitRepository.selectByUser(codeTimeQuery);
        Map<String, List<UserVO>> userMap = CollectionFunctionUtils.groupTo(userVOS, UserVO::getRealUserName);
        Set<String> users = userMap.keySet().stream().filter(u -> {
            long sum = userMap.get(u).stream().mapToLong(UserVO::getCodeTotal).sum();
            Bug bug = bugMap.get(u);
            double rate = bug.getCount() / (sum / 1000.0);
            if (condition.getConType() == 1) {
                return rate == Double.parseDouble(condition.getCondition());
            }
            if (condition.getConType() == 2) {
                return rate > Double.parseDouble(condition.getCondition());
            }
            if (condition.getConType() == 3) {
                return rate < Double.parseDouble(condition.getCondition());
            }
            return false;
        }).collect(Collectors.toSet());
        boolean isAlarm = CollectionUtil.isNotEmpty(users);
        String c = condition.getConType() == 1 ? "=" : condition.getConType() == 2 ? ">" : "<";

        if (isAlarm) {
            alarmResult.getAlarmData().add("监测缺陷率" + c + condition.getCondition());
            users.removeIf(t -> !alarmResult.getSeeUser().contains(t));

            alarmResult.getAlarmUser().addAll(users);
            if (CollectionUtil.isNotEmpty(users)) {
                alarmResult.getRealProjectNames().addAll(bugRepository.selectProjectByUser(startDate, CollectionUtil.newArrayList(users)));
            }
        }
        return isAlarm;
    }

    @Override
    public String getKey() {
        return "3";
    }
}
