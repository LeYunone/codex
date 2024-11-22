package com.leyunone.codex.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.model.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.util.XxlJobRemotingUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(prefix = "xxl",name = "job.admin.addresses",matchIfMissing = false)
public class XxlJobUtil {
    public static final int SUCCESS_CODE = 200;
    private static String adminAddresses;
    private static String appname;
    private static Integer groupId=1;
    private static String accessToken;

    @Value("${xxl.job.admin.addresses:}")
    public void setAdminAddresses(String adminAddresses) {
        XxlJobUtil.adminAddresses = adminAddresses;
    }

    @Value("${xxl.job.accessToken:}")
    public void setAccessToken(String accessToken) {
        XxlJobUtil.accessToken = accessToken;
    }

    @Value("${xxl.job.executor.appname:}")
    public void setAppname(String appname) {
        XxlJobUtil.appname = appname;
    }

    @PostConstruct
    public void init() {
        try {
            initGroupId();
        } catch (Exception e) {
        }
    }

    /**
     * 以下为xxl-job的接口路由，请自行实现：
     * ADD_URL：添加xxl-job任务
     * UPDATE_URL：更新xxl-job任务
     * REMOVE_URL：删除xxl-job任务
     * PAUSE_URL：暂停指定id任务
     * START_URL：开始指定id任务
     *
     */
    private static final String ADD_URL = "/real/api/jobinfo/add";
    private static final String UPDATE_URL = "/real/api/jobinfo/update";
    private static final String REMOVE_URL = "/real/api/jobinfo/remove";
    private static final String PAUSE_URL = "/real/api/jobinfo/stop";
    private static final String START_URL = "/real/api/jobinfo/start";
    private static final String GET_GROUP_ID = "/real/api/jobgroup/getGroupId";


    /**
     * 添加任务
     *
     * @param jobInfo 任务信息
     * @return {@link String} 任务id
     **/
    public static String add(XxlJobInfo jobInfo) {
        jobInfo.setJobGroup(groupId);
        return doPost(adminAddresses + ADD_URL, jobInfo, String.class);
    }

    /**
     * 初始化获取执行器id
     **/
    public static void initGroupId() {
        // 查询对应groupId:
        Map<String, Object> param = new HashMap<>(4);
        param.put("appname", appname);
        Integer groupId = doPost(adminAddresses + GET_GROUP_ID, param, Integer.class);
        if (groupId == null) {
        }
        XxlJobUtil.groupId = groupId;
    }

    /**
     * 修改任务信息
     *
     * @param jobInfo 任务信息
     * @return {@link String}
     **/
    public static String update(XxlJobInfo jobInfo) {
        jobInfo.setJobGroup(groupId);
        return doPost(adminAddresses + UPDATE_URL, jobInfo, String.class);
    }


    /**
     * 删除任务
     *
     * @param id 任务id
     * @return {@link String}
     **/
    public static String remove(int id) {
        try {
            Map<String, Object> param = new HashMap<>(4);
            param.put("id", id);
            return doPost(adminAddresses + REMOVE_URL, param, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 暂停任务
     *
     * @param id 任务id
     * @return {@link String}
     **/
    public static String pause(int id) {
        Map<String, Object> param = new HashMap<>(4);
        param.put("id", id);
        return doPost(adminAddresses + PAUSE_URL, param, String.class);
    }

    /**
     * 开始任务
     *
     * @param id 任务id
     * @return {@link String}
     **/
    public static String start(int id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return doPost(adminAddresses + START_URL, param, String.class);
    }


    @SuppressWarnings("unchecked")
    public static <T> T doPost(String url, Object json, Class<T> clazz) {
        accessToken = "0F9567E2D1A742C7A52E3EF9F3EE3FBF";
        ReturnT<T> returnT = XxlJobRemotingUtil.postBody(url, accessToken, 10, json, clazz);
        if(StringUtils.isBlank(adminAddresses)) {
            return null;
        }
        AssertUtil.isFalse(returnT.getCode() != SUCCESS_CODE, returnT.getMsg());
        return returnT.getContent();
    }

}