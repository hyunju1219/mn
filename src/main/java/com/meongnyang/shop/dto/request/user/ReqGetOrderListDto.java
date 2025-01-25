package com.meongnyang.shop.dto.request.user;

import lombok.Data;

@Data
public class ReqGetOrderListDto {
    private Long userId;
    private Long page;
    private Long limit;
    private String paymentSelect;
    private String startDate;
    private String endDate;

}
