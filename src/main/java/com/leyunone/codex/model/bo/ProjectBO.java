package com.leyunone.codex.model.bo;

import lombok.*;

import java.util.Date;

public class ProjectBO {
    
    private String projectId;
    
    private String projectName;
    
    private String path;
    
    private Date createDate;
    
    private Integer storageId;

    public String getProjectId() {
        return projectId;
    }

    public ProjectBO setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public ProjectBO setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ProjectBO setPath(String path) {
        this.path = path;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public ProjectBO setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public ProjectBO setStorageId(Integer storageId) {
        this.storageId = storageId;
        return this;
    }
}
