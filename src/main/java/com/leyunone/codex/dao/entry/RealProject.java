package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/29 9:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("x_real_project")
public class RealProject {

    @TableId
    private String realProjectName;
    
    private String department;
    
    private Integer isRelation;
}
