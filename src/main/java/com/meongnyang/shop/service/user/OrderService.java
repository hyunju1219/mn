package com.meongnyang.shop.service.user;

import com.meongnyang.shop.dto.request.user.ReqGetOrderListDto;

import com.meongnyang.shop.dto.request.user.ReqModifyOrderDto;
import com.meongnyang.shop.dto.request.user.ReqPostOrderDto;
import com.meongnyang.shop.dto.response.user.RespGetOrderListDto;
import com.meongnyang.shop.entity.*;
import com.meongnyang.shop.exception.RegisterException;
import com.meongnyang.shop.repository.*;
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
import java.util.stream.Collectors;


//@EnableScheduling
@Service
public class OrderService {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserStockService userStockService;
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private UserOrderDetailMapper userOrderDetailMapper;

    //주문을 저장하는 부분(주문 -> 기본 주문, 상세 주문, 가재고, 가재고 상세)
    @Transactional(rollbackFor = RegisterException.class)
    public void postProductsOrder(ReqPostOrderDto dto) {
        try {
            Payment payment = paymentService.getPaymentMethod(dto.getPaymentMethod());

            Long orderId = registerOrder(dto, payment.getId());

            for (ReqPostOrderDto.ProductEasy product : dto.getProducts()) {
                Long orderDetailId = registerOrderDetail(product, orderId);

                userStockService.processRegisterStock(product, orderDetailId);
            }
        } catch (Exception e) {
            throw new RegisterException(e.getMessage());
        }
    }

    //재고확정(주문 테이블의 상태를 변경하고 재고 차감)
    @Transactional(rollbackFor = RegisterException.class)
    public void modifyProductsOrder(ReqModifyOrderDto dto) { //modifyOrderStatus
        try {
            modifyOrder(dto);
            //주문아이디로 주문상세의 상품 리스트들 가져옴(productId와 productCount필요)
            List<OrderDetail> orderDetailList = getOrderDetailByOrderId(dto.getId());

            userStockService.processModifyStock(orderDetailList, dto.getOrderStatus());

        } catch (Exception e) {
            throw new RegisterException(e.getMessage());
        }
    }

    //주문/배송 내역 조회
    public RespGetOrderListDto getOrderList (ReqGetOrderListDto dto){
        Map<String, Object> params = createParamsMap(dto);

        List<Order> orders = userOrderMapper.findAllOrders(params);

        List<RespGetOrderListDto.OrderList> orderListDtos = orders.stream().
                map(this::convertToOrderListDto)
                .collect(Collectors.toList());

        return RespGetOrderListDto.builder()
                .orderList(orderListDtos)
                .orderListCount(orderListDtos.size())
                .build();
    }

    private RespGetOrderListDto.OrderList convertToOrderListDto(Order order) {
        // 주문 상세 목록 조회 및 변환
        List<OrderDetail> orderDetails = userOrderDetailMapper.findOrderDetailByOrderId(order.getId());
        List<RespGetOrderListDto.OrderDetail> orderDetailDtos = orderDetails.stream()
                .map(OrderDetail::toDto)
                .collect(Collectors.toList());

        // OrderList DTO 생성
        RespGetOrderListDto.OrderList orderListDto = order.toDto();
        orderListDto.setOrderDetailList(orderDetailDtos);
        return orderListDto;
    }

    private Map<String, Object> createParamsMap(ReqGetOrderListDto dto) {
        return Map.of(
                "userId", dto.getUserId(),
                "startIndex", (dto.getPage() - 1) * dto.getLimit(),
                "limit", dto.getLimit(),
                "paymentSelect", dto.getPaymentSelect(),
                "startDate", dto.getStartDate(),
                "endDate", dto.getEndDate()
        );
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
            modifyOrder(dto);
            List<OrderDetail> orderDetailList = getOrderDetailByOrderId(dto.getId());
            userStockService.processModifyStock(orderDetailList, dto.getOrderStatus());
        }
    }

    private Long registerOrder(ReqPostOrderDto dto, int paymentId) {
        Order order = dto.toEntity(paymentId);
        userOrderMapper.save(order);
        return order.getId();
    }

    private Long registerOrderDetail(ReqPostOrderDto.ProductEasy product, Long orderId) {
        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(orderId)
                .productId(product.getProductId())
                .productPrice(product.getProductPrice())
                .productCount(Long.valueOf(product.getProductCount()))
                .build();
        userOrderDetailMapper.save(orderDetail);

        return orderDetail.getId();
    }

    private void modifyOrder(ReqModifyOrderDto dto) {
        Map<String, Object> params = Map.of(
                "userId", dto.getUserId(),
                "id", dto.getId(),
                "orderStatus", dto.getOrderStatus()
        );
        userOrderMapper.modifyOrder(params);
    }

    private List<OrderDetail> getOrderDetailByOrderId(Long orderId) {
        return userOrderDetailMapper.findOrderProductIdByOrderId(orderId);
    }
}