package com.meongnyang.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {

    List<OrderStatus> orderStatusList;
    List<StockStatus> stockStatusList;
    List<StatisticsStatus> statisticsStatusList;

    @Data
    public static class OrderStatus {
        private String orderName;
        private LocalDate orderDate;
        private String orderStatus;
    }

    @Data
    public static class StockStatus {
        private Long productId;
        private String productName;
        private Long currentStock;
        private Long expectedStock;
    }

    @Data
    public static class StatisticsStatus {
        private LocalDate date;
        private Long orderCount;
        private Long orderAmount;
    }
}
