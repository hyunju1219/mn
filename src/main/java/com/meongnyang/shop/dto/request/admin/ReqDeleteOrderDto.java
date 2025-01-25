package com.meongnyang.shop.dto.request.admin;

import lombok.Data;

import java.util.List;

@Data
public class ReqDeleteOrderDto {
    private List<Long> orderIds;
}
