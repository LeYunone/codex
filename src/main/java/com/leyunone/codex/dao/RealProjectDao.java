package com.leyunone.codex.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.RealProject;
import com.leyunone.codex.model.query.ProjectRelationQuery;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/29 9:47
 */
public interface RealProjectDao extends BaseDao<RealProject> {

    Page<RealProject> selectPage(ProjectRelationQuery query);
    
    void updateRelation(List<String> projectNames,Integer status);
}
