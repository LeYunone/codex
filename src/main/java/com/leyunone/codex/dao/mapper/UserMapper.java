package com.leyunone.codex.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leyunone.codex.dao.entry.User;
import com.leyunone.codex.model.bo.UserBO;
import com.leyunone.codex.model.query.ProjectUserQuery;
import com.leyunone.codex.model.vo.ProjectUserVO;
import com.leyunone.codex.model.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    
    void saveUser(@Param("users") List<UserBO> userBOS);

    List<ProjectUserVO> selectProjectUserList(@Param("con") ProjectUserQuery projectUserQuery);

    List<UserVO> selectCode();
}
