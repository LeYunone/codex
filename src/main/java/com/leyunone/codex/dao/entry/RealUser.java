package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 14:49
 */
@Data
@TableName("x_real_user")
public class RealUser {
    
    @TableId(type = IdType.AUTO,value = "real_user_id")
    private Integer realUserId;
    
    private String realUserName;
    
    private String realEmail;

    @TableField(exist = false)
    private String groupName;
}
