package com.meongnyang.shop.repository.user;

import com.meongnyang.shop.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserOrderDetailMapper {
    int save(OrderDetail orderDetail);
    List<OrderDetail> findOrderDetailByOrderId(Long orderId);
    List<OrderDetail> findOrderProductIdByOrderId(Long orderId);
}
