package com.leyunone.codex.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyunone.codex.dao.ProjectUserDao;
import com.leyunone.codex.dao.entry.ProjectUser;
import com.leyunone.codex.dao.mapper.ProjectUserMapper;
import com.leyunone.codex.model.vo.ProjectUserVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectUserRepository extends BaseRepository<ProjectUserMapper, ProjectUser> implements ProjectUserDao {

    @Override
    public List<ProjectUserVO> getUserByProject(List<String> projectId) {
        return this.baseMapper.getUserByProject(projectId);
    }

    @Override
    public List<ProjectUser> selectUserIdByProjectId(String projectId) {
        LambdaQueryWrapper<ProjectUser> lambda = new QueryWrapper<ProjectUser>().lambda();

        lambda.eq(ProjectUser::getProjectId, projectId);
        return this.baseMapper.selectList(lambda);
    }
    
    @Override
    public List<ProjectUser> selectByProjectIds(List<String> projectIds) {
        return this.baseMapper.selectList(new QueryWrapper<ProjectUser>().lambda().in(ProjectUser::getProjectId,projectIds));
    }
    
    @Override
    public List<ProjectUser> selectUserByProjectIds(List<String> projectIds) {
        return this.baseMapper.selectUserByProjectIds(projectIds);
    }

    @Override
    public List<ProjectUser> selectByUserIds(List<String> userIds) {
        if(CollectionUtil.isEmpty(userIds)) return new ArrayList<>();
        LambdaQueryWrapper<ProjectUser> lambda = new QueryWrapper<ProjectUser>().lambda();
        lambda.in(ProjectUser::getUserId,userIds);
        return this.baseMapper.selectList(lambda);
    }
}
