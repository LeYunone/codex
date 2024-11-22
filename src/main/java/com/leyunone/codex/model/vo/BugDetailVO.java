package com.leyunone.codex.model.vo;

import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/6 9:31
 */
public class BugDetailVO {

    private String projectName;
    
    private String department; 
    
    private String bugId;
    
    private String bugTitle;
    
    private String level;
    
    private String status;
    
    private String creator;
    
    private String createTime;
    
    private String resolveName;
    
    private String endTime;
    
    private String bugType;
    
    private String bugType2;

    private String bugReopen;

    private String bugReopenMonth;

    private String belongTo;

    private String bugVersion;

    private String probability;

    private String fixVersion;

    private String module;

    public String getProjectName() {
        return projectName;
    }

    public BugDetailVO setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public BugDetailVO setDepartment(String department) {
        this.department = department;
        return this;
    }

    public String getBugId() {
        return bugId;
    }

    public BugDetailVO setBugId(String bugId) {
        this.bugId = bugId;
        return this;
    }

    public String getBugTitle() {
        return bugTitle;
    }

    public BugDetailVO setBugTitle(String bugTitle) {
        this.bugTitle = bugTitle;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public BugDetailVO setLevel(String level) {
        this.level = level;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public BugDetailVO setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public BugDetailVO setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public BugDetailVO setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getResolveName() {
        return resolveName;
    }

    public BugDetailVO setResolveName(String resolveName) {
        this.resolveName = resolveName;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public BugDetailVO setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getBugType() {
        return bugType;
    }

    public BugDetailVO setBugType(String bugType) {
        this.bugType = bugType;
        return this;
    }

    public String getBugType2() {
        return bugType2;
    }

    public BugDetailVO setBugType2(String bugType2) {
        this.bugType2 = bugType2;
        return this;
    }

    public String getBugReopen() {
        return bugReopen;
    }

    public BugDetailVO setBugReopen(String bugReopen) {
        this.bugReopen = bugReopen;
        return this;
    }

    public String getBugReopenMonth() {
        return bugReopenMonth;
    }

    public BugDetailVO setBugReopenMonth(String bugReopenMonth) {
        this.bugReopenMonth = bugReopenMonth;
        return this;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public BugDetailVO setBelongTo(String belongTo) {
        this.belongTo = belongTo;
        return this;
    }

    public String getBugVersion() {
        return bugVersion;
    }

    public BugDetailVO setBugVersion(String bugVersion) {
        this.bugVersion = bugVersion;
        return this;
    }

    public String getProbability() {
        return probability;
    }

    public BugDetailVO setProbability(String probability) {
        this.probability = probability;
        return this;
    }

    public String getFixVersion() {
        return fixVersion;
    }

    public BugDetailVO setFixVersion(String fixVersion) {
        this.fixVersion = fixVersion;
        return this;
    }

    public String getModule() {
        return module;
    }

    public BugDetailVO setModule(String module) {
        this.module = module;
        return this;
    }
}
