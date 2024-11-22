package com.leyunone.codex.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/28 15:49
 */
public class BugSumInfo {

    private Page<BugDetailVO> bugDetails;
    
    private long bugCount;
    
    private long closeCount;
    
    private long reopenCount;
    
    private long noBugCount;

    public Page<BugDetailVO> getBugDetails() {
        return bugDetails;
    }

    public BugSumInfo setBugDetails(Page<BugDetailVO> bugDetails) {
        this.bugDetails = bugDetails;
        return this;
    }

    public long getBugCount() {
        return bugCount;
    }

    public BugSumInfo setBugCount(long bugCount) {
        this.bugCount = bugCount;
        return this;
    }

    public long getCloseCount() {
        return closeCount;
    }

    public BugSumInfo setCloseCount(long closeCount) {
        this.closeCount = closeCount;
        return this;
    }

    public long getReopenCount() {
        return reopenCount;
    }

    public BugSumInfo setReopenCount(long reopenCount) {
        this.reopenCount = reopenCount;
        return this;
    }

    public long getNoBugCount() {
        return noBugCount;
    }

    public BugSumInfo setNoBugCount(long noBugCount) {
        this.noBugCount = noBugCount;
        return this;
    }
}
