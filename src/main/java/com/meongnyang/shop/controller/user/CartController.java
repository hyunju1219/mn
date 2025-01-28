package com.meongnyang.shop.controller.user;

import com.meongnyang.shop.aspect.annotation.ValidUserAop;
import com.meongnyang.shop.dto.request.user.*;
import com.meongnyang.shop.service.user.UserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    private UserCartService userCartService;

    @ValidUserAop
    @PostMapping("/user/cart")
    public ResponseEntity<?> saveProductCart(@RequestBody ReqPostCartDto dto) {
        userCartService.addCartItem(dto);
        return ResponseEntity.ok().body(true);
    }

    @ValidUserAop
    @PutMapping("/user/{cartId}/count")
    public ResponseEntity<?> modifyCartItemCount(@RequestBody ReqModifyCartItemDto dto) {
        userCartService.changeCartItemCount(dto);
        return ResponseEntity.ok().body(true);
    }

    @ValidUserAop
    @GetMapping("/user/cart")
    public ResponseEntity<?> getCartAll(ReqGetCartAllDto dto) {
        return ResponseEntity.ok().body(userCartService.getCartAll(dto));
    }

    @GetMapping("/user/cartId")
    public ResponseEntity<?> getCartIdAll(@RequestParam Long userId) {
        return ResponseEntity.ok().body(userCartService.getCartIdAll(userId));
    }

    @ValidUserAop
    @GetMapping("/user/cart/count")
    public ResponseEntity<?> getCartAllCount(ReqGetCartAllCountDto dto) {
        return ResponseEntity.ok().body(userCartService.getCartAllCount(dto));
    }

    @ValidUserAop
    @DeleteMapping("/user/cart")
    public ResponseEntity<?> deleteCart(ReqDeleteCartDto dto) {
        userCartService.deleteCartItem(dto);
        return ResponseEntity.ok().body(true);
    }
}
