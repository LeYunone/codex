package com.leyunone.codex.rule.enter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.dao.entry.*;
import com.leyunone.codex.dao.impl.*;
import com.leyunone.codex.model.dto.AlarmBotDTO;
import com.leyunone.codex.rule.*;
import com.leyunone.codex.rule.expression.AlarmExpressionHandler;
import com.leyunone.codex.rule.handler.ExecuteHandler;
import com.leyunone.codex.rule.bean.AlarmResult;
import com.leyunone.codex.rule.bean.AlarmRuleModel;
import lombok.RequiredArgsConstructor;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.core.RuleBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/8 15:59
 */
@RequiredArgsConstructor
@Service
public class AlarmRuleEnterService implements RuleEnterService<String> {

    private final AlarmBotRepository alarmBotRepository;
    private final RealUserRepository realUserRepository;
    private final GroupRepository groupRepository;
    private final RealProjectRelationRepository realProjectRelationRepository;
    private final ProjectUserRepository projectUserRepository;

    @Override
    public void run(String botId) {
        AlarmBot alarmBot = alarmBotRepository.selectById(botId);
        List<AlarmBotDTO.Condition> conditions = this.getConditions(alarmBot);
        AlarmResult alarmResult = new AlarmResult();
        alarmResult.setSeeUser(this.getAlarmUser(alarmBot));
        alarmResult.setContent(alarmBot.getContent());
        alarmResult.setBotUrl(alarmBot.getBotUrl());
        Facts facts = new Facts();
        //全部满足
        List<Rule> rules = conditions.stream().map(condition -> new RuleBuilder()
                .priority(0)
                .when(fact -> RuleExpressionFactory.get(String.valueOf(condition.getKey()), AlarmExpressionHandler.class).checkExpression(alarmResult, condition))
                .build()).collect(Collectors.toList());

        ExecuteHandler executeHandler = RuleExecutorFactory.get(String.valueOf(alarmBot.getAlarmType()), ExecuteHandler.class);
        if (alarmBot.getTriggerType() == 1) {
            AllEstablishRule allEstablishRule = new AllEstablishRule(rules, (f) -> {
                executeHandler.handler(alarmResult);
            });
            allEstablishRule.execute(facts);
        } else {
            //满足一个
            OneEstablishRule oneEstablishRule = new OneEstablishRule(rules, (f -> {
                executeHandler.handler(alarmResult);
            }));
            oneEstablishRule.execute(facts);
        }
    }

    private List<AlarmBotDTO.Condition> getConditions(AlarmBot alarmBot) {
        AlarmRuleModel alarmRuleModel = new AlarmRuleModel();
        List<AlarmBotDTO.Condition> conditions = new ArrayList<>();
        if (StringUtils.isNotBlank(alarmBot.getBugCountCondition())) {
            //数量预警
            AlarmBotDTO.Condition countCondition = JSONObject.parseObject(alarmBot.getBugCountCondition(), AlarmBotDTO.Condition.class);
            alarmRuleModel.setBugCountCondition(countCondition);
            conditions.add(countCondition);
        }
        if (ObjectUtil.isNotNull(alarmBot.getBugReopenCondition())) {
            //reopen率预警
            AlarmBotDTO.Condition reopenCondition = JSONObject.parseObject(alarmBot.getBugReopenCondition(), AlarmBotDTO.Condition.class);
            alarmRuleModel.setBugReopenCondition(reopenCondition);
            conditions.add(reopenCondition);
        }
        if (ObjectUtil.isNotNull(alarmBot.getCodeCondition())) {
            //代码函数预警
            AlarmBotDTO.Condition codeCondition = JSONObject.parseObject(alarmBot.getCodeCondition(), AlarmBotDTO.Condition.class);
            alarmRuleModel.setCodeCondition(codeCondition);
            conditions.add(codeCondition);
        }
        if (ObjectUtil.isNotNull(alarmBot.getBugRateCondition())) {
            //缺陷率预警
            AlarmBotDTO.Condition rateCondition = JSONObject.parseObject(alarmBot.getBugRateCondition(), AlarmBotDTO.Condition.class);
            alarmRuleModel.setBugRateCondition(rateCondition);
            conditions.add(rateCondition);
        }
        if (ObjectUtil.isNotNull(alarmBot.getSubmitCondition())) {
            //提交频率预警
            AlarmBotDTO.Condition submitCondition = JSONObject.parseObject(alarmBot.getSubmitCondition(), AlarmBotDTO.Condition.class);
            alarmRuleModel.setSubmitCondition(submitCondition);
            conditions.add(submitCondition);
        }
        return conditions;
    }

    private Set<String> getAlarmUser(AlarmBot alarmBot) {
        Set<String> user = new HashSet<>();
        /**
         * 监控人员
         */
        if (ObjectUtil.isNotNull(alarmBot.getAlarmAll()) && alarmBot.getAlarmAll() == 1) {
            user.addAll(realUserRepository.selectByCon(null).stream().map(RealUser::getRealUserName).collect(Collectors.toList()));
        } else {
            if (StringUtils.isNotBlank(alarmBot.getAlarmObject())) {
                AlarmBotDTO.ObjectInfo info = JSONObject.parseObject(alarmBot.getAlarmObject(), AlarmBotDTO.ObjectInfo.class);
                if (CollectionUtil.isNotEmpty(info.getGroupIds())) {
                    //部门指标
                    List<Group> groups = groupRepository.selectGroupUser(info.getGroupIds());
                    if (CollectionUtil.isNotEmpty(groups)) {
                        user.addAll(groups.stream().map(Group::getRealUserName).collect(Collectors.toList()));
                    }
                } else if (CollectionUtil.isNotEmpty(info.getRealProjectNames())) {
                    //项目指标
                    List<RealProjectRelation> realProjectRelations = realProjectRelationRepository.selectByProjectIds(info.getRealProjectNames());
                    if (CollectionUtil.isNotEmpty(realProjectRelations)) {
                        List<ProjectUser> projectUsers = projectUserRepository.selectByProjectIds(realProjectRelations.stream().map(RealProjectRelation::getProjectId).collect(Collectors.toList()));
                        if (CollectionUtil.isNotEmpty(projectUsers)) {
                            user.addAll(projectUsers.stream().map(ProjectUser::getRealUserName).collect(Collectors.toList()));
                        }
                    }
                } else if (CollectionUtil.isNotEmpty(info.getUsers())) {
                    //人员指标
                    user.addAll(info.getUsers());
                }
            }
        }
        return user;
    }
}
