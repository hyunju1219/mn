package com.meongnyang.shop.controller.admin;

import com.meongnyang.shop.service.admin.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardAll() {
        return ResponseEntity.ok().body(adminDashboardService.getDashboard());
    }
}
