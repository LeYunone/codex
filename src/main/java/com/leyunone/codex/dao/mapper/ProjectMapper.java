package com.leyunone.codex.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.Project;
import com.leyunone.codex.dao.entry.ProjectUser;
import com.leyunone.codex.model.query.ProjectUserQuery;
import com.leyunone.codex.model.vo.ProjectUserVO;
import com.leyunone.codex.model.vo.ProjectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectMapper extends BaseMapper<Project> {

    List<ProjectUserVO> selectProjectUserList(@Param("con") ProjectUserQuery query);

    Page<ProjectVO> selectProjectList(Page page, @Param("con") ProjectUserQuery query);
}
