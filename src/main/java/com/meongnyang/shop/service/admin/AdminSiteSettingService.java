package com.meongnyang.shop.service.admin;

import com.meongnyang.shop.dto.request.admin.ReqSiteSettingDto;
import com.meongnyang.shop.dto.response.admin.RespSiteSettingDto;
import com.meongnyang.shop.entity.SiteSetting;
import com.meongnyang.shop.exception.DataNotFoundException;
import com.meongnyang.shop.repository.SiteSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class AdminSiteSettingService {

    @Autowired
    private SiteSettingMapper siteSettingMapper;

    @Value("${file.path}")
    private String filePath;

    public RespSiteSettingDto getSiteSetting() {
        SiteSetting siteSetting = siteSettingMapper.getSiteSetting();
        if (siteSetting == null) {
            throw new DataNotFoundException("noData");
        }
        return siteSetting.toDto();
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean modifySiteSetting(ReqSiteSettingDto dto) throws IOException {
        int result = 0;
        SiteSetting siteSetting = dto.toEntity();
        if(siteSettingMapper.getSiteSettingCount() == 1) {
            // 등록된 사이트 정보가 있을때는 값 변경
            if(dto.getLogo() != null) {
                siteSetting.setImgPath(filePath);
                siteSetting.setImgName(dto.getLogo().getOriginalFilename());
            }
            result = siteSettingMapper.modifySiteSetting(siteSetting);
            deleteImgs(dto.getDeleteImgName());
        } else {
            // 등록된 사이트 정보가 없을때는 추가
            siteSetting.setImgPath(filePath);
            siteSetting.setImgName(dto.getLogo().getOriginalFilename());
            result = siteSettingMapper.setSiteSetting(siteSetting);
        }
        if (result == 0) {
            throw new RuntimeException();
        }
        if(dto.getLogo() != null) {
            registerImgs(dto.getLogo());
        }
        return true;
    }

    public String getLogoName() {
        SiteSetting siteSetting = siteSettingMapper.getLogoName();
        if (siteSetting == null) {
            throw new DataNotFoundException("등록된 로고가 없습니다.");
        }
        return siteSetting.getImgName();
    }

    private void registerImgs(MultipartFile img) throws IOException {
        String imgName = img.getOriginalFilename();
        File directory = new File(filePath);
        if(!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(filePath + imgName);
        img.transferTo(file);
    }

    private void deleteImgs(String imgName) {
        File file = new File(filePath + imgName);
        file.delete();
    }
}
