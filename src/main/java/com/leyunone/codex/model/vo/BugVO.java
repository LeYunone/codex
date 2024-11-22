package com.leyunone.codex.model.vo;

import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/6 10:13
 */
public class BugVO {

    private String name;

    private int bugNumber;

    private int fixNumber;

    private int doingNumber;

    private int reopenNumber;
    
    private long codeTotal;

    private long deletions;

    private long additions;
    
    private Integer groupId;

    public String getName() {
        return name;
    }

    public BugVO setName(String name) {
        this.name = name;
        return this;
    }

    public int getBugNumber() {
        return bugNumber;
    }

    public BugVO setBugNumber(int bugNumber) {
        this.bugNumber = bugNumber;
        return this;
    }

    public int getFixNumber() {
        return fixNumber;
    }

    public BugVO setFixNumber(int fixNumber) {
        this.fixNumber = fixNumber;
        return this;
    }

    public int getDoingNumber() {
        return doingNumber;
    }

    public BugVO setDoingNumber(int doingNumber) {
        this.doingNumber = doingNumber;
        return this;
    }

    public int getReopenNumber() {
        return reopenNumber;
    }

    public BugVO setReopenNumber(int reopenNumber) {
        this.reopenNumber = reopenNumber;
        return this;
    }

    public long getCodeTotal() {
        return codeTotal;
    }

    public BugVO setCodeTotal(long codeTotal) {
        this.codeTotal = codeTotal;
        return this;
    }

    public long getDeletions() {
        return deletions;
    }

    public BugVO setDeletions(long deletions) {
        this.deletions = deletions;
        return this;
    }

    public long getAdditions() {
        return additions;
    }

    public BugVO setAdditions(long additions) {
        this.additions = additions;
        return this;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public BugVO setGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }
}
