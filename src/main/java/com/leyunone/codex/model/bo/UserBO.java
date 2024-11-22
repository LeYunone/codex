package com.leyunone.codex.model.bo;


import java.util.List;

public class UserBO {
    
    private String userId;
    
    private String userName;
    
    private String userEmail;
    
    private String realUserName;
    
    private Integer storageId;
    
    private List<Integer> groups;

    public String getUserId() {
        return userId;
    }

    public UserBO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserBO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public UserBO setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getRealUserName() {
        return realUserName;
    }

    public UserBO setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public UserBO setStorageId(Integer storageId) {
        this.storageId = storageId;
        return this;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public UserBO setGroups(List<Integer> groups) {
        this.groups = groups;
        return this;
    }
}
