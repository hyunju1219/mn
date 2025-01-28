package com.meongnyang.shop.controller;

import com.meongnyang.shop.dto.request.auth.ReqAccessDto;
import com.meongnyang.shop.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final TokenService tokenService;

    @GetMapping("/user/me")
    public ResponseEntity<?> getUserMe() {
        return ResponseEntity.ok().body(tokenService.getUserMe());
    }

    @GetMapping("/auth/access")
    public ResponseEntity<?> getAccess(ReqAccessDto dto) {
        return ResponseEntity.ok().body(tokenService.access(dto));
    }
}
