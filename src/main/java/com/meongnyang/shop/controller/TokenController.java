package com.meongnyang.shop.controller;

import com.meongnyang.shop.dto.request.auth.ReqAccessDto;
import com.meongnyang.shop.service.auth.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/user/me")
    public ResponseEntity<?> getUserMe() {
        return ResponseEntity.ok().body(tokenService.getUserMe());
    }

    @GetMapping("/auth/access")
    public ResponseEntity<?> getAccess(ReqAccessDto dto) {
        return ResponseEntity.ok().body(tokenService.access(dto));
    }
}
