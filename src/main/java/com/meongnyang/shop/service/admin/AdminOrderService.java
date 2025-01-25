package com.meongnyang.shop.service.admin;

import com.meongnyang.shop.dto.request.admin.ReqSearchDto;
import com.meongnyang.shop.dto.response.admin.RespGetOrdersDto;
import com.meongnyang.shop.dto.response.admin.RespOrderDetailDto;
import com.meongnyang.shop.entity.ImgUrl;
import com.meongnyang.shop.entity.Order;
import com.meongnyang.shop.exception.DeleteException;
import com.meongnyang.shop.repository.ImgUrlMapper;
import com.meongnyang.shop.repository.OrderDetailMapper;
import com.meongnyang.shop.repository.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ImgUrlMapper imgUrlMapper;

    public RespGetOrdersDto getOrders() {
        List<Order> orderList = orderMapper.findOrderAll();
        return RespGetOrdersDto.builder()
                .orderList(orderList)
                .orderListCount(orderList.size())
                .build();
    }

    public RespGetOrdersDto getOrdersByOption(ReqSearchDto dto) {
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();

        Map<String, Object> params = new java.util.HashMap<>(Map.of(
                "startIndex", startIndex,
                "limit", dto.getLimit(),
                "searchWord", dto.getSearch() == null ? "" : dto.getSearch(),
                "option", dto.getOption() == null || dto.getOption().isBlank() ? "all" : dto.getOption()
        ));

        if (dto.getOption() != null && dto.getOption().equals("date")) {
            params.put("startDate", dto.getStartDate());
            params.put("endDate", dto.getEndDate());
        }

        List<Order> orderList = orderMapper.getOrderByOption(params);

        return RespGetOrdersDto.builder()
                .orderList(orderList)
                .orderListCount(orderMapper.getOrderCountByOption(params))
                .productCount(orderMapper.getProductCountByOption(params))
                .build();
    }

    public RespOrderDetailDto getOrderDetail(Long id) {
        Order order = orderMapper.getOrderDetail(id);

        List<RespOrderDetailDto.RespOrderProductsDto> respOrderProductsDtos
                = order.getOrderDetails().stream().map(product -> {
                    return RespOrderDetailDto.RespOrderProductsDto.builder()
                            .id(product.getProductId())
                            .productName(product.getProduct().getProductName())
                            .productPrice(product.getProduct().getProductPrice())
                            .productCount(product.getProductCount())
                            .imgName(getProductImgName(product.getProductId()))
                            .build();
        }).collect(Collectors.toList());

        RespOrderDetailDto dto = RespOrderDetailDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .totalPrice(order.getTotalPrice())
                .orderItemCount(order.getOrderItemCount())
                .orderStatus(order.getOrderStatus())
                .orderName(order.getOrderName())
                .zipcode(order.getZipcode())
                .addressDefault(order.getAddressDefault())
                .addressDetail(order.getAddressDetail())
                .phone(order.getPhone())
                .request(order.getRequest())
                .paymentId(order.getPayment().getId())
                .paymentName(order.getPayment().getPaymentName())
                .products(respOrderProductsDtos)
                .build();
        return dto;
    }

    @Transactional(rollbackFor = DeleteException.class)
    public void deleteOrder(Long id) {
        try {
            orderMapper.deleteOrderById(id);
            orderDetailMapper.deleteOrderDetailByOrderId(id);
        } catch (Exception e) {
            throw new DeleteException("삭제 실패");
        }
    }

    public void deleteOrderAll() {
        orderMapper.deleteOrderAll();
    }

    private String getProductImgName(Long productId) {
        List<ImgUrl> imgUrls = imgUrlMapper.findImgUrlByProductId(productId);

        if (imgUrls != null && !imgUrls.isEmpty()) {
            return imgUrls.get(0).getImgName();
        }
        return "";
    }
}
