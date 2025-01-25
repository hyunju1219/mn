package com.meongnyang.shop.dto.response.user;

import com.meongnyang.shop.entity.OrderDetail;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class RespGetOrderListDto {
    private List<OrderList> orderList;
    private int orderListCount;

    @Data
    @Builder
    public static class OrderList {
        private Long orderId;
        private Long userId;
        private String paymentId;
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
        private String paymentTypeName;
        private List<OrderDetail> orderDetailList;

    }

    @Data
    @Builder
    public static class OrderDetail {
        private Long id;
        private Long orderId;
        private Long productId;
        private String productName;
        private String petGroupName;
        private String categoryName;
        private Long productPrice;
        private Long productCount;
        private String imgName;
    }
}
