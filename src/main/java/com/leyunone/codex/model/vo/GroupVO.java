package com.leyunone.codex.model.vo;

import lombok.Data;

import java.util.List;

public class GroupVO {
    
    private Integer groupId;
    
    private String groupName;
    
    private List<GroupUserVO> groupUsers;

    public Integer getGroupId() {
        return groupId;
    }

    public GroupVO setGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupVO setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public List<GroupUserVO> getGroupUsers() {
        return groupUsers;
    }

    public GroupVO setGroupUsers(List<GroupUserVO> groupUsers) {
        this.groupUsers = groupUsers;
        return this;
    }
}
