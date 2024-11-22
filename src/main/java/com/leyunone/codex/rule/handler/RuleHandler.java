package com.leyunone.codex.rule.handler;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/8 16:07
 */
public interface RuleHandler<P> {

    void handler(P p);

    boolean check(P p);
}
