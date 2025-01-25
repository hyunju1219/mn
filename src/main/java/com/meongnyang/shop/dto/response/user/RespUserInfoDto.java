package com.meongnyang.shop.dto.response.user;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class RespUserInfoDto {
    private Long id;
    private String username;
    private String name;
    private String phone;
    private Long addressId;
    private String zipcode;
    private String addressDefault;
    private String addressDetail;
    private Long petId;
    private String petName;
    private String petAge;
    private String petType;
    private Set<String> roles;
}
