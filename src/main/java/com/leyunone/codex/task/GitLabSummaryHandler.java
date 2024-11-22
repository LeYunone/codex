package com.leyunone.codex.task;

import com.leyunone.codex.service.CodeXSummaryService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * GitLab 代码全量总结 任务
 */
@Component
public class GitLabSummaryHandler extends IJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(GitLabSummaryHandler.class);

    @Autowired
    private CodeXSummaryService codexSummaryService;

    @XxlJob(value = "git_summary")
    @Override
    public void execute() {
        String jobParam = XxlJobHelper.getJobParam();
        String[] split = jobParam.split("#");
        String token = null;
        String url = null;
        if(split.length>1){
            //自定义时间场景
            url = split[0];
            token = split[1];
        }

        try {
            codexSummaryService.summaryCodeX(url,token);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        XxlJobHelper.handleSuccess("success");
    }

}
