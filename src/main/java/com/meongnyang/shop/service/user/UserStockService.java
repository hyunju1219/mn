package com.meongnyang.shop.service.user;

import com.meongnyang.shop.dto.request.user.ReqPostOrderDto;
import com.meongnyang.shop.dto.response.user.RespCurrentStockDto;
import com.meongnyang.shop.entity.OrderDetail;
import com.meongnyang.shop.entity.Stock;
import com.meongnyang.shop.entity.StockDetail;
import com.meongnyang.shop.repository.StockDetailMapper;
import com.meongnyang.shop.repository.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserStockService {
    private final StockMapper stockMapper;
    private final StockDetailMapper stockDetailMapper;

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

    public void processModifyStock(List<OrderDetail> orderDetailList, String orderStatus) {
        for (int i = 0; i < orderDetailList.size(); i++) {
            modifyStockDetail(orderDetailList, i, orderStatus);

            modifyStockByCurrentCount(orderDetailList, i, orderStatus);
        }
    }

    public void processRegisterStock(ReqPostOrderDto.ProductEasy product, Long orderDetailId) {
        modifyStock(product);
        registerStockDetail(product, orderDetailId);
    }

    private void modifyStock(ReqPostOrderDto.ProductEasy product) {
        Map<String, Object> params = Map.of(
                "productId", product.getProductId(),
                "productCount", product.getProductCount()
        );
        stockMapper.modifyExpectedStockByProductId(params);
    }

    private void registerStockDetail(ReqPostOrderDto.ProductEasy product, Long orderDetailId) {
        stockDetailMapper.saveOrder(StockDetail.builder()
                .stockId(getStock(product.getProductId()))
                .status("배송중")
                .arrivalQuantity(product.getProductCount())
                .orderDetailId(orderDetailId)
                .build());
    }

    private Long getStock(Long productId) {
        return stockMapper.findStockByProductId(productId).getId();
    }

    private void modifyStockDetail(List<OrderDetail> orderDetailList, int i, String orderStatus) {
        stockDetailMapper.modifyStatusByOrderDetailId(StockDetail.builder()
                .orderDetailId(orderDetailList.get(i).getId())
                .status(orderStatus.equals("구매확정") ? "구매확정" : "취소")
                .build());
    }

    private void modifyStockByCurrentCount(List<OrderDetail> orderDetailList, int i, String orderStatus) {
        Map<String, Object> productDetail = Map.of(
                "productId", orderDetailList.get(i).getProductId(),
                "productCount", orderDetailList.get(i).getProductCount(),
                "orderStatus", orderStatus
        );
        stockMapper.modifyCurrentStockByProductId(productDetail);
    }


}
