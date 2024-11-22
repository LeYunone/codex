package com.leyunone.codex.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.RealUser;
import com.leyunone.codex.model.query.CodeQuery;
import com.leyunone.codex.model.query.RealUserQuery;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 14:49
 */
public interface RealUserDao extends BaseDao<RealUser> {

    Page<RealUser> selectPage(RealUserQuery query);

    List<RealUser> selectByNames(List<String> userNames);

    RealUser selectByName(String userName);

    Page<RealUser> selectCodeUser(CodeQuery query);

    List<RealUser> selectCodeUserList(CodeQuery query);
}

