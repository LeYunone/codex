package com.leyunone.codex.rule.expression;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.codex.dao.impl.BugRepository;
import com.leyunone.codex.dao.impl.CommitRepository;
import com.leyunone.codex.model.dto.AlarmBotDTO;
import com.leyunone.codex.model.query.CodeTimeQuery;
import com.leyunone.codex.model.vo.UserVO;
import com.leyunone.codex.rule.bean.AlarmResult;
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
public class CodeCountExpression extends AlarmExpressionHandler {

    private final BugRepository bugRepository;
    private final CommitRepository commitRepository;

    public CodeCountExpression(BugRepository bugRepository, CommitRepository commitRepository) {
        this.bugRepository = bugRepository;
        this.commitRepository = commitRepository;
    }

    @Override
    public boolean checkExpression(AlarmResult alarmResult, AlarmBotDTO.Condition condition) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String nowDate = LocalDateTime.now().format(dateTimeFormatter);
        //代码函数预警
        String startDate = LocalDateTime.now().plusDays(-condition.getDay()).format(dateTimeFormatter);

        CodeTimeQuery codeTimeQuery = new CodeTimeQuery();
        codeTimeQuery.setStartDate(startDate);
        codeTimeQuery.setEndDate(nowDate);
        codeTimeQuery.setLimit(condition.getLimit());
        List<UserVO> userVOS = commitRepository.selectByUser(codeTimeQuery);
        Map<String, List<UserVO>> codeMap = userVOS.stream().collect(Collectors.groupingBy(UserVO::getRealUserName));
        Set<String> users = codeMap.keySet().stream().filter(key -> {
            List<UserVO> currentCode = codeMap.get(key);
            if (CollectionUtil.isNotEmpty(currentCode)) {
                long sum = currentCode.stream().mapToLong(UserVO::getCodeTotal).sum();
                if (condition.getConType() == 1) {
                    return sum == Long.parseLong(condition.getCondition());
                }
                if (condition.getConType() == 2) {
                    return sum > Long.parseLong(condition.getCondition());
                }
                if (condition.getConType() == 3) {
                    return sum < Long.parseLong(condition.getCondition());
                }
                return false;
            }
            return false;
        }).collect(Collectors.toSet());
        boolean isAlarm = CollectionUtil.isNotEmpty(users);
        String c = condition.getConType() == 1 ? "=" : condition.getConType() == 2 ? ">" : "<";

        if (isAlarm) {
            alarmResult.getAlarmData().add("监测代码行数" + c + condition.getCondition());
            alarmResult.getAlarmUser().addAll(users);
        }
        return isAlarm;
    }

    @Override
    public String getKey() {
        return "5";
    }
}
