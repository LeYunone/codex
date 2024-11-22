package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 14:50
 */
@Data
@TableName("x_real_user_relation")
public class RealUserRelation {

    @TableId(value = "relation_id",type = IdType.AUTO)
    private Integer relationId;
    
    private Integer realUserId;
    
    private String userId;
    
    private String realUserName;
}
