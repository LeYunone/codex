package com.leyunone.codex.model.query;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/29 10:50
 */
public class ProjectRelationQuery extends PageCommonQuery {

    private String realProjectName;

    public String getRealProjectName() {
        return realProjectName;
    }

    public ProjectRelationQuery setRealProjectName(String realProjectName) {
        this.realProjectName = realProjectName;
        return this;
    }
}
