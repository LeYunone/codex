package com.leyunone.codex.model.bo;


public class BranchesBO {
    
    private Integer projectId;
    
    private String branchName;

    public Integer getProjectId() {
        return projectId;
    }

    public BranchesBO setProjectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getBranchName() {
        return branchName;
    }

    public BranchesBO setBranchName(String branchName) {
        this.branchName = branchName;
        return this;
    }
}
