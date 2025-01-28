package com.meongnyang.shop.service.user;

import com.meongnyang.shop.entity.Payment;
import com.meongnyang.shop.repository.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentMapper paymentMapper;

    public List<Payment> getPayments() {
        return  paymentMapper.findAll();
    }

    public Payment getPaymentMethod(String paymentMethod) {
        return paymentMapper.findPaymentMethodByName(paymentMethod);
    }
}
