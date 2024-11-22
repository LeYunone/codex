package com.leyunone.codex.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/29 10:52
 */
public class ProjectRelationVO {

    private String realProjectName;
    
    private String department;
    
    private List<String> projectIds;
    
    private List<String> projectNames;
    
    private List<String> realUserNames;

    public String getRealProjectName() {
        return realProjectName;
    }

    public ProjectRelationVO setRealProjectName(String realProjectName) {
        this.realProjectName = realProjectName;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public ProjectRelationVO setDepartment(String department) {
        this.department = department;
        return this;
    }

    public List<String> getProjectIds() {
        return projectIds;
    }

    public ProjectRelationVO setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
        return this;
    }

    public List<String> getProjectNames() {
        return projectNames;
    }

    public ProjectRelationVO setProjectNames(List<String> projectNames) {
        this.projectNames = projectNames;
        return this;
    }

    public List<String> getRealUserNames() {
        return realUserNames;
    }

    public ProjectRelationVO setRealUserNames(List<String> realUserNames) {
        this.realUserNames = realUserNames;
        return this;
    }
}
