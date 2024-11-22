package com.leyunone.codex.model.query;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/8/14 15:24
 */
public class PageCommonQuery {

    private Integer pageIndex;

    private Integer pageSize;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public PageCommonQuery setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PageCommonQuery setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
