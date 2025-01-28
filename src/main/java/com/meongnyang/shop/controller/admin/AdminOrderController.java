package com.meongnyang.shop.controller.admin;

import com.meongnyang.shop.dto.request.admin.ReqSearchDto;
import com.meongnyang.shop.service.admin.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminOrderController {
    private final AdminOrderService adminOrderService;

    @GetMapping("/orders")
    public ResponseEntity<?> getOrdersAll() {
        return ResponseEntity.ok().body(adminOrderService.getOrders());
    }

    @GetMapping("/orders/search")
    public ResponseEntity<?> getOrdersByOption(ReqSearchDto dto) {
        return ResponseEntity.ok().body(adminOrderService.getOrdersByOption(dto));
    }

    @GetMapping("/orders/detail/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(adminOrderService.getOrderDetail(id));
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrders(@PathVariable Long id) {
        adminOrderService.deleteOrder(id);
        return ResponseEntity.ok().body(true);
    }

    @DeleteMapping("/orders/all")
    public ResponseEntity<?> deleteOrdersAll() {
        adminOrderService.deleteOrderAll();
        return ResponseEntity.ok().body(null);
    }
}
