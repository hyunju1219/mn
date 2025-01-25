package com.meongnyang.shop.controller.admin;

import com.meongnyang.shop.aspect.annotation.ValidAop;
import com.meongnyang.shop.dto.request.admin.ReqDeleteProductDto;
import com.meongnyang.shop.dto.request.admin.ReqModifyProductDto;
import com.meongnyang.shop.dto.request.admin.ReqRegisterProductDto;
import com.meongnyang.shop.dto.request.admin.ReqSearchDto;
import com.meongnyang.shop.service.admin.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;

    @ValidAop
    @PostMapping("/product")
    public ResponseEntity<?> registerProduct(@Valid @ModelAttribute ReqRegisterProductDto dto, BindingResult bindingResult) throws IOException {
        adminProductService.registerProduct(dto);
        return ResponseEntity.ok().body(true);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProductsAll() {
        return ResponseEntity.ok().body(adminProductService.getProductsAll());
    }

    @GetMapping("/products/search")
    public ResponseEntity<?> getProductsByOption(ReqSearchDto dto) {
        System.out.println(dto);
        return ResponseEntity.ok().body(adminProductService.getProductsByOption(dto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductDetail(@PathVariable Long productId) {
        return ResponseEntity.ok().body(adminProductService.getProductDetail(productId));
    }

    @ValidAop
    @PutMapping("/product/{productId}")
    public ResponseEntity<?> modifyProduct(@Valid @ModelAttribute ReqModifyProductDto dto, BindingResult bindingResult) throws IOException {
        adminProductService.modifyProduct(dto);
        return ResponseEntity.ok().body(true);
    }

    @DeleteMapping("/products")
    public ResponseEntity<?> deleteProducts(ReqDeleteProductDto dto) {
        adminProductService.deleteProductById(dto);
        return ResponseEntity.ok().body(true);
    }

    @DeleteMapping("/products/all")
    public ResponseEntity<?> deleteProductsAll() {
        adminProductService.deleteProductsAll();
        return ResponseEntity.ok().body(true);
    }
}