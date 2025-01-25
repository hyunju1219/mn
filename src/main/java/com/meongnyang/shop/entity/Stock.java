package com.meongnyang.shop.entity;

import com.meongnyang.shop.dto.response.admin.RespStockDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private Long id;
    private Long productId;
    private String productName;
    private Long currentStock;
    private Long expectedStock;
    private Long minAlertQuantity;
    private int alertSetting;

    private Product product;
    private List<StockDetail> stockDetails;

    public RespStockDetailDto toDto() {
        return RespStockDetailDto.builder()
                .id(id)
                .productId(productId)
                .currentStock(currentStock)
                .expectedStock(expectedStock)
                .minAlertQuantity(minAlertQuantity)
                .alertSetting(alertSetting)
                .productName(product.getProductName())
                .build();
    }
}
