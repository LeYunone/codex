package com.leyunone.codex.model.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/8 10:39
 */
public class CodeQuery extends PageCommonQuery{
    
    private String realUserName;
    
    private Integer realUserId;

    private Integer groupId;

    private String startDate;

    private String endDate;

    private Integer limit = 1000;
    
    private List<String> userIds;
    
    private String projectId;
    
    private List<Integer> realUserIds;

    public String getRealUserName() {
        return realUserName;
    }

    public CodeQuery setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }

    public Integer getRealUserId() {
        return realUserId;
    }

    public CodeQuery setRealUserId(Integer realUserId) {
        this.realUserId = realUserId;
        return this;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public CodeQuery setGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public CodeQuery setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public CodeQuery setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public CodeQuery setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public CodeQuery setUserIds(List<String> userIds) {
        this.userIds = userIds;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public CodeQuery setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public List<Integer> getRealUserIds() {
        return realUserIds;
    }

    public CodeQuery setRealUserIds(List<Integer> realUserIds) {
        this.realUserIds = realUserIds;
        return this;
    }
}
