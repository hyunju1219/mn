package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.StockDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockDetailMapper {
    int save(StockDetail stockDetail);
    List<StockDetail> getIncommingList(Long stock_id);
    int modifyStatusById(@Param("id") Long id, @Param("status") String status);
    int saveOrder(StockDetail stockDetail);
    int modifyStatusByOrderDetailId(StockDetail stockDetail);
}
