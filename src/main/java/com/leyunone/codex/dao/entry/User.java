package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("x_user")
public class User {

    @TableId(value = "user_id")
    private String userId;
    
    private String userName;

    private String userEmail;
    
    private String realUserName;

    private Integer storageId;
}
