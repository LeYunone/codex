package com.leyunone.codex.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 14:44
 */
public class RealUserRelationVO {
    
    private Integer realUserId;

    private String userId;
    
    private String userName;
    
    private String userEmail;

    public Integer getRealUserId() {
        return realUserId;
    }

    public RealUserRelationVO setRealUserId(Integer realUserId) {
        this.realUserId = realUserId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RealUserRelationVO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public RealUserRelationVO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public RealUserRelationVO setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }
}
