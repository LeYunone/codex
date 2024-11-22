package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/6 9:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("x_bug")
public class Bug {

    @TableId(value = "bug_id")
    private String bugId;

    private String bugTitle;

    private String department;

    private String bugType;

    private String level;

    private String status;

    private String creator;

    private String createTime;

    private String resolveName;

    private String endTime;

    private String bugReopen;

    private String bugReopenMonth;

    private String bugType2;

    private String projectName;

    private String belongTo = "";

    private String bugVersion;

    private String probability;

    private String fixVersion;

    private String module = "";

    @TableField(exist = false)
    private Integer count;

    @TableField(exist = false)
    private Integer fixCount;

    @TableField(exist = false)
    private Integer reopenCount;
}
