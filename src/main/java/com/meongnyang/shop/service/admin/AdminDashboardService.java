package com.meongnyang.shop.service.admin;

import com.meongnyang.shop.dto.response.admin.RespDashboardDto;
import com.meongnyang.shop.repository.OrderMapper;
import com.meongnyang.shop.repository.StockMapper;
import com.meongnyang.shop.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminDashboardService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private UserMapper userMapper;

    public RespDashboardDto getDashboard() {
        RespDashboardDto respDashboardDto = orderMapper.getDashboardData();
        RespDashboardDto userData = userMapper.getDashboardCustomer();
        respDashboardDto.setTotalCustomerCount(userData.getTotalCustomerCount());
        respDashboardDto.setTodayJoinCustomerCount(userData.getTodayJoinCustomerCount());
        respDashboardDto.setOrderStatusList(orderMapper.getDashboardOrderStatus());
        respDashboardDto.setStockStatusList(stockMapper.getDashboardStockStatus());
        respDashboardDto.setStatisticsStatusList(orderMapper.getDashboardStatisticsStatus());
        return respDashboardDto;
    }

}
