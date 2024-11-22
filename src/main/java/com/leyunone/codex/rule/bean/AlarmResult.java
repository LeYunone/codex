package com.leyunone.codex.rule.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/4/8 17:37
 */
@Getter
@Setter
public class AlarmResult {

    private String botUrl;
    private String content;
    private Set<String> seeUser = new HashSet<>();
    private Set<String> groupNames = new HashSet<>();
    private Set<String> realProjectNames = new HashSet<>();
    private Set<String> alarmData = new HashSet<>();
    private Set<String> alarmUser = new HashSet<>();
}
