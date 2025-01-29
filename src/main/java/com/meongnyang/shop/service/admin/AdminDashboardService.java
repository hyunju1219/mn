package com.meongnyang.shop.service.admin;

import com.meongnyang.shop.dto.response.admin.RespDashboardDto;
import com.meongnyang.shop.repository.OrderMapper;
import com.meongnyang.shop.repository.StockMapper;
import com.meongnyang.shop.repository.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminDashboardService {
    private final OrderMapper orderMapper;
    private final StockMapper stockMapper;
    private final AdminUserMapper adminUserMapper;

    public RespDashboardDto getDashboard() {
        RespDashboardDto respDashboardDto = orderMapper.getDashboardData();
        RespDashboardDto userData = adminUserMapper.getDashboardCustomer();
        respDashboardDto.setTotalCustomerCount(userData.getTotalCustomerCount());
        respDashboardDto.setTodayJoinCustomerCount(userData.getTodayJoinCustomerCount());
        respDashboardDto.setOrderStatusList(orderMapper.getDashboardOrderStatus());
        respDashboardDto.setStockStatusList(stockMapper.getDashboardStockStatus());
        respDashboardDto.setStatisticsStatusList(orderMapper.getDashboardStatisticsStatus());
        return respDashboardDto;
    }

}
