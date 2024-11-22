package com.leyunone.codex.model.vo;

import com.leyunone.codex.model.ChartBean;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ChartVO {

    /**
     * 标题
     */
    private String title;

    private boolean ifImport = true;

    /**
     * 截止时间
     */
    private String date;

    /**
     * 横坐标
     */
    private List<String> xChart;

    private List<Long> data;

    /**
     * 数据
     */
    private List<ChartBean> series;

    private Object [] [] seriesData;

    private Long codeTotal;

    private Long codeAdditions;

    private Long codeDeletions;

    private String projectName;

    private Set<String> projectNames;

    private List<ChartVO> charts;
}
