package com.leyunone.codex.model.vo;

import com.leyunone.codex.model.dto.AlarmBotDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/11 10:22
 */
@Data
public class AlarmBotVO {

    private String id;

    private String botUrl;

    /**
     * 报警对象 json
     */
    private ObjectInfo alarmObject;

    /**
     * 是否全部
     */
    private Integer alarmAll;

    private List<Condition> alarmConditions;

    private String content;

    /**
     * 执行周期cron表达式
     */
    private String cron;

    private boolean status;

    private Integer triggerType;
    
    private Integer alarmType;
    @Data
    public static class Condition {

        private String condition;
        //1：等于，2：大于，3：小于，
        private Integer conType;

        //1bug总数条件对象  2reopen率条件对象  3缺陷率条件对象  4提交频率条件对象 5代码行数
        private Integer key;

        /**
         * 多少天内
         */
        private Integer day;

        private Integer limit = 1000;
    }
    @Data
    public static class ObjectInfo {

        private List<Integer> groupIds;

        private List<String> realProjectNames;

        private List<String> users;
    }
}
