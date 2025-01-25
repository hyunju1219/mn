package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    int deleteOrderDetailAll();
    int deleteOrderDetailByOrderId(Long orderId);
}
