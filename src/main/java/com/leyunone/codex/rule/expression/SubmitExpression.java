package com.leyunone.codex.rule.expression;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.codex.dao.entry.RealUser;
import com.leyunone.codex.dao.impl.BugRepository;
import com.leyunone.codex.dao.impl.CommitRepository;
import com.leyunone.codex.dao.impl.RealUserRepository;
import com.leyunone.codex.model.dto.AlarmBotDTO;
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
public class SubmitExpression extends AlarmExpressionHandler {

    private final BugRepository bugRepository;
    private final CommitRepository commitRepository;
    private final RealUserRepository realUserRepository;

    public SubmitExpression(BugRepository bugRepository, CommitRepository commitRepository, RealUserRepository realUserRepository) {
        this.bugRepository = bugRepository;
        this.commitRepository = commitRepository;
        this.realUserRepository = realUserRepository;
    }

    @Override
    public boolean checkExpression(AlarmResult alarmResult, AlarmBotDTO.Condition condition) {
        //提交频率预警
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String startDate = LocalDateTime.now().plusDays(-condition.getDay()).format(dateTimeFormatter);
        String nowDate = LocalDateTime.now().format(dateTimeFormatter);

        List<RealUser> realUsers = realUserRepository.selectByNames(CollectionUtil.newArrayList(alarmResult.getSeeUser()));
        CodeTimeQuery codeTimeQuery = new CodeTimeQuery();
        codeTimeQuery.setStartDate(startDate);
        codeTimeQuery.setEndDate(nowDate);
        codeTimeQuery.setRealUserNames(CollectionUtil.newArrayList(realUsers.stream().map(RealUser::getRealUserName).collect(Collectors.toList())));
        List<UserVO> userVOS = commitRepository.selectByUser(codeTimeQuery);
        Map<String, UserVO> userMap = CollectionFunctionUtils.mapTo(userVOS, UserVO::getRealUserName);
        Set<String> users = realUsers.stream().filter(u -> {
            if (userMap.containsKey(u.getRealUserName())) {
                UserVO userVO = userMap.get(u.getRealUserName());
                if (condition.getConType() == 1) {
                    return userVO.getCount() == Integer.parseInt(condition.getCondition());
                }
                if (condition.getConType() == 2) {
                    return userVO.getCount() > Integer.parseInt(condition.getCondition());
                }
                if (condition.getConType() == 3) {
                    return userVO.getCount() < Integer.parseInt(condition.getCondition());
                }
            }
            return true;
        }).map(RealUser::getRealUserName).collect(Collectors.toSet());
        String c = condition.getConType() == 1 ? "=" : condition.getConType() == 2 ? ">" : "<";
        boolean isAlarm = CollectionUtil.isNotEmpty(users);
        if (isAlarm) {
            alarmResult.getAlarmData().add("监测提交频率" + c + condition.getCondition());
            alarmResult.getAlarmUser().addAll(users);
        }
        return isAlarm;
    }

    @Override
    public String getKey() {
        return "4";
    }
}
