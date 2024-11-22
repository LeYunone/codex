package com.leyunone.codex.rule.handler;

import com.leyunone.codex.rule.RuleExecutorFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/17 9:21
 */
public abstract class ExecuteHandler<T> implements InitializingBean {

    public abstract void handler(T t);

    public abstract String getKey();

    @Override
    public void afterPropertiesSet() throws Exception {
        RuleExecutorFactory.regist(getKey(), this);
    }
}
