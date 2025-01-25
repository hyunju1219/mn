package com.meongnyang.shop.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespGetTokenDto {
    private String accessToken;
}
