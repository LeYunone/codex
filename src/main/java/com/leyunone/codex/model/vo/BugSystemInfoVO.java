package com.leyunone.codex.model.vo;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/21 11:34
 */
public class BugSystemInfoVO {

    private List<String> projectName;
    
    private Set<String> department;

    public List<String> getProjectName() {
        return projectName;
    }

    public BugSystemInfoVO setProjectName(List<String> projectName) {
        this.projectName = projectName;
        return this;
    }

    public Set<String> getDepartment() {
        return department;
    }

    public BugSystemInfoVO setDepartment(Set<String> department) {
        this.department = department;
        return this;
    }
}
