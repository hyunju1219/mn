package com.meongnyang.shop.dto.response.admin;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class RespOrderDetailDto {
    private Long id;
    private Long userId;
    private Long totalPrice;
    private Long orderItemCount;
    private LocalDate orderDate;
    private String orderStatus;
    private String orderName;
    private String zipcode;
    private String addressDefault;
    private String addressDetail;
    private String phone;
    private String request;
    private int paymentId;
    private String paymentName;

    private List<RespOrderProductsDto> products;

    @Data
    @Builder
    public static class RespOrderProductsDto{
        private Long id;
        private String productName;
        private Long productPrice;
        private Long productCount;
        private String imgName;
    }
}
