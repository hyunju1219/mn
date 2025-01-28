package com.meongnyang.shop.service.admin;

import com.meongnyang.shop.dto.request.admin.ReqStatisticsDateDto;
import com.meongnyang.shop.dto.response.admin.RespStatisticsDto;
import com.meongnyang.shop.repository.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AdminStatisticsService {
    private final OrderMapper orderMapper;

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
