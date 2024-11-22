package com.leyunone.codex.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.entry.Bug;
import com.leyunone.codex.model.query.BugQuery;
import com.leyunone.codex.model.query.BugTopQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BugMapper extends BaseMapper<Bug> {

    List<Bug> selectCountAlarmGroupByUser(@Param("startDate") String startDate, @Param("bug") Integer bug,@Param("conType") Integer conType);

    List<Bug> selectCountAlarmGroupByProject(@Param("startDate") String startDate, @Param("bug") Integer bug,@Param("conType") Integer conType,@Param("projectNames") List<String> projectNames);

    List<Bug> selectReopenAlarm(@Param("startDate") String startDate, @Param("bug") Double reopen,@Param("conType") Integer conType);

    Page<Bug> selectUserPage(@Param("con") BugQuery query, Page<Bug> page);

    List<Bug> selectUserPage(@Param("con") BugQuery query);

    Page<Bug> selectProjectPage(@Param("con") BugQuery query,Page<Bug> page);

    List<Bug> selectBugTopGroupByProjectName(@Param("con") BugQuery query);

    List<Bug> selectBugTopGroupByUserName(@Param("con") BugTopQuery query);

    List<String> selectProjectByUser(@Param("startDate") String startDate,@Param("users") List<String> userNames);
}
