package com.meongnyang.shop.service.user;

import com.meongnyang.shop.dto.request.user.ReqGetOrderListDto;

import com.meongnyang.shop.dto.request.user.ReqModifyOrderDto;
import com.meongnyang.shop.dto.request.user.ReqPostOrderDto;
import com.meongnyang.shop.dto.response.user.RespGetOrderListDto;
import com.meongnyang.shop.entity.*;
import com.meongnyang.shop.exception.RegisterException;
import com.meongnyang.shop.repository.PaymentMapper;
import com.meongnyang.shop.repository.ProductMapper;
import com.meongnyang.shop.repository.StockDetailMapper;
import com.meongnyang.shop.repository.StockMapper;
import com.meongnyang.shop.repository.user.UserOrderDetailMapper;
import com.meongnyang.shop.repository.user.UserOrderMapper;
import com.meongnyang.shop.security.principal.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@EnableScheduling
@Service
public class OrderService {

    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private UserOrderDetailMapper userOrderDetailMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StockDetailMapper stockDetailMapper;

    private void getCurrentUser(Long userId) {
        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principalUser.getId() != userId) {
            throw new SecurityException("사용자 ID가 일치하지 않습니다");
        }
    }

    @Transactional(rollbackFor = RegisterException.class)
    public void postProductsOrder(ReqPostOrderDto dto) {
       getCurrentUser(dto.getUserId());
        try {
            Payment payment = paymentMapper.findPaymentMethodByName(dto.getPaymentMethod());
            Order order = dto.toEntity(payment.getId());
            //주문 추가
            userOrderMapper.save(order);

            for (ReqPostOrderDto.ProductEasy product : dto.getProducts()) {
                //주문 상품 추가
                OrderDetail orderDetail = OrderDetail.builder()
                        .orderId(order.getId())
                        .productId(product.getProductId())
                        .productPrice(product.getProductPrice())
                        .productCount(Long.valueOf(product.getProductCount()))
                        .build();
                System.out.println(orderDetail);
                userOrderDetailMapper.save(orderDetail);
                System.out.println(orderDetail.getId());
                //가재고 빼기, 재고 상세 추가(배송중으로)
                Map<String, Object> params = Map.of(
                        "productId", product.getProductId(),
                        "productCount", product.getProductCount()
                );
                //가재고에서 수량만큼 빼기
                stockMapper.modifyExpectedStockByProductId(params);
                //재고 아이디 가져오기
                Stock stock = stockMapper.findStockByProductId(product.getProductId());
                //재고 상세에 배송중 상태로 추가
                stockDetailMapper.saveOrder(StockDetail.builder()
                                .stockId(stock.getId())
                                .status("배송중")
                                .arrivalQuantity(product.getProductCount())
                                .orderDetailId(orderDetail.getId())
                                .build());
            }
        } catch (Exception e) {
            throw new RegisterException(e.getMessage());
        }
    }

    // 재고확정
    @Transactional(rollbackFor = RegisterException.class)
    public void modifyProductsOrder(ReqModifyOrderDto dto) {
        getCurrentUser(dto.getUserId());

        try {
            Map<String, Object> params = Map.of(
                    "userId", dto.getUserId(),
                    "id", dto.getId(),
                    "orderStatus", dto.getOrderStatus()
            );
            // 주문 테이블의 orderStatus를 환불완료 및 구매확정으로 변경
            userOrderMapper.modifyOrder(params);

            //주문아이디로 주문상세의 상품 리스트들 가져옴(productId와 productCount필요)
            List<OrderDetail> orderDetailList = userOrderDetailMapper.findOrderProductIdByOrderId(dto.getId());

            for (int i = 0; i < orderDetailList.size(); i++) {
                Map<String, Object> productDetail = Map.of(
                        "productId", orderDetailList.get(i).getProductId(),
                        "productCount", orderDetailList.get(i).getProductCount(),
                        "orderStatus", dto.getOrderStatus()
                );
                // 환불완료시 재고 상세에 상태를 "취소"로 변경 => 가재고를 상품 개수만큼 더함
                // 구매확정시 재고 상세에 상태를 "구매확정"으로 변경 => 현재재고에서 상품 개수만큼 뺌
                stockDetailMapper.modifyStatusByOrderDetailId(StockDetail.builder()
                        .orderDetailId(orderDetailList.get(i).getId())
                        .status(dto.getOrderStatus().equals("구매확정") ? "구매확정" : "취소")
                        .build());
                stockMapper.modifyCurrentStockByProductId(productDetail);
            }
        } catch (Exception e) {
            throw new RegisterException(e.getMessage());
        }
    }

    public RespGetOrderListDto getOrderList (ReqGetOrderListDto dto){
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();
        Map<String, Object> params = Map.of(
                "userId", dto.getUserId(),
                "startIndex", startIndex,
                "limit", dto.getLimit(),
                "paymentSelect", dto.getPaymentSelect(),
                "startDate", dto.getStartDate(),
                "endDate", dto.getEndDate()
        );

        List<Order> orderList = userOrderMapper.findAllOrders(params);
        System.out.println("orderList" + orderList);
        List<RespGetOrderListDto.OrderList> orderListDtos = new ArrayList<>();

        for (Order order : orderList) {
            List<OrderDetail> orderDetailList = userOrderDetailMapper.findOrderDetailByOrderId(order.getId());
            RespGetOrderListDto.OrderList orderList1 = order.toDto();
            List<RespGetOrderListDto.OrderDetail> orderDetails = new ArrayList<>();

            for (OrderDetail orderDetail : orderDetailList) {
                orderDetails.add(orderDetail.toDto());
            }
            orderList1.setOrderDetailList(orderDetails);
            orderListDtos.add(orderList1);
        }

        return RespGetOrderListDto.builder()
                .orderList(orderListDtos)
                .orderListCount(orderListDtos.size())
                .build();
    }

    public int getOrderListCount() {
        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userOrderMapper.findOrderCount(principalUser.getId());
    }

