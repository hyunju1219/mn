package com.meongnyang.shop.repository;

import com.meongnyang.shop.dto.response.admin.RespDashboardDto;
import com.meongnyang.shop.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface StockMapper {
    int save(Stock stock);
    int modifyStockByProductId(Stock stock);
    int modifyStockById(Stock stock);
    int modifyCurrentAndExpectedStock(Stock stock);
    int modifyAlertSetting(Stock stock);
    int deleteStockByProductId(Long productId);
    int deleteStockAll();
    Stock findStockByProductId(Long productId);
    Stock findStockDataById(Long id);
    List<Stock> findStockAll();
    Stock getStockDetail(Long id);
    int getAllCount(
            @Param("option") String option,
            @Param("searchWord") String search,
            @Param("startIndex") int startIndex,
            @Param("limit") int limit
    );
    List<Stock> findStockByOption(
            @Param("option") String option,
            @Param("searchWord") String search,
            @Param("startIndex") int startIndex,
            @Param("limit") int limit);

    List<RespDashboardDto.StockStatus> getDashboardStockStatus();
    List<Stock> findCurrenStockByProductIds(List<Long> productIds);
    int modifyCurrentStockByProductId(Map<String, Object> params);
    int modifyExpectedStockByProductId(Map<String, Object> params);
}
