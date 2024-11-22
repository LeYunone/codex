package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("x_group")
public class Group {

    @TableId(value = "group_id",type = IdType.AUTO)
    private Integer groupId;
    
    private String groupName;
    
    @TableField(exist = false)
    private String realUserName;
    
    @TableField(exist = false)
    private Integer realUserId;
}
