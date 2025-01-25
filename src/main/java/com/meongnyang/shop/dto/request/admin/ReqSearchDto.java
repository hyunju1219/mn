package com.meongnyang.shop.dto.request.admin;

import lombok.Data;

@Data
public class ReqSearchDto {
    private Long page;
    private Long limit;
    private String search;
    private String option;
    private String startDate;
    private String endDate;
}
