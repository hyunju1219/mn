package com.meongnyang.shop.dto.request.user;

import com.meongnyang.shop.entity.Order;
import lombok.Data;
import lombok.Getter;
import java.util.List;

@Data
public class ReqPostOrderDto {
    private Long userId;
    private String orderName;
    private String zipcode;
    private String addressDefault;
    private String addressDetail;
    private String phone;
    private String email;
    private String request;
    private Long totalPrice;
    private String paymentId;
    private String paymentMethod;

    private List<ProductEasy> products;



    @Getter
    public static class ProductEasy{
        private Long productId;
        private int productCount;
        private Long productPrice;
    }

    public Order toEntity(int paymentTypeId) {
        return Order.builder()
                .userId(userId)
                .orderName(orderName)
                .totalPrice(totalPrice)
                .zipcode(zipcode)
                .addressDefault(addressDefault)
                .addressDetail(addressDetail)
                .phone(phone)
                .email(email)
                .request(request)
                .paymentId(paymentId)
                .paymentType(paymentTypeId)
                .build();
    }
}
