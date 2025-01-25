package com.meongnyang.shop.dto.request.user;

import lombok.Data;

@Data
public class ReqPostCartDto {
    private Long userId;
    private Long productId;
    private Long productCount;
}
