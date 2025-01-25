package com.meongnyang.shop.controller.admin;

import com.meongnyang.shop.dto.request.admin.ReqStatisticsDateDto;
import com.meongnyang.shop.service.admin.AdminStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminStatisticsController {

    @Autowired
    private AdminStatisticsService adminStatisticsService;

    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics(ReqStatisticsDateDto dto) {
        return ResponseEntity.ok().body(adminStatisticsService.getStatisticsInfo(dto));
    }
}