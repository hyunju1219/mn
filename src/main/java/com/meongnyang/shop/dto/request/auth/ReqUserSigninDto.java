package com.meongnyang.shop.dto.request.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqUserSigninDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
