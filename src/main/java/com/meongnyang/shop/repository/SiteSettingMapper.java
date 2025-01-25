package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.SiteSetting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SiteSettingMapper {

    int setSiteSetting(SiteSetting siteSetting);
    SiteSetting getSiteSetting();
    SiteSetting getLogoName();
    int getSiteSettingCount();
    int modifySiteSetting(SiteSetting siteSetting);

}
