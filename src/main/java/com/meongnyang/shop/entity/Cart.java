package com.meongnyang.shop.entity;

import com.meongnyang.shop.dto.response.user.RespGetCartDto;
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
public class Cart {
    private Long id;
    private Long userId;
    private Long productId;
    private Long productCount;
    private LocalDate cartUpdateDate;

    private Product product;
    private List<ImgUrl> imgUrls;

    public RespGetCartDto.CartContent toDto(String imgName) {
        return RespGetCartDto.CartContent.builder()
                .cartId(id)
                .userId(userId)
                .productId(productId)
                .productCount(productCount)
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productDiscountPrice(product.getProductPriceDiscount())
                .imgName(imgName)
                .build();
    }
}
