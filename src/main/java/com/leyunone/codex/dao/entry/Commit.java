package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("x_commit")
public class Commit {

    private Date commitDate;

    private String committerEmail;

    private String userName;

    private String title;

    @TableId(value = "commit_id")
    private String commitId;

    private String projectId;

    private String message;

    /**
     * 新增
     */
    private Integer additions;

    /**
     * 刪除
     */
    private Integer deletions;

    /**
     * 总数
     */
    private Integer total;
    
    private String storageId;
    
    private String userId;
    
    @TableField(exist = false)
    private String commitDateStr;
    
    @TableField(exist = false)
    private String projectName;
}
