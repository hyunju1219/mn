package com.meongnyang.shop.service.admin;

import com.meongnyang.shop.dto.request.admin.ReqAddStockDetailDto;
import com.meongnyang.shop.repository.StockDetailMapper;
import com.meongnyang.shop.repository.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminStockDetailService {
    private final StockMapper stockMapper;

    @Autowired
    private StockDetailMapper stockDetailMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean save(ReqAddStockDetailDto dto) {
        stockDetailMapper.save(dto.toEntity());
        stockMapper.modifyCurrentAndExpectedStock(dto.toStockEntity());
        return true;
    }
}
