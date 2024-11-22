package com.leyunone.codex.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.query.BugQuery;
import com.leyunone.codex.model.query.CodeQuery;
import com.leyunone.codex.model.query.CodeTimeQuery;
import com.leyunone.codex.model.vo.ChartVO;
import com.leyunone.codex.model.vo.CodeInfoVO;
import com.leyunone.codex.service.BugStatisticsService;
import com.leyunone.codex.service.CodeStatisticsService;
import com.leyunone.codex.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 统计接口
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private CodeStatisticsService codeStatisticsService;
    @Autowired
    private BugStatisticsService bugStatisticsService;

    @GetMapping("/userinfo")
    public DataResponse<Page<CodeInfoVO>> getCodeStatistics(CodeQuery query) {
        Page<CodeInfoVO> codeStatistics = codeStatisticsService.getCodeStatistics(query);
        return DataResponse.of(codeStatistics);
    }

    @GetMapping("/getCodeTotal")
    public DataResponse<CodeInfoVO> getCodeStatisticsTotal(CodeQuery query) {
        CodeInfoVO codeTotal = codeStatisticsService.getCodeTotal(query);
        return DataResponse.of(codeTotal);
    }

    /**
     * 人员基本信息统计
     *
     * @return
     */
    @GetMapping("/userbasecode")
    public DataResponse<ChartVO> statisticsUserBaseCode(CodeTimeQuery query) {
        ChartVO chartVO = statisticsService.userBaseCode(query);
        return DataResponse.of(chartVO);
    }

    @GetMapping("/projectbasecode")
    public DataResponse<ChartVO> statisticsProjectBaseCode(CodeTimeQuery query) {
        ChartVO chartVO = statisticsService.projectBaseCode(query);
        return DataResponse.of(chartVO);
    }

    /**
     * 代码随时间提交统计
     *
     * @param query
     * @return
     */
    @GetMapping("/userProjectTimeCode")
    public DataResponse<ChartVO> userProjectTimeCode(CodeTimeQuery query) {
        ChartVO chartVO = statisticsService.userProjectTimeCode(query);
        return DataResponse.of(chartVO);
    }

    @GetMapping("/projectTimeCode")
    public DataResponse<ChartVO> projectTimeCode(CodeTimeQuery query) {
        ChartVO chartVO = statisticsService.projectTimeCode(query);
        return DataResponse.of(chartVO);
    }

    @PostMapping("/bugTop")
    public DataResponse<ChartVO> bugTopStatistics(@RequestBody BugQuery query) {
        ChartVO chartVO = bugStatisticsService.bugTop(query);
        return DataResponse.of(chartVO);
    }

    @PostMapping("/bugAnalysis")
    public DataResponse<ChartVO> bugAnalysis(@RequestBody BugQuery query) {
        ChartVO chartVO = bugStatisticsService.bugAnalysis(query);
        return DataResponse.of(chartVO);
    }

    @PostMapping("/bugVersionAnalysis")
    public DataResponse<ChartVO> bugVersionAnalysis(@RequestBody BugQuery query) {
        ChartVO chartVO = bugStatisticsService.bugVersionAnalysis(query);
        return DataResponse.of(chartVO);
    }

    @PostMapping("/bugHistogram")
    public DataResponse<ChartVO> bugHistogram(@RequestBody BugQuery query) {
        ChartVO chartVO = bugStatisticsService.bugHistogram(query);
        return DataResponse.of(chartVO);
    }
}
