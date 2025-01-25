package com.meongnyang.shop.controller.admin;

import com.meongnyang.shop.dto.request.ReqModifyStockDetailDto;
import com.meongnyang.shop.dto.request.admin.ReqAddStockDetailDto;
import com.meongnyang.shop.dto.request.admin.ReqAlertSettingDto;
import com.meongnyang.shop.dto.request.admin.ReqModifyStockListDto;
import com.meongnyang.shop.dto.request.admin.ReqStockSearchOptionDto;
import com.meongnyang.shop.service.admin.AdminStockDetailService;
import com.meongnyang.shop.service.admin.AdminStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminStockController {

    @Autowired
    private AdminStockService adminStockService;

    @Autowired
    private AdminStockDetailService adminStockDetailService;

    @PostMapping("/products/stock/detail")
    public ResponseEntity<?> addStockDetail(@RequestBody ReqAddStockDetailDto dto) {
        return ResponseEntity.ok().body(adminStockDetailService.save(dto));
    }

    @GetMapping("/products/stock")
    public ResponseEntity<?> getProductsStocks() {
        return ResponseEntity.ok().body(adminStockService.getStocks());
    }

    @GetMapping("/products/stock/search")
    public ResponseEntity<?> getProductsStockByOption(ReqStockSearchOptionDto dto) {
        return ResponseEntity.ok().body(adminStockService.getProductsStockByOption(dto));
    }

    @GetMapping("/products/stock/detail/{id}")
    public ResponseEntity<?> getProductStockDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(adminStockService.getStockDetail(id));
    }

    @PutMapping("/products/stock")
    public ResponseEntity<?> modifyProductsStock(@RequestBody ReqModifyStockListDto dto) {
        adminStockService.modifyStock(dto);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/products/stock/status")
    public ResponseEntity<?> modifyStockAndStatus(@RequestBody ReqModifyStockDetailDto dto) {
        adminStockService.modifyStockAndStatus(dto);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/products/stock/alert")
    public ResponseEntity<?> modifyAlertSettings(@RequestBody ReqAlertSettingDto dto) {
        adminStockService.modifyAlertSetting(dto);
        return ResponseEntity.ok().body(null);
    }
}