package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespGetOrdersDto {
    private List<Order> orderList;
    private int orderListCount;
    private int productCount;
}
