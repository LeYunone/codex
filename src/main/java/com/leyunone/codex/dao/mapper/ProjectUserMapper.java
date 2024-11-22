package com.leyunone.codex.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyunone.codex.dao.entry.ProjectUser;
import com.leyunone.codex.model.vo.ProjectUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectUserMapper extends BaseMapper<ProjectUser> {

    List<ProjectUserVO> getUserByProject(@Param("ids") List<String> projectId);

    List<ProjectUser> selectUserByProjectIds(@Param("ids") List<String> projectIds);
}
