package com.leyunone.codex.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.dao.CommitDao;
import com.leyunone.codex.dao.GroupUserDao;
import com.leyunone.codex.dao.ProjectDao;
import com.leyunone.codex.dao.RealUserRelationDao;
import com.leyunone.codex.dao.entry.GroupUser;
import com.leyunone.codex.dao.entry.Project;
import com.leyunone.codex.model.ChartBean;
import com.leyunone.codex.model.enums.StatisticsTypeEnum;
import com.leyunone.codex.model.query.CodeTimeQuery;
import com.leyunone.codex.model.vo.*;
import com.leyunone.codex.util.TimeUtil;
import com.leyunone.codex.util.UserNameUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final GroupUserDao groupUserDao;
    private final CommitDao commitDao;
    private final RealUserRelationDao realUserRelationDao;
    private final ProjectDao projectDao;

    private static final DateTimeFormatter YYYY_MM_DD_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public StatisticsService(GroupUserDao groupUserDao, CommitDao commitDao, RealUserRelationDao realUserRelationDao, ProjectDao projectDao) {
        this.groupUserDao = groupUserDao;
        this.commitDao = commitDao;
        this.realUserRelationDao = realUserRelationDao;
        this.projectDao = projectDao;
    }

    /**
     * 统计人员基础代码信息
     */
    public ChartVO userBaseCode(CodeTimeQuery codeTimeQuery) {
        ChartVO chartVO = new ChartVO();

        List<UserVO> users = null;
        if (StringUtils.isBlank(codeTimeQuery.getStartDate())) {
            //未入时间，则截止至今
//            users = userDao.selectCode();
            chartVO.setDate(LocalDateTime.now().format(YYYY_MM_DD_FORMAT));
        } else {
            chartVO.setDate(codeTimeQuery.getEndDate());
        }
        if (ObjectUtil.isNotNull(codeTimeQuery.getGroupId())) {
            //指定组
            List<GroupUser> groupUsers = groupUserDao.selectRealUserByGroupId(codeTimeQuery.getGroupId());
            if (CollectionUtil.isNotEmpty(groupUsers)) {
                List<Integer> realUserIds = groupUsers.stream().map(GroupUser::getRealUserId).collect(Collectors.toList());
                //拿到这个组下所有人员的git账号
                List<RealUserRelationVO> realUserRelationVOS = realUserRelationDao.selectByRealUserIds(realUserIds);
                if (CollectionUtil.isNotEmpty(realUserRelationVOS)) {
                    codeTimeQuery.setUserIds(realUserRelationVOS.stream().map(RealUserRelationVO::getUserId).collect(Collectors.toList()));
                }
            } else {
                //组中无人员
                codeTimeQuery.setUserIds(CollectionUtil.newArrayList("-1"));
            }
        }
        users = commitDao.selectByUser(codeTimeQuery);
        //TODO 第二版本代码
        if (CollectionUtil.isNotEmpty(users)) {
            List<UserVO> realUser = new ArrayList<>();
            Iterator<UserVO> iterator = users.iterator();
            //将已被关联realUser的账户提出来
            while (iterator.hasNext()) {
                UserVO next = iterator.next();
                if (StringUtils.isNotBlank(next.getRealUserName())) {
                    realUser.add(next);
                    iterator.remove();
                }
            }

            Map<String, List<UserVO>> userMap = realUser.stream().collect(Collectors.groupingBy(UserVO::getRealUserName));
            //将同一关联的gitlab账号累加起来
            for (String realUserName : userMap.keySet()) {
                List<UserVO> userVOS = userMap.get(realUserName);
                if (CollectionUtil.isNotEmpty(userVOS)) {
                    UserVO userVO = new UserVO();
                    userVO.setUserName(realUserName);
                    userVO.setCodeAdditions(userVOS.stream().mapToLong(UserVO::getCodeAdditions).sum());
                    userVO.setCodeDeletions(userVOS.stream().mapToLong(UserVO::getCodeDeletions).sum());
                    userVO.setCodeTotal(userVOS.stream().mapToLong(UserVO::getCodeTotal).sum());
                    users.add(userVO);
                }
            }
            //根据总量排序
            users.sort(Comparator.comparing(UserVO::getCodeTotal));
        }

//        Map<String, String> realUserNames = UserNameUtils.getUserRealNames(null);
        List<Long> deletes = new ArrayList<>();
        List<Long> additions = new ArrayList<>();
        List<ChartBean> cb = new ArrayList<>();
        ChartBean adds = new ChartBean();
        adds.setName("新增");
        adds.setData(additions);
        ChartBean des = new ChartBean();
        des.setName("删除");
        des.setData(deletes);
        cb.add(adds);
        cb.add(des);
        List<String> names = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            UserVO userVO = users.get(i);
            additions.add(userVO.getCodeAdditions());
            deletes.add(userVO.getCodeDeletions());
            names.add(userVO.getUserName());
        }
        chartVO.setSeries(cb);
        chartVO.setTitle("人员代码量");
        chartVO.setXChart(names);
        return chartVO;
    }

    public ChartVO projectBaseCode(CodeTimeQuery codeTimeQuery) {
        ChartVO chartVO = new ChartVO();
        List<GroupUserVO> groupUserVOS = null;
        if (StringUtils.isBlank(codeTimeQuery.getStartDate())) {
            String statisticsDate = StatisticsTypeEnum.getStatisticsType(0).getStatisticsDate();
            codeTimeQuery.setStartDate(statisticsDate);
        } else {
            if (codeTimeQuery.getStartDate().length() == 7) {
                codeTimeQuery.setStartDate(TimeUtil.monthTime(codeTimeQuery.getStartDate(), true));
            }
        }
        if (StringUtils.isBlank(codeTimeQuery.getEndDate())) {
            String endDate = YYYY_MM_DD_FORMAT.format(LocalDateTime.now());
            codeTimeQuery.setEndDate(endDate);
        } else {
            if (codeTimeQuery.getEndDate().length() == 7) {
                codeTimeQuery.setEndDate(TimeUtil.monthTime(codeTimeQuery.getEndDate(), false));
            }
        }
        List<CommitVO> commitVOS = commitDao.selectByProject(codeTimeQuery);

        List<String> names = new ArrayList<>();
        List<Long> deletes = new ArrayList<>();
        List<Long> additions = new ArrayList<>();
        List<ChartBean> cb = new ArrayList<>();
        ChartBean adds = new ChartBean();
        adds.setName("新增");
        adds.setData(additions);
        ChartBean des = new ChartBean();
        des.setName("删除");
        des.setData(deletes);
        cb.add(adds);
        cb.add(des);
        commitVOS.forEach(commitVO -> {
            additions.add(commitVO.getAddition());
            deletes.add(commitVO.getDeletions());
            names.add(commitVO.getProjectName());
        });
        chartVO.setTitle("项目代码量");
        chartVO.setXChart(names);
        chartVO.setSeries(cb);
        return chartVO;
    }

    /**
     * 统计 人员根据时间
     *
     * @param codeTimeQuery
     * @return
     */
    public ChartVO userProjectTimeCode(CodeTimeQuery codeTimeQuery) {
        if (StringUtils.isBlank(codeTimeQuery.getStartDate())) {
            String statisticsDate = StatisticsTypeEnum.getStatisticsType(0).getStatisticsDate();
            codeTimeQuery.setStartDate(statisticsDate);
        } else {
            if (codeTimeQuery.getStartDate().length() == 7) {
                codeTimeQuery.setStartDate(TimeUtil.monthTime(codeTimeQuery.getStartDate(), true));
            }
        }
        if (StringUtils.isBlank(codeTimeQuery.getEndDate())) {
            String endDate = YYYY_MM_DD_FORMAT.format(LocalDateTime.now());
            codeTimeQuery.setEndDate(endDate);
        } else {
            if (codeTimeQuery.getEndDate().length() == 7) {
                codeTimeQuery.setEndDate(TimeUtil.monthTime(codeTimeQuery.getEndDate(), false));
            }
        }
        if (ObjectUtil.isNotNull(codeTimeQuery.getRealUserId())) {
            List<String> userIds = realUserRelationDao.selectUserIdByRealUserId(codeTimeQuery.getRealUserId());
            if (CollectionUtil.isEmpty(userIds)) {
                //未关联账户 赋空值进去
                userIds.add("1");
            }
            codeTimeQuery.setUserIds(userIds);
        }
        List<CommitVO> commitVOS = new ArrayList<>();
        if (codeTimeQuery.getTimeType() == 1) {
            commitVOS = commitDao.selectProjectCodeGroupUserMonth(codeTimeQuery);
        } else {
            commitVOS = commitDao.selectProjectCodeGroupUser(codeTimeQuery);
        }

        //TODO 第二版本代码
        if (CollectionUtil.isNotEmpty(commitVOS)) {
            List<CommitVO> realCommit = new ArrayList<>();
            Iterator<CommitVO> iterator = commitVOS.iterator();
            //将已被关联realUser的账户提出来
            while (iterator.hasNext()) {
                CommitVO next = iterator.next();
                if (StringUtils.isNotBlank(next.getRealUserName())) {
                    realCommit.add(next);
                    iterator.remove();
                }
            }

            Map<String, List<CommitVO>> userMap = realCommit.stream().collect(Collectors.groupingBy(CommitVO::getRealUserName));
            //将同一关联的gitlab账号累加起来
            for (String realUserName : userMap.keySet()) {
                List<CommitVO> userVOS = userMap.get(realUserName);
                if (CollectionUtil.isNotEmpty(userVOS)) {
                    //如果日期相同的就合并
                    Map<String, List<CommitVO>> userDateMap = userVOS.stream().collect(Collectors.groupingBy(CommitVO::getDate));
                    for (String date : userDateMap.keySet()) {
                        List<CommitVO> dateCommits = userDateMap.get(date);
                        CommitVO userVO = new CommitVO();
                        userVO.setUserName(realUserName);
                        userVO.setDate(date);
                        userVO.setUserId(CollectionUtil.getFirst(dateCommits).getUserId());
                        userVO.setTotal(dateCommits.stream().mapToLong(CommitVO::getTotal).sum());
                        userVO.setDeletions(dateCommits.stream().mapToLong(CommitVO::getDeletions).sum());
                        userVO.setAddition(dateCommits.stream().mapToLong(CommitVO::getAddition).sum());
                        commitVOS.add(userVO);
                    }

                }
            }
            //根据总量排序
            commitVOS.sort(Comparator.comparing(CommitVO::getDate));
        }

        //解析页面可用的chart对象 日期 -》 提交人集合
        Map<String, List<CommitVO>> datamap = new HashMap<>();
        Set<String> setX = new LinkedHashSet<>();
        Set<String> names = new LinkedHashSet<>();
        Long codeTotal = 0L;
        Long codeAdditions = 0L;
        Long codeDeletions = 0L;
        for (CommitVO commitVO : commitVOS) {
            String date = commitVO.getDate();
            if (datamap.containsKey(date)) {
                datamap.get(date).add(commitVO);
            } else {
                ArrayList<CommitVO> l = CollectionUtil.newArrayList();
                l.add(commitVO);
                datamap.put(date, l);
            }
            //X坐标
            setX.add(date);
            if (StringUtils.isNotBlank(commitVO.getUserName())) {
                names.add(commitVO.getUserName());
            }
            codeTotal += ObjectUtil.isNull(commitVO.getTotal()) ? 0L : commitVO.getTotal();
            codeAdditions += ObjectUtil.isNull(commitVO.getAddition()) ? 0L : commitVO.getAddition();
            codeDeletions += ObjectUtil.isNull(commitVO.getDeletions()) ? 0L : commitVO.getDeletions();
        }
        Map<String, List<Long>> nameDate = new HashMap<>();
        if (CollectionUtil.isEmpty(names)) {
//            names = userDao.selectByCon(null).stream().map(User::getUserId).collect(Collectors.toSet());
        } else {
            names.forEach((t) -> nameDate.put(t, new ArrayList<>()));
        }

        for (String date : setX) {
            List<CommitVO> commits = datamap.get(date);
            CommitVO first = CollectionUtil.getFirst(commits);
            if (StringUtils.isBlank(first.getUserId()) && StringUtils.isBlank(first.getUserName())) {
                //全为空
                Collection<List<Long>> values = nameDate.values();
                for (List<Long> data : values) {
                    data.add(0L);
                }
            } else {
                //找到每个提交的对应人员
                for (CommitVO commit : commits) {
                    List<Long> integers = nameDate.get(commit.getUserName());
                    integers.add(commit.getTotal());
                }
            }
        }
        ChartVO chartVO = new ChartVO();
        chartVO.setCodeTotal(codeTotal);
        chartVO.setCodeAdditions(codeAdditions);
        chartVO.setCodeDeletions(codeDeletions);
        chartVO.setXChart(CollectionUtil.newArrayList(setX));
        chartVO.setTitle("人员时间提交记录量");
        List<ChartBean> series = new ArrayList<>();
        Set<String> userIds = nameDate.keySet();
        Map<String, String> userRealNames = UserNameUtils.getUserRealNames(CollectionUtil.newArrayList(userIds));
        for (String name : nameDate.keySet()) {
            ChartBean chartBean = new ChartBean();
            chartBean.setData(nameDate.get(name));
            chartBean.setName(userRealNames.containsKey(name) ? userRealNames.get(name) : name);
            series.add(chartBean);
        }
        chartVO.setSeries(series);
        return chartVO;
    }

    private void reloadTime(CodeTimeQuery codeTimeQuery) {
        if (StringUtils.isBlank(codeTimeQuery.getStartDate())) {
            String statisticsDate = StatisticsTypeEnum.getStatisticsType(0).getStatisticsDate();
            codeTimeQuery.setStartDate(statisticsDate);
        }
        if (StringUtils.isBlank(codeTimeQuery.getEndDate())) {
            String endDate = YYYY_MM_DD_FORMAT.format(LocalDateTime.now());
            codeTimeQuery.setEndDate(endDate);
        }
    }

    /**
     * 统计 小组随时间
     *
     * @param codeTimeQuery
     * @return
     */
    public ChartVO projectTimeCode(CodeTimeQuery codeTimeQuery) {
        if (StringUtils.isBlank(codeTimeQuery.getStartDate())) {
            String statisticsDate = StatisticsTypeEnum.getStatisticsType(0).getStatisticsDate();
            codeTimeQuery.setStartDate(statisticsDate);
        } else {
            if (codeTimeQuery.getStartDate().length() == 7) {
                codeTimeQuery.setStartDate(TimeUtil.monthTime(codeTimeQuery.getStartDate(), true));
            }
        }
        if (StringUtils.isBlank(codeTimeQuery.getEndDate())) {
            String endDate = YYYY_MM_DD_FORMAT.format(LocalDateTime.now());
            codeTimeQuery.setEndDate(endDate);
        } else {
            if (codeTimeQuery.getEndDate().length() == 7) {
                codeTimeQuery.setEndDate(TimeUtil.monthTime(codeTimeQuery.getEndDate(), false));
            }
        }

        this.reloadTime(codeTimeQuery);
        //前置加载数据库日期
        List<String> strings = commitDao.preDate(codeTimeQuery.getEndDate());
        List<CommitVO> commitVOS = null;
        if (codeTimeQuery.getTimeType() == 2) {
            commitVOS = commitDao.selectProjectCodeTimeMonth(codeTimeQuery);
        } else {
            commitVOS = commitDao.selectProjectCodeTime(codeTimeQuery);
        }

        //解析页面可用的chart对象 日期 -》 提交人集合
        Set<String> setX = new LinkedHashSet<>();
        List<Long> data = new ArrayList<>();
        Long codeTotal = 0L;
        Long codeAdditions = 0L;
        Long codeDeletions = 0L;
        for (CommitVO commitVO : commitVOS) {
            String date = commitVO.getDate();
            //X坐标
            setX.add(date);
            data.add(ObjectUtil.isNull(commitVO.getTotal()) ? 0L : commitVO.getTotal());
            codeTotal += ObjectUtil.isNull(commitVO.getTotal()) ? 0L : commitVO.getTotal();
            codeAdditions += ObjectUtil.isNull(commitVO.getAddition()) ? 0L : commitVO.getAddition();
            codeDeletions += ObjectUtil.isNull(commitVO.getDeletions()) ? 0L : commitVO.getDeletions();
        }

        ChartVO chartVO = new ChartVO();
        chartVO.setCodeAdditions(codeAdditions);
        chartVO.setCodeTotal(codeTotal);
        chartVO.setCodeDeletions(codeDeletions);
        chartVO.setXChart(CollectionUtil.newArrayList(setX));
        chartVO.setData(data);
        chartVO.setTitle("项目代码提交量");
        if (StringUtils.isNotBlank(codeTimeQuery.getProjectId())) {
            Project project = projectDao.selectById(codeTimeQuery.getProjectId());
            if (ObjectUtil.isNotNull(project)) {
                chartVO.setProjectName(project.getProjectName());
            }
        }
        return chartVO;
    }
}
