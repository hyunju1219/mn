package com.meongnyang.shop.controller.user;

import com.meongnyang.shop.aspect.annotation.ValidUserAop;
import com.meongnyang.shop.dto.request.user.ReqGetOrderListDto;
import com.meongnyang.shop.dto.request.user.ReqModifyOrderDto;
import com.meongnyang.shop.dto.request.user.ReqPostOrderDto;
import com.meongnyang.shop.service.user.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    // 주문 등록
    @ValidUserAop
    @PostMapping("/order")
    public ResponseEntity<?> postProductsOrder(@RequestBody ReqPostOrderDto dto) {
        orderService.postProductsOrder(dto);
        return ResponseEntity.ok().body(true);
    }

    @ValidUserAop
    @GetMapping("/user/orderlist")
    public ResponseEntity<?> getOrderList(ReqGetOrderListDto dto) {
        return ResponseEntity.ok().body(orderService.getOrderList(dto));
    }

    @GetMapping("/user/orderlist/count")
    public ResponseEntity<?> getOrderListCount() {
        return ResponseEntity.ok().body(orderService.getOrderListCount());
    }
    // 주문상태 수정
    @ValidUserAop
    @PutMapping("/order/status")
    public ResponseEntity<?> modifyProductsOrder(@RequestBody ReqModifyOrderDto dto) {
        orderService.modifyProductsOrder(dto);
        return ResponseEntity.ok().body(true);
    }
}
