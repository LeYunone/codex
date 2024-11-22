package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("x_real_project_relation")
public class RealProjectRelation {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String projectId;

    private String realProjectName;
}
