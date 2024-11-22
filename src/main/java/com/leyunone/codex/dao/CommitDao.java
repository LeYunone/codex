package com.leyunone.codex.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.Commit;
import com.leyunone.codex.model.bo.CommitBO;
import com.leyunone.codex.model.query.CodeQuery;
import com.leyunone.codex.model.query.CodeTimeQuery;
import com.leyunone.codex.model.query.CommitQuery;
import com.leyunone.codex.model.vo.CodeInfoVO;
import com.leyunone.codex.model.vo.CommitVO;
import com.leyunone.codex.model.vo.UserVO;

import java.util.Date;
import java.util.List;

public interface CommitDao extends BaseDao<Commit> {

    Page<Commit> selectByPage(CommitQuery commitQuery);

    List<UserVO> selectSumGroupUser(String startDate, String endDate);

    void saveBatch(List<CommitBO> commits);

    Date selectLastDate(String url);

    List<CommitVO> selectProjectCodeGroupUser(CodeTimeQuery query);

    List<CommitVO> selectProjectCodeGroupUserMonth(CodeTimeQuery query);

    List<String> preDate(String date);

    List<UserVO> selectByUser(CodeTimeQuery query);

    List<CommitVO> selectProjectCodeTime(CodeTimeQuery query);

    List<CommitVO> selectProjectCodeTimeMonth(CodeTimeQuery query);

    List<CommitVO> selectByProject(CodeTimeQuery codeTimeQuery);
    
    List<CodeInfoVO> selectCodeStatistics(CodeQuery query);

    List<CodeInfoVO> selectProjectNumber(CodeQuery query);
}
