package com.meongnyang.shop.dto.request.user;

import com.meongnyang.shop.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ReqUpdateUserDto {
    private Long userId;

    @NotBlank
    @Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 입력 가능합니다.")
    private String name;

    @NotBlank
    @Pattern(regexp = "^(010-\\d{4}-\\d{4})$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phone;

    private String zipcode;
    private String addressDefault;
    private String addressDetail;

    public User toEntity() {
        return User.builder()
                .name(name)
                .phone(phone)
                .build();
    }
}
