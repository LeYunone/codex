package com.leyunone.codex.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.dto.ProjectRelationDTO;
import com.leyunone.codex.model.query.ProjectRelationQuery;
import com.leyunone.codex.model.query.ProjectUserQuery;
import com.leyunone.codex.model.vo.ProjectRelationVO;
import com.leyunone.codex.model.vo.ProjectVO;
import com.leyunone.codex.service.ProjectRelationService;
import com.leyunone.codex.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRelationService projectRelationService;

    @RequestMapping("/projectcon")
    public DataResponse<Page<ProjectVO>> getProject(ProjectUserQuery projectUserQuery) {
        Page<ProjectVO> projectCon = projectService.getProjectCon(projectUserQuery);
        return DataResponse.of(projectCon);
    }

    @PostMapping("/relationRealProject")
    public DataResponse<?> relationRealProject(@RequestBody ProjectRelationDTO projectRelation) {
        projectRelationService.relationRealProject(projectRelation);
        return DataResponse.of();
    }

    @GetMapping("/relationList")
    public DataResponse<Page<ProjectRelationVO>> relationList(ProjectRelationQuery query) {
        Page<ProjectRelationVO> relationList = projectRelationService.getRelationList(query);
        return DataResponse.of(relationList);
    }
}
