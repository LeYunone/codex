package com.leyunone.codex.dao;


import com.leyunone.codex.dao.entry.ProjectUser;
import com.leyunone.codex.model.vo.ProjectUserVO;

import java.util.List;

public interface ProjectUserDao extends BaseDao<ProjectUser> {
 
    List<ProjectUserVO> getUserByProject(List<String> projectId);

    List<ProjectUser> selectUserIdByProjectId(String projectId);

    List<ProjectUser> selectByProjectIds(List<String> projectIds);

    List<ProjectUser> selectUserByProjectIds(List<String> projectIds);
    
    List<ProjectUser> selectByUserIds(List<String> userIds);
}
