package com.meongnyang.shop.dto.request.user;

import lombok.Data;

@Data
public class ReqUpdatePetDto {
    private Long userId;
    private String petName;
    private Long petAge;
    private String petType;
}
