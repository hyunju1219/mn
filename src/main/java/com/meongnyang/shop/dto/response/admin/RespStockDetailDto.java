package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.StockDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespStockDetailDto {
    private Long id;
    private Long productId;
    private Long currentStock;
    private Long expectedStock;
    private Long minAlertQuantity;
    private int alertSetting;
    private String productName;
    List<StockDetailList> stockDetailList;
    List<StockDetailList> incommingList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StockDetailList {
        private Long id;
        private String type;
        private String createDate;
        private String arrivalDate;
        private int arrivalQuantity;
        private String arrivedDate;
        private String status;
    }

}