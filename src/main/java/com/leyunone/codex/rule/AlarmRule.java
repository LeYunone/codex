package com.leyunone.codex.rule;

import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *  报警规则
 * @Author LeYunone
 * @Date 2024/4/8 15:52
 */
@Getter
@Setter
public class AlarmRule {


    private String condition;
    //1：等于，2：大于，3：小于，
    private Integer conType;

    //1bug总数条件对象  2reopen率条件对象  3缺陷率条件对象  4提交频率条件对象 5代码行数
    private Integer key;

    /**
     * 多少天内
     */
    private Integer day;
}
