package com.leyunone.codex.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/29 9:59
 */
@Getter
@Setter
public class ProjectRelationDTO {

    private List<String> projectIds;

    private List<String> realProjectNames;
    
    //0新增   1关联
    private Integer type;

    public List<String> getProjectIds() {
        return projectIds;
    }

    public ProjectRelationDTO setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
        return this;
    }

    public List<String> getRealProjectNames() {
        return realProjectNames;
    }

    public ProjectRelationDTO setRealProjectNames(List<String> realProjectNames) {
        this.realProjectNames = realProjectNames;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public ProjectRelationDTO setType(Integer type) {
        this.type = type;
        return this;
    }
}
