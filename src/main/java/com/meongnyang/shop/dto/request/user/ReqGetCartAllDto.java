package com.meongnyang.shop.dto.request.user;

import lombok.Data;

@Data
public class ReqGetCartAllDto {
    private Long userId;
    private Long page;
    private Long limit;
}
