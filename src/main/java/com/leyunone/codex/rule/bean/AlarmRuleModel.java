package com.leyunone.codex.rule.bean;

import com.leyunone.codex.model.dto.AlarmBotDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/8 15:59
 */
@Getter
@Setter
public class AlarmRuleModel {

    private String id;
    
    private boolean hasAlarm;
    
    /**
     * bug总数    条件 {
     *     condition:"",
     *     conType:"",
     *     content:""
     * }
     */
    private AlarmBotDTO.Condition bugCountCondition;

    /**
     * reopen率
     */
    private AlarmBotDTO.Condition bugReopenCondition;

    /**
     * 缺陷率
     */
    private AlarmBotDTO.Condition bugRateCondition;

    /**
     * 提交频率
     */
    private AlarmBotDTO.Condition submitCondition;

    private AlarmBotDTO.Condition codeCondition;
}
