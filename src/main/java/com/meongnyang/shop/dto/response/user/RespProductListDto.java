package com.meongnyang.shop.dto.response.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespProductListDto {
    private List<SearchContent> products;
    private Integer productCount;

    @Data
    @Builder
    public static class SearchContent {
        private Long productId;
        private String productName;
        private String petGroupName;
        private String categoryName;
        private Long productPrice;
        private Long productPriceDiscount;
        private String productDetail;
        private String productBrand;
        private String productModel;
        private String imgName;
    }
}
