package com.meongnyang.shop.controller.admin;

import com.meongnyang.shop.dto.request.admin.ReqSearchDto;
import com.meongnyang.shop.service.admin.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

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
