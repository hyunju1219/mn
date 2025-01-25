package com.meongnyang.shop.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespSiteSettingDto {
    private String siteName;
    private String siteAddress;
    private String sitePhone;
    private int defaultDeliverCost;
    private String imgName;
}
