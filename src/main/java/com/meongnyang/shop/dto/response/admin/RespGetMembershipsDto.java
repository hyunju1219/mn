package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.Membership;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespGetMembershipsDto {
    private List<Membership> membershipList;
}
