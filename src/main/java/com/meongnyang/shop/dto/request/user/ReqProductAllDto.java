package com.meongnyang.shop.dto.request.user;

import lombok.Data;

@Data
public class ReqProductAllDto {
    private Long page;
    private Long limit;
    private String groupName;
    private Long categoryId;
}
