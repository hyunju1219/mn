package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.Stock;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespGetStocksDto {
    List<Stock> stockList;
    int stockListCount;
 }
