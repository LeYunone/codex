package com.leyunone.codex.model.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/3 10:16
 */
@Getter
@Setter
public class BugTopQuery {

    private Integer groupId;

    private String startDate;

    private String realUserName;

    private String bugProjectName;

    private String endDate;

    private String status;

    private Integer bugReopen;

    private String bugType2;

    private String department;

    //1top图  2饼状图
    private Integer type = 1;

    private List<String> realUserNames;

    private Integer size = 5;
    
    private String bugTitle;
    
    private String bugId;

}
