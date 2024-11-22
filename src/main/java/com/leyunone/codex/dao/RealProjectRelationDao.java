package com.leyunone.codex.dao;


import com.leyunone.codex.dao.entry.RealProjectRelation;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/29 9:47
 */
public interface RealProjectRelationDao extends BaseDao<RealProjectRelation> {

    void deleteByProjectNames(List<String> projectNames);

    void deleteByProjectIds(List<String> projectIds);

    List<RealProjectRelation> selectByProjectIds(List<String> projectIds);
    
    List<RealProjectRelation> selectByProjectNames(List<String> projectNames);
    
}
