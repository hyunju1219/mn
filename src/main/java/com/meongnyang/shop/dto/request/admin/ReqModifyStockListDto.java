package com.meongnyang.shop.dto.request.admin;

import com.meongnyang.shop.entity.Stock;
import lombok.Data;

import java.util.List;

@Data
public class ReqModifyStockListDto {
    private List<Stock> modifyStockList;
}
