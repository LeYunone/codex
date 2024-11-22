//package com.leyunone.codex.task;
//
//import com.leyunone.codex.service.CodeXHourSummaryService;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.IJobHandler;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * GitLab 按时代码全量总结 任务
// */
//@Component
//public class GitLabHourSummaryHandler extends IJobHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(GitLabHourSummaryHandler.class);
//
//    @Autowired
//    private CodeXHourSummaryService codexHourSummaryService;
//
//    @XxlJob(value = "git_summary_hour")
//    @Override
//    public void execute() {
//        String jobParam = XxlJobHelper.getJobParam();
//        String[] split = jobParam.split("#");
//        String token = null;
//        String url = null;
//        if(split.length>1){
//            //自定义时间场景
//            url = split[0];
//            token = split[1];
//        }
//
//        codexHourSummaryService.summaryCodeX(url,token);
//        XxlJobHelper.handleSuccess("success");
//    }
//}
