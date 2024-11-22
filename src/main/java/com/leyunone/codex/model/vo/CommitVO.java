package com.leyunone.codex.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

public class CommitVO {
    
    private String userId;
    
    private String userName;
    
    private Long total;
    
    private LocalDateTime commitDate;

    /**
     * 日期
     */
    private String date;
    
    private Long addition;
    
    private Long deletions;
    
    private String projectName;
    
    private String projectId;
    
    private String realUserName;

    public String getUserId() {
        return userId;
    }

    public CommitVO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CommitVO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public CommitVO setTotal(Long total) {
        this.total = total;
        return this;
    }

    public LocalDateTime getCommitDate() {
        return commitDate;
    }

    public CommitVO setCommitDate(LocalDateTime commitDate) {
        this.commitDate = commitDate;
        return this;
    }

    public String getDate() {
        return date;
    }

    public CommitVO setDate(String date) {
        this.date = date;
        return this;
    }

    public Long getAddition() {
        return addition;
    }

    public CommitVO setAddition(Long addition) {
        this.addition = addition;
        return this;
    }

    public Long getDeletions() {
        return deletions;
    }

    public CommitVO setDeletions(Long deletions) {
        this.deletions = deletions;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public CommitVO setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public CommitVO setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getRealUserName() {
        return realUserName;
    }

    public CommitVO setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }
}
