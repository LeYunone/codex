package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/6 13:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bug系统的表")
public class BugBI {

    private String 系统;

    @TableField("项目名称")
    private String 项目名称;

    @TableField("事业部")
    private String 事业部;

    @TableField("BugID")
    private String BugID;

    @TableField("BUG标题")
    private String BUG标题;

    @TableField("BUG分类")
    private String BUG分类;

    @TableField("严重等级")
    private String 严重等级;

    @TableField("状态")
    private String 状态;

    @TableField("BUG创建人")
    private String BUG创建人;

    @TableField("BUG创建时间")
    private String BUG创建时间;

    @TableField("BUG负责人")
    private String BUG负责人;

    @TableField("任务关闭时间")
    private String 任务关闭时间;

    @TableField("是否被重新开启")
    private String BugReopen;

    @TableField("重新开启的月份")
    private String BugReopenMonth;

    @TableField("BUG类型")
    private String BUG类型;
    
    @TableField("责任归属")
    private String 责任归属;
    @TableField("影响版本")
    private String 影响版本;
    @TableField("发生概率")
    private String 发生概率;
    @TableField("实际修复版本")
    private String 实际修复版本;
    @TableField("功能模块")
    private String 功能模块;
}
