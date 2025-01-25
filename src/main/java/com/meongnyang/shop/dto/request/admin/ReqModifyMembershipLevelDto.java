package com.meongnyang.shop.dto.request.admin;

import lombok.Data;

@Data
public class ReqModifyMembershipLevelDto {
    private Long userId;
    private Long membershipId;
}