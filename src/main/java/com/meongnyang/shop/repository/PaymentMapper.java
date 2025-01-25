package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {
    int save(Payment payment);
    Payment findPaymentByName(String name);
    Payment findPaymentMethodByName(String name);
    List<Payment> findAll();
}
