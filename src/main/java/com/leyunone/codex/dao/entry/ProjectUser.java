package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("x_project_user")
public class ProjectUser {

    private String id;

    private String projectId;

    private String userId;

    @TableField(exist = false)
    private String projectName;

    @TableField(exist = false)
    private String realUserName;

    @TableField(exist = false)
    private Integer realUserId;
}
