package com.leyunone.codex.rule.expression;

import com.leyunone.codex.model.dto.AlarmBotDTO;
import com.leyunone.codex.rule.RuleExpressionFactory;
import com.leyunone.codex.rule.bean.AlarmResult;
import org.springframework.beans.factory.InitializingBean;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/8 17:52
 */
public abstract class AlarmExpressionHandler implements InitializingBean {

    public abstract boolean checkExpression(AlarmResult t, AlarmBotDTO.Condition condition);

    public abstract String getKey();
    
    @Override
    public void afterPropertiesSet(){
        RuleExpressionFactory.regist(getKey(),this);
    }
}
