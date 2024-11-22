package com.leyunone.codex.control;

import com.leyunone.codex.dao.entry.RealProject;
import com.leyunone.codex.dao.impl.RealProjectRepository;
import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.vo.BugSystemInfoVO;
import com.leyunone.codex.service.CodeXSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/8/1 10:51
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    public static volatile Integer status = 0;

    @Autowired
    private CodeXSummaryService codeXSummaryService;


    @GetMapping("/sync")
    public DataResponse<?> syncCode(@RequestParam("url") String url, @RequestParam("token") String token) {
        if (status == 1) {
            return DataResponse.buildFailure("同步中，请等待...");
        }
        codeXSummaryService.summaryCodeX(url, null);
        return DataResponse.buildSuccess();
    }

    @Autowired
    private RealProjectRepository realProjectRepository;

    @GetMapping("/getBugSystemName")
    public DataResponse<BugSystemInfoVO> getBugProjectName() {
        BugSystemInfoVO bugSystemInfoVO = new BugSystemInfoVO();
        List<RealProject> realProjects = realProjectRepository.selectByCon(null);
        bugSystemInfoVO.setDepartment(realProjects.stream().map(RealProject::getDepartment).collect(Collectors.toSet()));
        bugSystemInfoVO.setProjectName(realProjects.stream().map(RealProject::getRealProjectName).collect(Collectors.toList()));
        return DataResponse.of(bugSystemInfoVO);
    }

}
