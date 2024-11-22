package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("x_project")
public class Project {
    
    @TableId("project_id")
    private String projectId;
    
    private String projectName;
    
    private String storageId;

    private Date createDate;
    
    private String path;
}
