package com.leyunone.codex.rule.handler;

import cn.hutool.core.collection.CollectionUtil;
import com.leyunone.codex.dao.impl.GroupUserRepository;
import com.leyunone.codex.rule.bean.AlarmResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/17 9:20
 */
@Service
public class EmailExectueHandler extends ExecuteHandler<AlarmResult> implements InitializingBean {

    @Resource
    private JavaMailSender javaMailSender;
    @Autowired
    private GroupUserRepository groupUserRepository;
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void handler(AlarmResult alarmResult) {
        // new 一个简单邮件消息对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 和配置文件中的的username相同，相当于发送方
        message.setFrom(from);
        // 收件人邮箱
        message.setTo(alarmResult.getBotUrl());
        // 标题
        message.setSubject("软件bi系统预警");
        // 正文
        String mess = this.buildContent(alarmResult.getContent(),
                alarmResult.getAlarmUser(),
                alarmResult.getSeeUser(),
                alarmResult.getRealProjectNames(),
                alarmResult.getAlarmData());
        if (StringUtils.isBlank(mess)) return;
        message.setText(mess);
        // 发送
        javaMailSender.send(message);
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

    @Override
    public String getKey() {
        return "0";
    }
}
