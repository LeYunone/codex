package com.leyunone.codex.rule.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.dao.impl.GroupUserRepository;
import com.leyunone.codex.rule.bean.AlarmResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/8 16:07
 */
@Service
public class AlarmExecuteHandler extends ExecuteHandler<AlarmResult> {

    private final GroupUserRepository groupUserRepository;

    public AlarmExecuteHandler(GroupUserRepository groupUserRepository) {
        this.groupUserRepository = groupUserRepository;
    }

    @Override
    public void handler(AlarmResult alarmResult) {
        String mess = buildContent(alarmResult.getContent(),
                alarmResult.getAlarmUser(),
                alarmResult.getSeeUser(),
                alarmResult.getRealProjectNames(),
                alarmResult.getAlarmData());
        if (StringUtils.isBlank(mess)) return;
        JSONObject o = new JSONObject();
        o.put("msgtype", "markdown");
        JSONObject content = new JSONObject();
        o.put("markdown", content);
        content.put("content", mess);
        HttpUtil.createRequest(Method.POST, alarmResult.getBotUrl()).body(
                JSONObject.toJSONString(o)
        ).header("Content-Type", "application/json; charset=utf-8").execute().body();
    }

    @Override
    public String getKey() {
        return "1";
    }


    private String buildContent(String content, Set<String> users, Set<String> seeUser, Set<String> realProjectNames, Set<String> alarmData) {

        users.removeIf(t -> !seeUser.contains(t));
        if (CollectionUtil.isEmpty(users)) return null;
        String userMess = CollectionUtil.join(users, ",");
        String groupMess = "";
        if (CollectionUtil.isNotEmpty(users)) {
            List<String> groups = groupUserRepository.selectGroupByUser(CollectionUtil.newArrayList(users));
            groupMess = CollectionUtil.join(groups, ",");
        }
        String projectMess = CollectionUtil.join(realProjectNames, "\n");
        String alarmMess = CollectionUtil.join(alarmData, "\n");
        String mess = content.replace("#{user}", userMess)
                .replace("#{projects}", projectMess)
                .replace("#{alarmDatas}", alarmMess)
                .replace("#{groups}", groupMess);
        return mess;
    }
}
