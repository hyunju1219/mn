package com.meongnyang.shop.controller.user;

import com.meongnyang.shop.aspect.annotation.ValidAop;
import com.meongnyang.shop.dto.request.user.ReqUpdatePasswordDto;
import com.meongnyang.shop.dto.request.user.ReqUpdatePetDto;
import com.meongnyang.shop.dto.request.user.ReqUpdateUserDto;
import com.meongnyang.shop.entity.User;
import com.meongnyang.shop.repository.user.MyPageMapper;
import com.meongnyang.shop.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MyPageController {

    @Autowired
    private UserService userService;

    // 회원정보 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.getUserInfo(userId));
    }

    // 회원정보 수정
    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody ReqUpdateUserDto dto) {
        userService.updateUser(dto);
        return ResponseEntity.ok().body(true);
    }

    // 비밀번호 수정
    @ValidAop
    @PutMapping("/edit/password")
    public ResponseEntity<?> editPassword(@Valid @RequestBody ReqUpdatePasswordDto dto, BindingResult bindingResult) {
        userService.editPassword(dto);
        return ResponseEntity.ok().body(true);
    }

    // 반려동물정보 수정
    @PutMapping("/user/pet/{userId}")
    public ResponseEntity<?> modifyPet(@RequestBody ReqUpdatePetDto dto) {
        userService.modifyPet(dto);
        return ResponseEntity.ok().body(true);
    }

}
