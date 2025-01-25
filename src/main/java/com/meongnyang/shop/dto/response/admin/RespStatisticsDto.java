package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.Product;
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
public class RespStatisticsDto {
    List<SummaryStatistics> summaryStatistics;
    List<BestProductCount> bestProductsCounts;
    List<BestProductAmount> bestProductsAmounts;

    @Data
    public static class SummaryStatistics {
        private LocalDate orderDate;
        private String orderStatus;
        private Long totalPrice;
        private Long totalCount;
    }

    @Data
    public static class BestProductCount {
        private Long productId;
        private String productName;
        private Long productCount;
    }

    @Data
    public static class BestProductAmount {
        private Long productId;
        private String productName;
        private Long productPrice;
    }
}
