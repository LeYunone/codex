package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/8 10:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("x_alarm_bot")
public class AlarmBot {
    
    @TableId
    private String id;
    
    private String botUrl;

    /**
     * 报警对象 json
     */
    private String alarmObject;

    /**
     * 是否全部
     */
    private Integer alarmAll;

    /**
     * bug总数    条件 {
     *     condition:"",
     *     conType:"",
     *     content:""
     * }
     */
    private String bugCountCondition;

    /**
     * reopen率
     */
    private String bugReopenCondition;

    /**
     * 缺陷率
     */
    private String bugRateCondition;

    /**
     * 提交频率
     */
    private String submitCondition;
    
    private String codeCondition;

    /**
     */
    private String content;

    /**
     * 执行周期cron表达式
     */
    private String cron;

    /**
     * 0暂停 1启动
     */
    private Integer status;
    
    private LocalDateTime createTime;
    
    private Integer triggerType;
    
    private Integer alarmType;

    //下一个触发时间
    private Long nextTriggerTime;
}