// 매일 자정에 들어온 주문 중 구매 후 3일이 지난 경우 자동 확정
    @Scheduled(cron = "0 0 0 * * *")
    public void autoPurchaseConfirmation() {
        List<Order> orderList = userOrderMapper.findArrivingStatusOrderList();
        for (Order order : orderList) {
            ReqModifyOrderDto dto = new ReqModifyOrderDto(order.getId(), order.getUserId(), "구매확정");
            Map<String, Object> params = Map.of(
                    "userId", dto.getUserId(),
                    "id", dto.getId(),
                    "orderStatus", dto.getOrderStatus()
            );
            // 주문 테이블의 orderStatus를 환불완료 및 구매확정으로 변경
            userOrderMapper.modifyOrder(params);
            List<OrderDetail> orderDetailList = userOrderDetailMapper.findOrderProductIdByOrderId(dto.getId());
            for (int i = 0; i < orderDetailList.size(); i++) {
                Map<String, Object> productDetail = Map.of(
                        "productId", orderDetailList.get(i).getProductId(),
                        "productCount", orderDetailList.get(i).getProductCount(),
                        "orderStatus", dto.getOrderStatus()
                );
                // 환불완료시 재고 상세에 상태를 "취소"로 변경 => 가재고를 상품 개수만큼 더함
                // 구매확정시 재고 상세에 상태를 "구매확정"으로 변경 => 현재재고에서 상품 개수만큼 뺌
                stockDetailMapper.modifyStatusByOrderDetailId(StockDetail.builder()
                        .orderDetailId(orderDetailList.get(i).getId())
                        .status(dto.getOrderStatus().equals("구매확정") ? "구매확정" : "취소")
                        .build());
                stockMapper.modifyCurrentStockByProductId(productDetail);
            }
        }
        System.out.println("구매확정 업데이트 성공");
    }
}