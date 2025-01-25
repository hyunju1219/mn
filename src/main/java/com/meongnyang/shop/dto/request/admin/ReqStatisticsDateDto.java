package com.meongnyang.shop.dto.request.admin;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReqStatisticsDateDto {
    private String startDate;
    private String endDate;
}
