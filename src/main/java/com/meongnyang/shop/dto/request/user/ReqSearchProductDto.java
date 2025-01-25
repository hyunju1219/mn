package com.meongnyang.shop.dto.request.user;

import lombok.Data;

@Data
public class ReqSearchProductDto {
    private Long page;
    private Long limit;
    private String search;
}
