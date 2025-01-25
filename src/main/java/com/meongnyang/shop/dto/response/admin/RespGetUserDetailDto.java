package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.Address;
import com.meongnyang.shop.entity.Membership;
import com.meongnyang.shop.entity.Pet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class RespGetUserDetailDto {
    private Long id;
    private String username;
    private String name;
    private String phone;
    private Membership membership;
    private String createDate;
    private UserPurchaseData userPurchaseData;
    private Address address;
    private Pet pet;

    private List<RespUserDetailProductDto> products;

    @Data
    @Builder
    public static class RespUserDetailProductDto {
        private Long productId;
        private String productName;
        private Long count;
        private Long productPrice;
        private Long totalPrice;
        private LocalDate recentlyPurchaseDate;
    }

    @Data
    @Builder
    public static class UserPurchaseData {
        private LocalDate initialPurchaseDate;
        private LocalDate recentlyPurchaseDate;
        private Long totalPrice;
    }
}
