package com.meongnyang.shop.entity;

import com.meongnyang.shop.dto.response.admin.RespSiteSettingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteSetting {
    private Long id;
    private String siteName;
    private String siteAddress;
    private String sitePhone;
    private int defaultDeliverCost;
    private String imgPath;
    private String imgName;

    public RespSiteSettingDto toDto() {
        return RespSiteSettingDto.builder()
                .siteName(siteName)
                .siteAddress(siteAddress)
                .sitePhone(sitePhone)
                .defaultDeliverCost(defaultDeliverCost)
                .imgName(imgName)
                .build();
    }
}
