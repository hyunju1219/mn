package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class RespGetUsersDto {
    private List<User> userList;
    private List<RespUserDto> userRespList;
    private int userListCount;

    @Data
    @Builder
    public static class RespUserDto {
        private Long id;
        private LocalDate createDate;
        private String username;
        private String name;
        private String phone;
        private LocalDate recentPurchaseDate;
        private String membershipName;
    }
}
