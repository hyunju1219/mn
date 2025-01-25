package com.meongnyang.shop.dto.request.admin;

import lombok.Data;

@Data
public class ReqStockSearchOptionDto {
    private int page;
    private int limit;
    private String search;
    private String option;
}
