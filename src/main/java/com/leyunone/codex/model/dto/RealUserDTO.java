package com.leyunone.codex.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 14:44
 */
@Getter
@Setter
public class RealUserDTO {

    private Integer realUserId;
    
    private String realUserName;
    
    private String realEmail;
 
    private List<String> userIds;

    private List<Integer> groups;

    public Integer getRealUserId() {
        return realUserId;
    }

    public RealUserDTO setRealUserId(Integer realUserId) {
        this.realUserId = realUserId;
        return this;
    }

    public String getRealUserName() {
        return realUserName;
    }

    public RealUserDTO setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }

    public String getRealEmail() {
        return realEmail;
    }

    public RealUserDTO setRealEmail(String realEmail) {
        this.realEmail = realEmail;
        return this;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public RealUserDTO setUserIds(List<String> userIds) {
        this.userIds = userIds;
        return this;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public RealUserDTO setGroups(List<Integer> groups) {
        this.groups = groups;
        return this;
    }
}
