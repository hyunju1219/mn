package com.meongnyang.shop.dto.response.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RespGetCartDto {

    private List<RespGetCartDto.CartContent> cartList;
    private int cartListCount;


    @Builder
    @Data
    public static class CartContent {
        private Long cartId;
        private Long userId;
        private Long productId;
        private Long productCount;
        private String productName;
        private Long productPrice;
        private Long productDiscountPrice;

        private String imgName;
    }
}
