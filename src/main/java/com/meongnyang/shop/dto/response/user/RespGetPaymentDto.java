package com.meongnyang.shop.dto.response.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespGetPaymentDto {
    private Long id;
    private String paymentName;
}
