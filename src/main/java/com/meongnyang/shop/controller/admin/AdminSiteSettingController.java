package com.meongnyang.shop.controller.admin;

import com.meongnyang.shop.dto.request.admin.ReqSiteSettingDto;
import com.meongnyang.shop.service.admin.AdminSiteSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AdminSiteSettingController {
    private final AdminSiteSettingService adminSiteSettingService;

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