package com.leyunone.codex.model.dto;

import lombok.Data;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/3/8 10:50
 */
public class AlarmInfoDTO {

    private Integer bug;

    private String botUrl;
    
    private Integer reopen;
    
    private Integer code;

    public Integer getBug() {
        return bug;
    }

    public AlarmInfoDTO setBug(Integer bug) {
        this.bug = bug;
        return this;
    }

    public String getBotUrl() {
        return botUrl;
    }

    public AlarmInfoDTO setBotUrl(String botUrl) {
        this.botUrl = botUrl;
        return this;
    }

    public Integer getReopen() {
        return reopen;
    }

    public AlarmInfoDTO setReopen(Integer reopen) {
        this.reopen = reopen;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public AlarmInfoDTO setCode(Integer code) {
        this.code = code;
        return this;
    }
}
