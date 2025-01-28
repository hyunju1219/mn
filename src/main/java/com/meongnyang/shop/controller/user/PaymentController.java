package com.meongnyang.shop.controller.user;

import com.meongnyang.shop.service.user.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/order/payment")
    public ResponseEntity<?> getPayments() {
        return ResponseEntity.ok().body(paymentService.getPayments());
    }
}
