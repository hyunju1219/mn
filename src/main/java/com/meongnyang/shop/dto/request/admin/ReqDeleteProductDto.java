package com.meongnyang.shop.dto.request.admin;

import lombok.Data;

import java.util.List;

@Data
public class ReqDeleteProductDto {
    private List<Long> productIds;
}
