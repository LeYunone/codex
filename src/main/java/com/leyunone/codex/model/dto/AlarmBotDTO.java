package com.leyunone.codex.model.dto;

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
public class AlarmBotDTO {

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

        public String getCondition() {
            return condition;
        }

        public Condition setCondition(String condition) {
            this.condition = condition;
            return this;
        }

        public Integer getConType() {
            return conType;
        }

        public Condition setConType(Integer conType) {
            this.conType = conType;
            return this;
        }

        public Integer getKey() {
            return key;
        }

        public Condition setKey(Integer key) {
            this.key = key;
            return this;
        }

        public Integer getDay() {
            return day;
        }

        public Condition setDay(Integer day) {
            this.day = day;
            return this;
        }

        public Integer getLimit() {
            return limit;
        }

        public Condition setLimit(Integer limit) {
            this.limit = limit;
            return this;
        }
    }

    public static class ObjectInfo {

        private List<Integer> groupIds;

        private List<String> realProjectNames;

        private List<String> users;

        public List<Integer> getGroupIds() {
            return groupIds;
        }

        public ObjectInfo setGroupIds(List<Integer> groupIds) {
            this.groupIds = groupIds;
            return this;
        }

        public List<String> getRealProjectNames() {
            return realProjectNames;
        }

        public ObjectInfo setRealProjectNames(List<String> realProjectNames) {
            this.realProjectNames = realProjectNames;
            return this;
        }

        public List<String> getUsers() {
            return users;
        }

        public ObjectInfo setUsers(List<String> users) {
            this.users = users;
            return this;
        }
    }

    public String getId() {
        return id;
    }

    public AlarmBotDTO setId(String id) {
        this.id = id;
        return this;
    }

    public String getBotUrl() {
        return botUrl;
    }

    public AlarmBotDTO setBotUrl(String botUrl) {
        this.botUrl = botUrl;
        return this;
    }

    public ObjectInfo getAlarmObject() {
        return alarmObject;
    }

    public AlarmBotDTO setAlarmObject(ObjectInfo alarmObject) {
        this.alarmObject = alarmObject;
        return this;
    }

    public Integer getAlarmAll() {
        return alarmAll;
    }

    public AlarmBotDTO setAlarmAll(Integer alarmAll) {
        this.alarmAll = alarmAll;
        return this;
    }

    public List<Condition> getAlarmConditions() {
        return alarmConditions;
    }

    public AlarmBotDTO setAlarmConditions(List<Condition> alarmConditions) {
        this.alarmConditions = alarmConditions;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AlarmBotDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCron() {
        return cron;
    }

    public AlarmBotDTO setCron(String cron) {
        this.cron = cron;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public AlarmBotDTO setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public AlarmBotDTO setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
        return this;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public AlarmBotDTO setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
        return this;
    }
}
