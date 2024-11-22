package com.leyunone.codex.model;

import lombok.Data;

import java.util.List;

@Data
public class ChartBean {
    /**
     * 线条颜色
     */
    private String color;

    /**
     * 线条名
     */
    private String name;

    /**
     * 数据
     */
    private List<Long> data;

    private double y;
}
