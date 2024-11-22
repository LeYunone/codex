package com.leyunone.codex.control;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.model.data.CodeInfoData;
import com.leyunone.codex.model.query.CodeQuery;
import com.leyunone.codex.model.vo.CodeInfoVO;
import com.leyunone.codex.service.CodeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/1/9 16:08
 */
@Controller
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private CodeStatisticsService codeStatisticsService;

    @GetMapping("/userCode")
    public void exportUserCode(CodeQuery query, HttpServletResponse response) throws IOException {
        query.setPageIndex(1);
        query.setPageSize(999);
        Page<CodeInfoVO> codeStatistics =
                codeStatisticsService.getCodeStatistics(query);
        List<CodeInfoVO> records = codeStatistics.getRecords();
        List<CodeInfoData> collect = records.stream().map(ci -> {
            CodeInfoData codeInfoData = new CodeInfoData();
            codeInfoData.setRealUserName(ci.getRealUserName());
            codeInfoData.setGroupName(ci.getGroupName());
            codeInfoData.setDeletions(String.valueOf(ci.getDeletions()));
            codeInfoData.setAdditions(String.valueOf(ci.getAdditions()));
            codeInfoData.setTotal(String.valueOf(ci.getTotal()));
            codeInfoData.setProjectNumber(String.valueOf(ci.getProjectNumber()));
            return codeInfoData;
        }).collect(Collectors.toList());
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=userCode.xls");
        response.setCharacterEncoding("UTF-8");
        EasyExcel.write(response.getOutputStream(), CodeInfoData.class)
                .sheet()
                .doWrite(collect);
    }

}
