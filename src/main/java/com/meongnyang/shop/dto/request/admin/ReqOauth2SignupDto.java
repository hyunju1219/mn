package com.meongnyang.shop.dto.request.admin;

import com.meongnyang.shop.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ReqOauth2SignupDto {
    @NotBlank
    private String username;
    @NotBlank
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 입력 가능합니다.")
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String provider;

    public User toEntity() {
        return User.builder()
                .username(username)
                .name(name)
                .phone(phone)
                .provider(provider)
                .build();
    }
}
