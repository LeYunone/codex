//package com.leyunone.codex.service;
//
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.leyunone.codex.model.JobInfo;
//import com.leyunone.codex.model.XxlJobInfo;
//import com.leyunone.codex.util.XxlJobUtil;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class XxlJobService {
//
//
//    public String addJob(JobInfo jobInfo) {
//        XxlJobInfo xxlJobInfo = convertXxlJobInfo(jobInfo);
//        return XxlJobUtil.add(xxlJobInfo);
//    }
//
//    public void removeJob(String jobId) {
//        XxlJobUtil.remove(Integer.parseInt(jobId));
//    }
//
//    public void updateJob(JobInfo jobInfo) {
//        XxlJobInfo xxlJobInfo = convertXxlJobInfo(jobInfo);
//        XxlJobUtil.update(xxlJobInfo);
//    }
//
//    public void startJob(String id) {
//        if(StringUtils.isNotBlank(id) && !"null".equals(id)){
//            XxlJobUtil.start(Integer.parseInt(id));
//        }
//    }
//
//    public void pauseJob(String id) {
//        if(StringUtils.isNotBlank(id) && !"null".equals(id)){
//            XxlJobUtil.pause(Integer.parseInt(id));
//        }
//    }
//
//
//    private XxlJobInfo convertXxlJobInfo(JobInfo jobInfo) {
//        XxlJobInfo xxlJobInfo = new XxlJobInfo();
//        xxlJobInfo.setJobDesc(jobInfo.getJobDesc());
//        xxlJobInfo.setExecutorRouteStrategy("SHARDING_BROADCAST");
//        xxlJobInfo.setAuthor(jobInfo.getAuthor());
//        xxlJobInfo.setScheduleType(jobInfo.getScheduleType());
//        xxlJobInfo.setScheduleConf(jobInfo.getScheduleTime());
//        xxlJobInfo.setGlueType("BEAN");
//        xxlJobInfo.setExecutorHandler("alarm_bot");
//        xxlJobInfo.setExecutorParam(jobInfo.getJobParam());
//        xxlJobInfo.setMisfireStrategy("DO_NOTHING");
//        xxlJobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
//        xxlJobInfo.setTriggerStatus(jobInfo.getStatus());
//        if (jobInfo.getId() != null) {
//            xxlJobInfo.setId(jobInfo.getId());
//        }
//        if (jobInfo.getCreateTime() != null) {
//            xxlJobInfo.setAddTime(Date.from(jobInfo.getCreateTime().atZone(ZoneId.systemDefault()).toInstant()));
//        }
//        if (jobInfo.getUpdateTime() != null) {
//            xxlJobInfo.setUpdateTime(Date.from(jobInfo.getUpdateTime().atZone(ZoneId.systemDefault()).toInstant()));
//        }
//        return xxlJobInfo;
//    }
//}
