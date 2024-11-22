package com.leyunone.codex.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 14:44
 */
public class RealUserVO {

    private Integer realUserId;
    
    private String realUserName;
    
    private String realEmail;
 
    private List<RealUserRelationVO> gitLabUser;

    private List<GroupUserVO> groupList;

    public Integer getRealUserId() {
        return realUserId;
    }

    public RealUserVO setRealUserId(Integer realUserId) {
        this.realUserId = realUserId;
        return this;
    }

    public String getRealUserName() {
        return realUserName;
    }

    public RealUserVO setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }

    public String getRealEmail() {
        return realEmail;
    }

    public RealUserVO setRealEmail(String realEmail) {
        this.realEmail = realEmail;
        return this;
    }

    public List<RealUserRelationVO> getGitLabUser() {
        return gitLabUser;
    }

    public RealUserVO setGitLabUser(List<RealUserRelationVO> gitLabUser) {
        this.gitLabUser = gitLabUser;
        return this;
    }

    public List<GroupUserVO> getGroupList() {
        return groupList;
    }

    public RealUserVO setGroupList(List<GroupUserVO> groupList) {
        this.groupList = groupList;
        return this;
    }
}
