package com.meongnyang.shop.dto.request.user;

import lombok.Data;

@Data
public class ReqModifyCartItemDto {
    private Long cartId;
    private Long userId;
    private Long productCount;
}
