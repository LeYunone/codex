package com.leyunone.codex.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 15:09
 */
public class RealUserQuery extends PageCommonQuery {

    private String realUserName;
    
    private Integer groupId;
    
    private List<Integer> realUserIds;

    public Integer getGroupId() {
        return groupId;
    }

    public RealUserQuery setGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getRealUserName() {
        return realUserName;
    }

    public RealUserQuery setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }

    public List<Integer> getRealUserIds() {
        return realUserIds;
    }

    public RealUserQuery setRealUserIds(List<Integer> realUserIds) {
        this.realUserIds = realUserIds;
        return this;
    }
}
