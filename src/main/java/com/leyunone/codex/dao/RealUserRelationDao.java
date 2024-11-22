package com.leyunone.codex.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.RealUserRelation;
import com.leyunone.codex.model.query.CodeQuery;
import com.leyunone.codex.model.vo.RealUserRelationVO;
import com.leyunone.codex.model.vo.RealUserVO;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 14:49
 */
public interface RealUserRelationDao extends BaseDao<RealUserRelation>{

    int deleteByRealUserId(Integer RealUserId);
    
    List<RealUserRelationVO> selectByRealUserIds(List<Integer> RealUserIds);

    List<String> selectUserIdByRealUserId(Integer RealUserId);
    
    List<RealUserRelation> selectByUserIds(List<String> userIds);

    Page<RealUserVO> selectCodeUser(CodeQuery query);

    List<RealUserRelation> selectByName(List<String> userName);
}
