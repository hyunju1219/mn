package com.meongnyang.shop.service.admin;

import com.meongnyang.shop.dto.request.admin.ReqStatisticsDateDto;
import com.meongnyang.shop.dto.response.admin.RespStatisticsDto;
import com.meongnyang.shop.repository.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminStatisticsService {

    @Autowired
    private OrderMapper orderMapper;

    @Transactional(rollbackFor = Exception.class)
    public RespStatisticsDto getStatisticsInfo(ReqStatisticsDateDto dto) {
        Map<String, LocalDate> searchDates = new HashMap<>();
        searchDates.put("startDate", LocalDate.parse(dto.getStartDate()));
        searchDates.put("endDate", LocalDate.parse(dto.getEndDate()));
        return RespStatisticsDto.builder()
                .summaryStatistics(orderMapper.getSummaryStatisticsByDate(searchDates))
                .bestProductsCounts(orderMapper.getBestProductCountByDate(searchDates))
                .bestProductsAmounts(orderMapper.getBestProductAmountByDate(searchDates))
                .build();
    }
}
