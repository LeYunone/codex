package com.leyunone.codex.model.query;

import lombok.Data;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/6 9:16
 */
@Data
public class BugQuery extends PageCommonQuery{
    
    private String bugType2;

    private List<String> userNames;
    
    private String realUserName;

    private Integer groupId;
    
    private String projectId;
    
    private String bugProjectName;
    
    private String projectName;
    
    private String startDate;

    private String endDate;

    private Integer limit = 1000;
    
    private String bugReopen;
    
    private String status;

    /**
     * 0人员 1部门 2项目
     */
    private Integer queryType;
    
    private String department;

    private Integer sortField = 1;

    /**
     * 排序方式 1：正序 2：倒序
     */
    private Integer sortMode = 2;
    
    private List<String> realUserNames;

    /**
     * 0项目占比 1事业部占比 4等级  5状态 7负责人 8类型 9归属 模块
     */
    private Integer statisticsType;
    
    private String bugId;
    
    private String bugTitle;
    
    //版本前缀
    private String versionPrefix;
    //版本后缀
    private String versionSuffix;

    //1top图  2饼状图
    private Integer type = 1;

}
