package com.leyunone.codex.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

public class GroupUserVO {
    
    private Integer realUserId;

    private String groupName;

    private Integer groupId;
    
    private Integer codeTotal;
    
    private LocalDateTime commitDate;
    
    private Long total;
    
    private String date;
    
    private String userName;
    
    private Integer id;
    
    private Long codeAdditions;
    
    private Long codeDeletions;

    public Integer getRealUserId() {
        return realUserId;
    }

    public GroupUserVO setRealUserId(Integer realUserId) {
        this.realUserId = realUserId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupUserVO setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public GroupUserVO setGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    public Integer getCodeTotal() {
        return codeTotal;
    }

    public GroupUserVO setCodeTotal(Integer codeTotal) {
        this.codeTotal = codeTotal;
        return this;
    }

    public LocalDateTime getCommitDate() {
        return commitDate;
    }

    public GroupUserVO setCommitDate(LocalDateTime commitDate) {
        this.commitDate = commitDate;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public GroupUserVO setTotal(Long total) {
        this.total = total;
        return this;
    }

    public String getDate() {
        return date;
    }

    public GroupUserVO setDate(String date) {
        this.date = date;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public GroupUserVO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public GroupUserVO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Long getCodeAdditions() {
        return codeAdditions;
    }

    public GroupUserVO setCodeAdditions(Long codeAdditions) {
        this.codeAdditions = codeAdditions;
        return this;
    }

    public Long getCodeDeletions() {
        return codeDeletions;
    }

    public GroupUserVO setCodeDeletions(Long codeDeletions) {
        this.codeDeletions = codeDeletions;
        return this;
    }
}
