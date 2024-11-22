package com.leyunone.codex.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class ProjectUserQuery extends PageCommonQuery {

    private String projectId;
    
    private String userName;
    
    private Integer storageId;
    
    private Integer type;
    
    private String userId;
    
    private String projectName;
    
    private Integer orderType = 2;
    
    private String startDate;
    
    private Integer realUserId;
    
    private List<String> userIds;

    public String getProjectId() {
        return projectId;
    }

    public ProjectUserQuery setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public ProjectUserQuery setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public ProjectUserQuery setStorageId(Integer storageId) {
        this.storageId = storageId;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public ProjectUserQuery setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public ProjectUserQuery setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public ProjectUserQuery setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public ProjectUserQuery setOrderType(Integer orderType) {
        this.orderType = orderType;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public ProjectUserQuery setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public Integer getRealUserId() {
        return realUserId;
    }

    public ProjectUserQuery setRealUserId(Integer realUserId) {
        this.realUserId = realUserId;
        return this;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public ProjectUserQuery setUserIds(List<String> userIds) {
        this.userIds = userIds;
        return this;
    }
}
