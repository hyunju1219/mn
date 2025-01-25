package com.meongnyang.shop.dto.response.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespCurrentStockDto {
    private List<CurrentStock> currentStocks;

    @Data
    @Builder
    public static class CurrentStock {
        private Long productId;
        private Long currentStock;
    }
}
