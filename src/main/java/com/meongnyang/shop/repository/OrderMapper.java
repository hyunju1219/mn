package com.meongnyang.shop.repository;

import com.meongnyang.shop.dto.response.admin.RespDashboardDto;
import com.meongnyang.shop.dto.response.admin.RespGetUserDetailDto;
import com.meongnyang.shop.dto.response.admin.RespStatisticsDto;
import com.meongnyang.shop.entity.Order;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    List<Order> findOrderAll();
    List<RespGetUserDetailDto.RespUserDetailProductDto> getOrderDetailProductsById(Long id);
    List<Order> getOrderByOption(Map<String, Object> params);
    List<Order> getUserTotalAmount();
    Order getOrderDetail(Long id);
    LocalDate getRecentOrderDate(Long userId);
    RespDashboardDto getDashboardData();
    List<RespDashboardDto.OrderStatus> getDashboardOrderStatus();
    List<RespDashboardDto.StatisticsStatus> getDashboardStatisticsStatus();
    int getOrderCountByOption(Map<String, Object> params);
    int getProductCountByOption(Map<String, Object> params);
    int deleteOrderById(Long id);
    int deleteOrderAll();
    List<RespStatisticsDto.SummaryStatistics> getSummaryStatisticsByDate(Map<String, LocalDate> params);
    List<RespStatisticsDto.BestProductCount> getBestProductCountByDate(Map<String, LocalDate> params);
    List<RespStatisticsDto.BestProductAmount> getBestProductAmountByDate(Map<String, LocalDate> params);
}