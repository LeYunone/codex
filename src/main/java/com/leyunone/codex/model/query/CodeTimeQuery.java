package com.leyunone.codex.model.query;

import lombok.Data;

import java.util.List;

@Data
public class CodeTimeQuery {

    private String startDate;

    private String endDate;

    private Integer groupId;

    private String projectId;

    private String userId;

    private Integer timeType = 1;

    private Integer maxNum;

    private Integer realUserId;

    private List<String> userIds;

    private Integer limit = 1000;

    private Integer size;

    private List<String> realUserNames;
}
