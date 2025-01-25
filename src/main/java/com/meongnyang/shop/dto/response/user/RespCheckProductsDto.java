package com.meongnyang.shop.dto.response.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespCheckProductsDto {
    private List<CheckProduct> checkProducts;
    private int productsCount;

    @Data
    @Builder
    public static class CheckProduct {
        private Long productId;
        private String productName;
        private Long productPrice;
        private Long productPriceDiscount;
        private String groupName;
        private String categoryName;
        private String imgName;
    }
}
