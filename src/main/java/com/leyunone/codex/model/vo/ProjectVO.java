package com.leyunone.codex.model.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

public class ProjectVO {

    private String projectId;

    private String projectName;

    private Date createDate;
    
    private String createStr;
    
    private List<ProjectUserVO> users;
    
    private List<String> realProjectNames;

    public String getProjectId() {
        return projectId;
    }

    public ProjectVO setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public ProjectVO setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public ProjectVO setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getCreateStr() {
        return createStr;
    }

    public ProjectVO setCreateStr(String createStr) {
        this.createStr = createStr;
        return this;
    }

    public List<ProjectUserVO> getUsers() {
        return users;
    }

    public ProjectVO setUsers(List<ProjectUserVO> users) {
        this.users = users;
        return this;
    }

    public List<String> getRealProjectNames() {
        return realProjectNames;
    }

    public ProjectVO setRealProjectNames(List<String> realProjectNames) {
        this.realProjectNames = realProjectNames;
        return this;
    }
}
