package com.leyunone.codex.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.Project;
import com.leyunone.codex.model.query.ProjectUserQuery;
import com.leyunone.codex.model.vo.ProjectUserVO;
import com.leyunone.codex.model.vo.ProjectVO;

import java.util.List;

public interface ProjectDao extends BaseDao<Project> {
    
    List<ProjectUserVO> selectProjectUserList(ProjectUserQuery query);

    Page<ProjectVO> selectProjectList(ProjectUserQuery query);
}
