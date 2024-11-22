package com.leyunone.codex.dao.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.ProjectDao;
import com.leyunone.codex.dao.entry.Project;
import com.leyunone.codex.dao.mapper.ProjectMapper;
import com.leyunone.codex.model.query.ProjectUserQuery;
import com.leyunone.codex.model.vo.ProjectUserVO;
import com.leyunone.codex.model.vo.ProjectVO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProjectRepository extends BaseRepository<ProjectMapper, Project> implements ProjectDao {

    @Override
    public List<ProjectUserVO> selectProjectUserList(ProjectUserQuery query) {
        return this.baseMapper.selectProjectUserList(query);
    }

    @Override
    public Page<ProjectVO> selectProjectList(ProjectUserQuery query) {
        Page page = new Page<>(query.getPageIndex(), query.getPageSize());
        return this.baseMapper.selectProjectList(page, query);
    }
}
