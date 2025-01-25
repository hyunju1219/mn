package com.meongnyang.shop.dto.request.admin;

import com.meongnyang.shop.entity.SiteSetting;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReqSiteSettingDto {
    private String siteName;
    private String siteAddress;
    private String sitePhone;
    private String deleteImgName;
    private int defaultDeliverCost;
    private MultipartFile logo;

    public SiteSetting toEntity() {
        return SiteSetting.builder()
                .siteName(siteName)
                .siteAddress(siteAddress)
                .sitePhone(sitePhone)
                .defaultDeliverCost(defaultDeliverCost)
                .build();
    }
}
