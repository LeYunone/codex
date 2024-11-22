package com.leyunone.codex.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.model.ResponseCell;
import com.leyunone.codex.model.bo.BranchesBO;
import com.leyunone.codex.model.bo.CommitBO;
import com.leyunone.codex.model.bo.ProjectBO;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * GITLab API解析服务
 */
public class GitLabAPIService {

    private static final Logger logger = LoggerFactory.getLogger(GitLabAPIService.class);

    private final GitLabApi gitLabApi;

    private GitLabAPIService(GitLabApi gitLabApi){
        this.gitLabApi = gitLabApi;
    }

    public static GitLabAPIService buildGitApiService(GitLabApi gitLabApi){
        return new GitLabAPIService(gitLabApi);
    }

    /**
     * 解析用户下的所有项目
     */
    public ResponseCell<List<ProjectBO>, List<Project>> resoleProjects() {
        //所有项目
        List<Project> projects = null;
        try {
            projects = gitLabApi.getProjectApi().getProjects();
        } catch (GitLabApiException e) {
        }

        List<ProjectBO> projectBOS = new ArrayList<>();
        for (Project project : projects) {
            ProjectBO projectBO = new ProjectBO();
            projectBO.setProjectId(String.valueOf(project.getId()));
            projectBO.setProjectName(project.getName());
            projectBO.setPath(project.getWebUrl());
            projectBO.setCreateDate(project.getCreatedAt());
            projectBOS.add(projectBO);
        }
        return ResponseCell.build(projectBOS, projects);
    }

    /**
     * 解析项目下所有分支
     *
     * @param project 项目
     */
    public List<BranchesBO> resoleBranches(Project project) {
        //项目下的所有分支
        List<ProtectedBranch> protectedBranches = null;
        try {
            protectedBranches = gitLabApi.getProtectedBranchesApi().getProtectedBranches(project);
        } catch (GitLabApiException e) {
            logger.error(e.getMessage());
        }
        if (CollectionUtil.isEmpty(protectedBranches)) return CollectionUtil.newArrayList();

        List<BranchesBO> branchess = new ArrayList<>();
        for (ProtectedBranch protectedBranch : protectedBranches) {
            BranchesBO branchesBO = new BranchesBO();
            branchesBO.setBranchName(protectedBranch.getName());
            branchesBO.setProjectId(project.getId());
            branchess.add(branchesBO);
        }
        return branchess;
    }

    /**
     * 解析分支下的所有提交记录
     *
     * @param projectId  项目id
     * @param brunchName 分支名
     * @param lastDate   开始时间
     */
    public List<CommitBO> resoleCommits(Integer projectId, String brunchName, String lastDate) {
        //项目下的所有提交记录
        List<Commit> allCommits = null;
        try {
            if (StringUtils.isNotBlank(lastDate)) {
                allCommits = gitLabApi.getCommitsApi().getCommits(projectId, brunchName, DateUtil.parse(lastDate,"yyyy-MM"), new Date());
            } else {
                //该项目全提交记录
                allCommits = gitLabApi.getCommitsApi().getCommits(projectId);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (CollectionUtil.isEmpty(allCommits)) return CollectionUtil.newArrayList();

        List<CommitBO> commits = new ArrayList<>();
        for (Commit commit : allCommits) {
            try {
                commit = gitLabApi.getCommitsApi().getCommit(projectId,commit.getShortId());
            }catch (Exception ignored){
            }
            Author author = commit.getAuthor();
            //跳过合并分支的提交
            if(commit.getMessage().startsWith("Merge ")) continue;
            CommitBO commitBO = new CommitBO();
            commitBO.setCommitDate(commit.getCommittedDate());
//            commitBO.setUserName(ObjectUtil.isNotNull(author)?author.getUsername():commit.getAuthorName());
            commitBO.setUserName(commit.getCommitterName());
            commitBO.setCommitterEmail(commit.getAuthorEmail());
            commitBO.setTitle(commit.getTitle());
            commitBO.setCommitId(commit.getId());
            commitBO.setMessage(commit.getMessage());
            commitBO.setBranchesName(brunchName);

            //本次提交的修改记录
            CommitStats stats = commit.getStats();
            if(ObjectUtil.isNotNull(stats)){
                commitBO.setAdditions(stats.getAdditions());
                commitBO.setDeletions(stats.getDeletions());
                commitBO.setTotal(stats.getTotal());
            }else{
                commitBO.setAdditions(0);
                commitBO.setDeletions(0);
                commitBO.setTotal(0);
            }
            commits.add(commitBO);
        }
        return commits;
    }
}
