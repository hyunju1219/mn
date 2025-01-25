package com.meongnyang.shop.dto.response.admin;

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
public class RespDashboardDto {
    private Long totalPrice;
    private Long totalCount;
    private Long refundPrice;
    private Long refundCount;
    private Long todayTotalPrice;
    private Long todayTotalCount;
    private Long todayRefundPrice;
    private Long todayRefundCount;
    private Long totalCustomerCount;
    private Long todayJoinCustomerCount;

    List<RespDashboardDto.OrderStatus> orderStatusList;
    List<RespDashboardDto.StockStatus> stockStatusList;
    List<RespDashboardDto.StatisticsStatus> statisticsStatusList;

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
        private Long refundCount;
        private Long refundAmount;
    }
}
