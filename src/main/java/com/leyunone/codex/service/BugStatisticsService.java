package com.leyunone.codex.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.codex.dao.entry.Bug;
import com.leyunone.codex.dao.entry.Group;
import com.leyunone.codex.dao.impl.BugRepository;
import com.leyunone.codex.dao.impl.GroupRepository;
import com.leyunone.codex.model.ChartBean;
import com.leyunone.codex.model.query.BugQuery;
import com.leyunone.codex.model.vo.ChartVO;
import com.leyunone.codex.util.CollectionFunctionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/3 10:18
 */
@Service
public class BugStatisticsService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private BugRepository bugRepository;

    public ChartVO bugTop(BugQuery query) {
        List<Bug> result;
        //项目top
        if (ObjectUtil.isNotNull(query.getGroupId())) {
            List<Group> groups = groupRepository.selectGroupUser(CollectionUtil.newArrayList(query.getGroupId()));
            if (CollectionUtil.isNotEmpty(groups)) {
                query.setRealUserNames(groups.stream().map(Group::getRealUserName).collect(Collectors.toList()));
            }
        }
        result = bugRepository.selectBugTopGroupByProjectName(query);
        ChartVO chartVO = new ChartVO();
        List<Long> data = new ArrayList<>();
        List<String> x = new ArrayList<>();
        chartVO.setData(data);
        chartVO.setXChart(x);
        result.forEach(b -> {
            data.add((long) b.getCount());
            x.add(b.getProjectName());
        });
        return chartVO;
    }

    public ChartVO bugAnalysis(BugQuery query) {
        ChartVO chartVO = new ChartVO();
        List<ChartBean> series = new ArrayList<>();
        chartVO.setSeries(series);
        if (ObjectUtil.isNotNull(query.getGroupId())) {
            List<Group> groups = groupRepository.selectGroupUser(CollectionUtil.newArrayList(query.getGroupId()));
            if (CollectionUtil.isNotEmpty(groups)) {
                query.setRealUserNames(groups.stream().map(Group::getRealUserName).collect(Collectors.toList()));
            }
        }
        List<Bug> bugs = null;
        if (query.getStatisticsType() == 0) {
            bugs = bugRepository.selectBugTopGroupByProjectName(query);
        } else {
            bugs = bugRepository.selectList(query);

        }
        Map<String, List<Bug>> map = new HashMap<>();
        if (query.getStatisticsType() == 0) {
            Map<String, Bug> c = CollectionFunctionUtils.mapTo(bugs, Bug::getProjectName);
            long sum = bugs.stream().mapToLong(Bug::getCount).sum();

            c.keySet().forEach(key -> {
                Bug bug = c.get(key);
                ChartBean chartBean = new ChartBean();
                chartBean.setName(key);
                chartBean.setY((bug.getCount() * 1.00 / sum) * 100);
                series.add(chartBean);
            });
        }
        if (query.getStatisticsType() == 1) {
            map = CollectionFunctionUtils.groupTo(bugs, Bug::getDepartment);
        }
        if (query.getStatisticsType() == 4) {
            map = CollectionFunctionUtils.groupTo(bugs, Bug::getLevel);
        }
        if (query.getStatisticsType() == 5) {
            map = CollectionFunctionUtils.groupTo(bugs, Bug::getStatus);
        }
        if (query.getStatisticsType() == 7) {
            map = CollectionFunctionUtils.groupTo(bugs, Bug::getResolveName);
        }
        if (query.getStatisticsType() == 8) {
            map = CollectionFunctionUtils.groupTo(bugs, Bug::getBugType2);
        }
        if (query.getStatisticsType() == 9) {
            map = CollectionFunctionUtils.groupTo(bugs, Bug::getBelongTo);
        }
        if (query.getStatisticsType() == 13) {
            map = CollectionFunctionUtils.groupTo(bugs, Bug::getModule);
        }


        Map<String, List<Bug>> finalMap = map;
        List<Bug> finalBugs = bugs;
        map.keySet().forEach(key -> {
            int count = finalMap.get(key).size();
            ChartBean chartBean = new ChartBean();
            chartBean.setName(key);
            chartBean.setY((count * 1.00 / finalBugs.size()) * 100);
            series.add(chartBean);
        });
        return chartVO;
    }

    public ChartVO bugVersionAnalysis(BugQuery query) {
        if (query.getType() == 1) {
            return this.levelBugVersionAnalysis(query);
        } else {
            return this.belongBugVersionAnalysis(query);
        }
    }

    private ChartVO belongBugVersionAnalysis(BugQuery query) {
        ChartVO chartVO = new ChartVO();
        if (ObjectUtil.isNotNull(query.getGroupId())) {
            List<Group> groups = groupRepository.selectGroupUser(CollectionUtil.newArrayList(query.getGroupId()));
            if (CollectionUtil.isNotEmpty(groups)) {
                query.setRealUserNames(groups.stream().map(Group::getRealUserName).collect(Collectors.toList()));
            }
        }
        List<String> belongs = CollectionUtil.newArrayList("产品需求", "测试质量", "软件质量", "无");
        List<Bug> bugs = bugRepository.selectList(query);
        Map<String, List<Bug>> bugMap = CollectionFunctionUtils.groupTo(bugs, t -> t.getProjectName().trim());
        chartVO.setProjectNames(bugMap.keySet());
        Map<String, ChartVO> result = new HashMap<>();
        bugMap.keySet().forEach(projectName -> {
            ChartVO chart = new ChartVO();
            chart.setProjectName(projectName);
            List<Bug> currentBugs = bugMap.get(projectName);
            Collections.reverse(currentBugs);
            Map<String, VersionBug> map = new LinkedHashMap<>();
            currentBugs.forEach(bug -> {
                VersionBug versionBug = new VersionBug();
                if (map.containsKey(bug.getBugVersion())) {
                    versionBug = map.get(bug.getBugVersion());
                }
                Map<String, Long> belongsCount = versionBug.getCount();
                Long count = 0L;
                if (belongsCount.containsKey(bug.getBelongTo())) {
                    count = belongsCount.get(bug.getBelongTo());
                }
                belongsCount.put(bug.getBelongTo(), ++count);
                map.put(bug.getBugVersion(), versionBug);
            });
            chart.setXChart(CollectionUtil.newArrayList(map.keySet()));
            Map<String, List<Long>> belongMap = new HashMap<>();
            for (String key : map.keySet()) {
                VersionBug versionBug = map.get(key);
                Map<String, Long> belongsCount = versionBug.getCount();
                belongs.forEach(belong -> {
                    List<Long> counts = belongMap.get(belong);
                    if (CollectionUtil.isEmpty(counts)) {
                        counts = new ArrayList<>();
                    }
                    counts.add(belongsCount.getOrDefault(belong, 0L));
                    belongMap.put(belong, counts);
                });
            }
            chart.setSeries(belongMap.keySet().stream().map(key -> {
                ChartBean chartBean = new ChartBean();
                chartBean.setName(key);
                chartBean.setData(belongMap.get(key));
                return chartBean;
            }).collect(Collectors.toList()));

            result.put(projectName, chart);
        });

        chartVO.setCharts(CollectionUtil.newArrayList(result.values()));
        return chartVO;
    }

    private ChartVO levelBugVersionAnalysis(BugQuery query) {
        ChartVO chartVO = new ChartVO();
        if (ObjectUtil.isNotNull(query.getGroupId())) {
            List<Group> groups = groupRepository.selectGroupUser(CollectionUtil.newArrayList(query.getGroupId()));
            if (CollectionUtil.isNotEmpty(groups)) {
                query.setRealUserNames(groups.stream().map(Group::getRealUserName).collect(Collectors.toList()));
            }
        }
        List<String> levels = CollectionUtil.newArrayList("blocker", "critical", "major", "minor", "normal");
        List<Bug> bugs = bugRepository.selectList(query);
        Map<String, List<Bug>> bugMap = CollectionFunctionUtils.groupTo(bugs, t -> t.getProjectName().trim());
        chartVO.setProjectNames(bugMap.keySet());
        Map<String, ChartVO> result = new HashMap<>();
        bugMap.keySet().forEach(projectName -> {
            ChartVO chart = new ChartVO();
            chart.setProjectName(projectName);
            List<Bug> currentBugs = bugMap.get(projectName);
            Collections.reverse(currentBugs);
            Map<String, VersionBug> map = new LinkedHashMap<>();
            currentBugs.forEach(bug -> {
                VersionBug versionBug = new VersionBug();
                if (map.containsKey(bug.getBugVersion())) {
                    versionBug = map.get(bug.getBugVersion());
                }
                Map<String, Long> levelCount = versionBug.getCount();
                Long count = 0L;
                if (levelCount.containsKey(bug.getLevel())) {
                    count = levelCount.get(bug.getLevel());
                }
                levelCount.put(bug.getLevel(), ++count);
                map.put(bug.getBugVersion(), versionBug);
            });
            chart.setXChart(CollectionUtil.newArrayList(map.keySet()));
            Map<String, List<Long>> levelMap = new HashMap<>();
            for (String key : map.keySet()) {
                VersionBug versionBug = map.get(key);
                Map<String, Long> levelCount = versionBug.getCount();
                levels.forEach(level -> {
                    List<Long> counts = levelMap.get(level);
                    if (CollectionUtil.isEmpty(counts)) {
                        counts = new ArrayList<>();
                    }
                    counts.add(levelCount.getOrDefault(level, 0L));
                    levelMap.put(level, counts);
                });
            }
            chart.setSeries(levelMap.keySet().stream().map(key -> {
                ChartBean chartBean = new ChartBean();
                chartBean.setName(key);
                chartBean.setData(levelMap.get(key));
                return chartBean;
            }).collect(Collectors.toList()));

            result.put(projectName, chart);
        });

        chartVO.setCharts(CollectionUtil.newArrayList(result.values()));
        return chartVO;
    }

    public ChartVO bugHistogram(BugQuery query) {
        if (ObjectUtil.isNotNull(query.getGroupId())) {
            List<Group> groups = groupRepository.selectGroupUser(CollectionUtil.newArrayList(query.getGroupId()));
            if (CollectionUtil.isNotEmpty(groups)) {
                query.setRealUserNames(groups.stream().map(Group::getRealUserName).collect(Collectors.toList()));
            }
        }
        List<Bug> bugs = bugRepository.selectList(query);
        Map<String, List<Bug>> map = new HashMap<>();
        if (query.getStatisticsType() == 11) {
            map = CollectionFunctionUtils.groupTo(bugs, Bug::getModule);
        }
        ChartVO chartVO = new ChartVO();
        List<Long> data = new ArrayList<>();
        List<String> x = new ArrayList<>();
        chartVO.setData(data);
        chartVO.setXChart(x);
        // 对Map的value按照List的大小进行排序
        List<Map.Entry<String, List<Bug>>> sortedEntries = map.entrySet().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getValue().size(), o1.getValue().size()))
                .collect(Collectors.toList());
        sortedEntries.forEach(entry -> {
            x.add(entry.getKey());
            data.add((long) entry.getValue().size());
        });
        return chartVO;
    }

    public static class VersionBug {

        private Map<String, Long> count = new HashMap<>();

        public Map<String, Long> getCount() {
            return count;
        }

        public VersionBug setCount(Map<String, Long> count) {
            this.count = count;
            return this;
        }
    }
}
