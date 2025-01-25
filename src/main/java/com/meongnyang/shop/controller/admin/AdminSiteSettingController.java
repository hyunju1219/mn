package com.meongnyang.shop.controller.admin;

import com.meongnyang.shop.dto.request.admin.ReqSiteSettingDto;
import com.meongnyang.shop.service.admin.AdminSiteSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AdminSiteSettingController {

    @Autowired
    private AdminSiteSettingService adminSiteSettingService;

    @GetMapping("/admin/setting")
    public ResponseEntity<?> getSiteSetting() {
        return ResponseEntity.ok().body(adminSiteSettingService.getSiteSetting());
    }

    @GetMapping("/logo")
    public ResponseEntity<?> getLogo() {
        return ResponseEntity.ok().body(adminSiteSettingService.getLogoName());
    }

    @PutMapping("/admin/setting")
    public ResponseEntity<?> modifySiteSetting(@ModelAttribute ReqSiteSettingDto dto) throws IOException {
        return ResponseEntity.ok().body(adminSiteSettingService.modifySiteSetting(dto));
    }
}