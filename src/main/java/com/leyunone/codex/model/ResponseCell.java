package com.leyunone.codex.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCell<BO,API> {

    private BO dataBO;

    private API dataApi;

    public static <BO,API> ResponseCell build(BO dataBO, API dataApi) {
        return ResponseCell.builder().dataBO(dataBO).dataApi(dataApi).build();
    }
}
