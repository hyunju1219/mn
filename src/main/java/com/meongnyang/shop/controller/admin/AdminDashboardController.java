package com.meongnyang.shop.controller.admin;

import com.meongnyang.shop.service.admin.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminDashboardController {
    private final AdminDashboardService adminDashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardAll() {
        return ResponseEntity.ok().body(adminDashboardService.getDashboard());
    }
}
