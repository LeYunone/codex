package com.leyunone.codex.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.query.BugQuery;
import com.leyunone.codex.model.vo.BugSumInfo;
import com.leyunone.codex.model.vo.BugVO;
import com.leyunone.codex.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/5 14:28
 */
@RestController
@RequestMapping("/bug")
public class BugController {

    @Autowired
    private BugService bugService;

    /**
     * bug列表
     *
     * @param bugQuery
     * @return
     */
    @PostMapping("/detailList")
    public DataResponse<BugSumInfo> detailList(@RequestBody BugQuery bugQuery) {
        BugSumInfo detailList = bugService.getDetailList(bugQuery);
        return DataResponse.of(detailList);
    }
    
    @PostMapping("sync")
    public void sync(){
        bugService.sync();
    }

    @PostMapping("/statistics")
    public DataResponse<Page<BugVO>> bugStatistics(@RequestBody BugQuery bugQuery) {
        Page<BugVO> page = new Page<>();
        if (bugQuery.getQueryType() == 0) {
            page = bugService.getUserList(bugQuery);
        }
        if (bugQuery.getQueryType() == 1) {
            page = bugService.getGroupList(bugQuery);
        }
        if (bugQuery.getQueryType() == 2) {
            page = bugService.getProjectList(bugQuery);
        }
        return DataResponse.of(page);
    }
}
