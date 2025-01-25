package com.meongnyang.shop.service.user;

import com.meongnyang.shop.dto.response.user.RespCurrentStockDto;
import com.meongnyang.shop.entity.Stock;
import com.meongnyang.shop.repository.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStockService {

    @Autowired
    private StockMapper stockMapper;

    public RespCurrentStockDto getCurrentStock(List<Long> productIds) {
        List<Stock> stockList = stockMapper.findCurrenStockByProductIds(productIds);

        List<RespCurrentStockDto.CurrentStock> stockListDto = stockList.stream()
                .map(stock -> {
                    RespCurrentStockDto.CurrentStock stockDto = RespCurrentStockDto.CurrentStock.builder()
                            .productId(stock.getProductId())
                            .currentStock(stock.getCurrentStock())
                            .build();
                    return stockDto;
                }).collect(Collectors.toList());

        return RespCurrentStockDto.builder()
                .currentStocks(stockListDto)
                .build();
    }
}
