package com.leyunone.codex.model.query;


import java.util.List;

public class CommitQuery extends PageCommonQuery{

    private String realUserName;

    private String projectId;

    private String startDate;

    private String endDate;

    private Integer type;

    private Integer storageId;

    private Integer realUserId;

    private List<String> userIds;

    private Integer groupId;

    private String commitId;

    private Integer limit = 1000;

    public String getRealUserName() {
        return realUserName;
    }

    public CommitQuery setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public CommitQuery setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public CommitQuery setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public CommitQuery setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public CommitQuery setType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public CommitQuery setStorageId(Integer storageId) {
        this.storageId = storageId;
        return this;
    }

    public Integer getRealUserId() {
        return realUserId;
    }

    public CommitQuery setRealUserId(Integer realUserId) {
        this.realUserId = realUserId;
        return this;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public CommitQuery setUserIds(List<String> userIds) {
        this.userIds = userIds;
        return this;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public CommitQuery setGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getCommitId() {
        return commitId;
    }

    public CommitQuery setCommitId(String commitId) {
        this.commitId = commitId;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public CommitQuery setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }
}
