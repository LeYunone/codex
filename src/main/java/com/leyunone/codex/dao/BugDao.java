package com.leyunone.codex.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.Bug;
import com.leyunone.codex.model.query.BugQuery;
import com.leyunone.codex.model.query.BugTopQuery;

import java.util.List;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/6 9:37
 */
public interface BugDao extends BaseDao<Bug> {

    Page<Bug> selectPage(BugQuery query);

    List<Bug> selectList(BugQuery query);

    List<Bug> selectByUserName(BugQuery query);

    List<Bug> selectCountAlarmGroupByUser(String startDate, Integer bug, Integer conType);

    List<String> selectProjectByUser(String startDate,List<String> userNames);

    List<Bug> selectCountAlarmGroupByProject(String startDate, Integer bug,Integer conType,List<String> projectNames);

    List<Bug> selectReopenAlarm(String startDate, Double reopen, Integer conType);

    Page<Bug> selectUserPage(BugQuery query);

    List<Bug> selectUserList(BugQuery query);

    void deleteAll();

    Page<Bug> selectProjectPage(BugQuery query);

    List<Bug> selectBugTopGroupByProjectName(BugQuery query);

    List<Bug> selectBugTopGroupByUserName(BugTopQuery query);
}
