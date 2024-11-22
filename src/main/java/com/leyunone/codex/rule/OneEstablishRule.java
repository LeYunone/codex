package com.leyunone.codex.rule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

import java.util.List;
import java.util.function.Consumer;

public class OneEstablishRule {

    private List<Rule> rules;

    private Consumer<Facts> consumer;

    public OneEstablishRule(List<Rule> rules, Consumer<Facts> consumer) {
        this.rules = rules;
        this.consumer = consumer;
    }

    private boolean evaluate(Facts facts) {
        boolean ok = false;
        for (Rule rule : rules) {
            ok = rule.evaluate(facts);
            if (ok) break;
        }
        return ok;
    }

    public void execute(Facts facts) {
        if (evaluate(facts)) {
            consumer.accept(facts);
        }
    }
}