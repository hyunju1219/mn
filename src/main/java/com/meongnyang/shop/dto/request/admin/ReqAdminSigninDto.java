package com.meongnyang.shop.dto.request.admin;

import com.meongnyang.shop.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReqAdminSigninDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
