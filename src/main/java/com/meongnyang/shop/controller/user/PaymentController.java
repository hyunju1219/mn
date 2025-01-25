package com.meongnyang.shop.controller.user;

import com.meongnyang.shop.service.user.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/order/payment")
    public ResponseEntity<?> getPayments() {
        return ResponseEntity.ok().body(paymentService.getPayments());
    }
}
