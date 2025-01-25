package com.meongnyang.shop.dto.request.admin;

import com.meongnyang.shop.entity.Stock;
import com.meongnyang.shop.entity.StockDetail;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReqAddStockDetailDto {
    private Long stockId;
    private int count;
    private LocalDate arrivalDate;
    private Long currentStock;
    private Long expectedStock;

    public StockDetail toEntity() {
        return StockDetail.builder()
                .stockId(stockId)
                .arrivalQuantity(count)
                .arrivalDate(arrivalDate.atStartOfDay())
                .build();
    }

    public Stock toStockEntity() {
        return Stock.builder()
                .id(stockId)
                .currentStock(currentStock)
                .expectedStock(expectedStock)
                .build();
    }
}