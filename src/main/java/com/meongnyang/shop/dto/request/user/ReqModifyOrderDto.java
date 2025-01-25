package com.meongnyang.shop.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqModifyOrderDto {
    private Long id;
    private Long userId;
    private String orderStatus;
}
