package com.meongnyang.shop.service.admin;

import com.meongnyang.shop.dto.request.ReqModifyStockDetailDto;
import com.meongnyang.shop.dto.request.admin.ReqAlertSettingDto;
import com.meongnyang.shop.dto.request.admin.ReqModifyStockListDto;
import com.meongnyang.shop.dto.request.admin.ReqStockSearchOptionDto;
import com.meongnyang.shop.dto.response.admin.RespGetStocksDto;
import com.meongnyang.shop.dto.response.admin.RespStockDetailDto;
import com.meongnyang.shop.entity.Stock;
import com.meongnyang.shop.entity.StockDetail;
import com.meongnyang.shop.repository.StockDetailMapper;
import com.meongnyang.shop.repository.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminStockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockDetailMapper stockDetailMapper;

    public RespGetStocksDto getStocks() {
        List<Stock> stockList = stockMapper.findStockAll();
        return RespGetStocksDto.builder()
                .stockList(stockList)
                .stockListCount(stockList.size())
                .build();
    }

    public RespStockDetailDto getStockDetail(Long id) {
        Stock stocks = stockMapper.getStockDetail(id);
        RespStockDetailDto respDto = stocks.toDto();
        respDto.setStockDetailList(stocks.getStockDetails().stream().map(StockDetail::toDto).collect(Collectors.toList()));
        respDto.setIncommingList(
                stockDetailMapper.getIncommingList(id).stream().map(StockDetail::toFullDto).collect(Collectors.toList())
        );
        return respDto;
    }

    public RespGetStocksDto getProductsStockByOption(ReqStockSearchOptionDto dto) {
        List<Stock> stockList = stockMapper.findStockByOption(
                dto.getOption(),
                dto.getSearch(),
                (dto.getPage() - 1) * dto.getLimit(),
                dto.getLimit()
        );
        System.out.println(dto);
        return RespGetStocksDto.builder()
                .stockList(stockList)
                .stockListCount(stockMapper.getAllCount(dto.getOption(), dto.getSearch(), (dto.getPage() - 1) * dto.getLimit(), dto.getLimit()))
                .build();
    }

    public void modifyStock(ReqModifyStockListDto dto) {
        List<Stock> stockList = dto.getModifyStockList();
        for(Stock stock : stockList) {
            stockMapper.modifyStockById(stock);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void modifyStockAndStatus(ReqModifyStockDetailDto dto) {
        Stock stock = stockMapper.findStockDataById(dto.getStockId());
        Long currentStock = stock.getCurrentStock();
        Long expectedStock = stock.getExpectedStock();
        if (dto.getStatus().equals("확정")) {
            currentStock += dto.getCount();
        } else if (dto.getStatus().equals("취소")) {
            expectedStock -= dto.getCount();
        }
        stock.setCurrentStock(currentStock);
        stock.setExpectedStock(expectedStock);
        stockMapper.modifyCurrentAndExpectedStock(stock);
        stockDetailMapper.modifyStatusById(dto.getId(), dto.getStatus());
    }

    public void modifyAlertSetting(ReqAlertSettingDto dto) {
        stockMapper.modifyAlertSetting(dto.toEntity());
    }
}