package com.leyunone.codex.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.Commit;
import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.query.CommitQuery;
import com.leyunone.codex.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提交记录接口
 */
@RestController
@RequestMapping("/commit")
public class CommitController {

    @Autowired
    private CommitService commitService;

    @RequestMapping("/commitBy")
    public DataResponse<Page<Commit>> getCommit(CommitQuery query) {
        Page<Commit> commitPage = commitService.queryCommitCodeX(query);
        return DataResponse.of(commitPage);
    }

}
