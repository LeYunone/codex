package com.leyunone.codex.dao;

import com.leyunone.codex.dao.entry.User;
import com.leyunone.codex.model.bo.UserBO;
import com.leyunone.codex.model.query.ProjectUserQuery;
import com.leyunone.codex.model.vo.ProjectUserVO;
import com.leyunone.codex.model.vo.UserVO;

import java.util.List;


public interface UserDao extends BaseDao<User> {
    
    void saveUser(List<UserBO> userBOS);
    
    List<ProjectUserVO> selectProjectUserList(ProjectUserQuery projectUserQuery);
    
    List<UserVO> selectCode();
    
    int updateRealNameByUserIds(List<String> userIds,String realName);
    
    List<User> selectByNoReal();
}
