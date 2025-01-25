package com.meongnyang.shop.service.user;

import com.meongnyang.shop.dto.response.user.RespGetPaymentDto;
import com.meongnyang.shop.entity.Payment;
import com.meongnyang.shop.repository.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;

    public List<Payment> getPayments() {
        return  paymentMapper.findAll();
    }
}
