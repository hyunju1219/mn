package com.meongnyang.shop.entity;

import com.meongnyang.shop.dto.response.user.RespGetOrderListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private Long id;
    private Long orderId;
    private Long productId;
    private Long productPrice;
    private Long productCount;

    private Product product;

    public RespGetOrderListDto.OrderDetail toDto() {
        return RespGetOrderListDto.OrderDetail.builder()
                .id(id)
                .orderId(orderId)
                .productId(productId)
                .productName(product.getProductName())
                .petGroupName(product.getPetGroup().getCategoryGroupName())
                .categoryName(product.getCategory().getCategoryName())
                .productPrice(productPrice)
                .productCount(productCount)
                .imgName(product.getImgUrls().get(0).getImgName())
                .build();
    }
}
