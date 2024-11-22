package com.leyunone.codex.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.leyunone.codex.dao.UserDao;
import com.leyunone.codex.dao.entry.User;
import com.leyunone.codex.dao.mapper.UserMapper;
import com.leyunone.codex.model.bo.UserBO;
import com.leyunone.codex.model.query.ProjectUserQuery;
import com.leyunone.codex.model.vo.ProjectUserVO;
import com.leyunone.codex.model.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository extends BaseRepository<UserMapper, User> implements UserDao {

    @Override
    public void saveUser(List<UserBO> userBOS) {
        this.baseMapper.saveUser(userBOS);
    }

    @Override
    public List<ProjectUserVO> selectProjectUserList(ProjectUserQuery projectUserQuery) {
        return this.baseMapper.selectProjectUserList(projectUserQuery);
    }

    @Override
    public List<UserVO> selectCode() {
        return this.baseMapper.selectCode();
    }

    @Override
    public int updateRealNameByUserIds(List<String> userIds,String realname) {
        LambdaUpdateWrapper<User> lambda = new UpdateWrapper<User>().lambda();
        lambda.in(User::getUserId, userIds);
        lambda.set(User::getRealUserName, realname);

        return this.baseMapper.update(null, lambda);
    }

    @Override
    public List<User> selectByNoReal() {
        LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
        lambda.isNull(User::getRealUserName);
        return this.baseMapper.selectList(lambda);
    }
}
