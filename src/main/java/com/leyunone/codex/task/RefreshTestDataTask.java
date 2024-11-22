package com.leyunone.codex.task;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.dao.BugDao;
import com.leyunone.codex.dao.CommitDao;
import com.leyunone.codex.dao.entry.Bug;
import com.leyunone.codex.dao.entry.Commit;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * :)
 * TODO
 * 定时刷新测试数据
 * 每天的时间往后推一天
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/8/21
 */
@EnableScheduling
@Component
public class RefreshTestDataTask {

    private final BugDao bugDao;
    private final CommitDao commitDao;

    public RefreshTestDataTask(BugDao bugDao, CommitDao commitDao) {
        this.bugDao = bugDao;
        this.commitDao = commitDao;
    }

    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Scheduled(cron = "0 0 1 * * ?")
    public void updateTestData() {
        List<Bug> bugs = bugDao.selectByCon(null);
        bugs.forEach(b -> {
            String createTime = b.getCreateTime();
            String endTime = b.getEndTime();
            if (StringUtils.isNotBlank(createTime)) {
                LocalDateTime parse = LocalDateTime.parse(createTime, df);
                b.setCreateTime(parse.plusDays(1).format(df));
            }
            if (StringUtils.isNotBlank(endTime)) {
                LocalDateTime parse = LocalDateTime.parse(createTime, df);
                b.setCreateTime(parse.plusDays(1).format(df));
            }
        });
        List<Commit> commits = commitDao.selectByCon(null);
        commits.forEach(commit -> {
            Date commitDate = commit.getCommitDate();
            if (ObjectUtil.isNotNull(commitDate)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(commitDate);
                calendar.add(Calendar.DATE, 1); // 在日期上增加一天

                // 获取修改后的日期
                Date newDate = calendar.getTime();
                commit.setCommitDate(newDate);
            }
        });
        bugDao.updateBatchById(bugs);
        commitDao.updateBatchById(commits);
    }
}
