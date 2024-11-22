package com.leyunone.codex.model.bo;

import lombok.Data;

import java.util.List;

public class GroupBO {

    private Integer groupId;
    
    private String groupName;
    
    private List<String> userNames;
    
    private String userName;
    
    private String userId;
    
    private List<String> groups;

    public Integer getGroupId() {
        return groupId;
    }

    public GroupBO setGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupBO setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public List<String> getUserNames() {
        return userNames;
    }

    public GroupBO setUserNames(List<String> userNames) {
        this.userNames = userNames;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public GroupBO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public GroupBO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public List<String> getGroups() {
        return groups;
    }

    public GroupBO setGroups(List<String> groups) {
        this.groups = groups;
        return this;
    }
}
