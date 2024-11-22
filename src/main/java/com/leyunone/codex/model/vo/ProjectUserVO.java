package com.leyunone.codex.model.vo;

import lombok.Data;

public class ProjectUserVO {
    /**
     * 人员真实姓名
     */
    private String realUserName;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 项目名
     */
    private String projectId;
    
    private String realUserId;
    
    private String userEmail;
    
    private String userId;
    
    private String userName;

    public String getRealUserName() {
        return realUserName;
    }

    public ProjectUserVO setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public ProjectUserVO setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public ProjectUserVO setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getRealUserId() {
        return realUserId;
    }

    public ProjectUserVO setRealUserId(String realUserId) {
        this.realUserId = realUserId;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ProjectUserVO setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public ProjectUserVO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public ProjectUserVO setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
