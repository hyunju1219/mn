package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.Membership;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RespTokenUserInfoDto {
    private Long id;
    private String username;
    private String name;
    private String phone;
    private Membership membership;
    private Set<String> userRoles;

}
