package com.leyunone.codex.model.vo;

import lombok.*;


public class UserVO {
    
    private String userId;

    private String userName;

    private String userEmail;
    
    private String realUserName;

    private Long codeAdditions;

    private Long codeDeletions;

    private Long codeTotal;
    
    private Integer count;

    public String getUserId() {
        return userId;
    }

    public UserVO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserVO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public UserVO setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getRealUserName() {
        return realUserName;
    }

    public UserVO setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }

    public Long getCodeAdditions() {
        return codeAdditions;
    }

    public UserVO setCodeAdditions(Long codeAdditions) {
        this.codeAdditions = codeAdditions;
        return this;
    }

    public Long getCodeDeletions() {
        return codeDeletions;
    }

    public UserVO setCodeDeletions(Long codeDeletions) {
        this.codeDeletions = codeDeletions;
        return this;
    }

    public Long getCodeTotal() {
        return codeTotal;
    }

    public UserVO setCodeTotal(Long codeTotal) {
        this.codeTotal = codeTotal;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public UserVO setCount(Integer count) {
        this.count = count;
        return this;
    }
}
