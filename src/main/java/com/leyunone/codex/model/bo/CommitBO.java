package com.leyunone.codex.model.bo;

import lombok.*;

import java.util.Date;

public class CommitBO {
 
    private Date commitDate;

    private String committerEmail;
    
    private String userName;
    
    private String title;
    
    private String commitId;
    
    private String projectId;
    
    private String message;
    
    /**
     * 新增
     */
    private Integer additions;

    /**
     * 刪除
     */
    private Integer deletions;

    /**
     * 总数
     */
    private Integer total;
    
    private Integer storageId;
    
    private String userId;
    
    private String branchesName;

    public Date getCommitDate() {
        return commitDate;
    }

    public CommitBO setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
        return this;
    }

    public String getCommitterEmail() {
        return committerEmail;
    }

    public CommitBO setCommitterEmail(String committerEmail) {
        this.committerEmail = committerEmail;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CommitBO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public CommitBO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCommitId() {
        return commitId;
    }

    public CommitBO setCommitId(String commitId) {
        this.commitId = commitId;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public CommitBO setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommitBO setMessage(String message) {
        this.message = message;
        return this;
    }

    public Integer getAdditions() {
        return additions;
    }

    public CommitBO setAdditions(Integer additions) {
        this.additions = additions;
        return this;
    }

    public Integer getDeletions() {
        return deletions;
    }

    public CommitBO setDeletions(Integer deletions) {
        this.deletions = deletions;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public CommitBO setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public CommitBO setStorageId(Integer storageId) {
        this.storageId = storageId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public CommitBO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getBranchesName() {
        return branchesName;
    }

    public CommitBO setBranchesName(String branchesName) {
        this.branchesName = branchesName;
        return this;
    }
}
