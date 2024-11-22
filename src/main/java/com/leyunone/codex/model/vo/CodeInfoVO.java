package com.leyunone.codex.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/8 10:45
 */
public class CodeInfoVO {

    private String realUserName;

    private String groupName;

    private String userId;

    private long total;

    private long deletions;

    private long additions;

    private long projectNumber;

    public String getRealUserName() {
        return realUserName;
    }

    public CodeInfoVO setRealUserName(String realUserName) {
        this.realUserName = realUserName;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public CodeInfoVO setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public CodeInfoVO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public CodeInfoVO setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getDeletions() {
        return deletions;
    }

    public CodeInfoVO setDeletions(long deletions) {
        this.deletions = deletions;
        return this;
    }

    public long getAdditions() {
        return additions;
    }

    public CodeInfoVO setAdditions(long additions) {
        this.additions = additions;
        return this;
    }

    public long getProjectNumber() {
        return projectNumber;
    }

    public CodeInfoVO setProjectNumber(long projectNumber) {
        this.projectNumber = projectNumber;
        return this;
    }
}
