package com.meongnyang.shop.entity;

import com.meongnyang.shop.dto.response.user.RespGetOrderListDto;
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
public class Order {
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
    private String email;
    private String request;

    private String paymentId;
    private int paymentType;

    private Payment payment;
    private List<OrderDetail> orderDetails;

    public RespGetOrderListDto.OrderList toDto() {
        return RespGetOrderListDto.OrderList.builder()
                .orderId(id)
                .userId(userId)
                .paymentId(paymentId)
                .totalPrice(totalPrice)
                .orderItemCount(orderItemCount)
                .orderDate(orderDate)
                .orderStatus(orderStatus)
                .orderName(orderName)
                .zipcode(zipcode)
                .addressDefault(addressDefault)
                .addressDetail(addressDetail)
                .phone(phone)
                .email(email)
                .request(request)
                .paymentTypeName(payment.getPaymentName())
                .build();
    }
}
