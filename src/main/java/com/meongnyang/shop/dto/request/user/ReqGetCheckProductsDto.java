package com.meongnyang.shop.dto.request.user;

import lombok.Data;

import java.util.List;

@Data
public class ReqGetCheckProductsDto {
    private List<Long> productIds;
}
