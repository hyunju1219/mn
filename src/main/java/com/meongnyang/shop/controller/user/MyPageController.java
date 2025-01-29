package com.meongnyang.shop.controller.user;

import com.meongnyang.shop.aspect.annotation.ValidAop;
import com.meongnyang.shop.aspect.annotation.ValidUserAop;
import com.meongnyang.shop.dto.request.user.ReqUpdatePasswordDto;
import com.meongnyang.shop.dto.request.user.ReqUpdatePetDto;
import com.meongnyang.shop.dto.request.user.ReqUpdateUserDto;
import com.meongnyang.shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MyPageController {
    private final UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.getUserInfo(userId));
    }

    @ValidUserAop
    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody ReqUpdateUserDto dto) {
        userService.updateUser(dto);
        return ResponseEntity.ok().body(true);
    }

    @ValidUserAop
    @ValidAop
    @PutMapping("/edit/password")
    public ResponseEntity<?> editPassword(@Valid @RequestBody ReqUpdatePasswordDto dto, BindingResult bindingResult) {
        userService.editPassword(dto);
        return ResponseEntity.ok().body(true);
    }

    @ValidUserAop
    @PutMapping("/user/pet/{userId}")
    public ResponseEntity<?> modifyPet(@RequestBody ReqUpdatePetDto dto) {
        System.out.println(dto);
        userService.modifyPet(dto);
        return ResponseEntity.ok().body(true);
    }

}
