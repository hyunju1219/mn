package com.meongnyang.shop.dto.request;

import lombok.Data;

@Data
public class ReqModifyStockDetailDto {
    private Long id;
    private Long stockId;
    private Long count;
    private String status;
}
