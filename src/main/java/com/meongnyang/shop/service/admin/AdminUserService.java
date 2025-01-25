package com.meongnyang.shop.service.admin;

import com.meongnyang.shop.dto.request.admin.ReqModifyMembershipLevelDto;
import com.meongnyang.shop.dto.request.admin.ReqSearchDto;
import com.meongnyang.shop.dto.response.admin.RespGetMembershipsDto;
import com.meongnyang.shop.dto.response.admin.RespGetUserDetailDto;
import com.meongnyang.shop.dto.response.admin.RespGetUsersDto;
import com.meongnyang.shop.entity.Order;
import com.meongnyang.shop.entity.Pet;
import com.meongnyang.shop.entity.User;
import com.meongnyang.shop.exception.NotFoundMembershipException;
import com.meongnyang.shop.exception.NotFoundUserException;
import com.meongnyang.shop.repository.MembershipMapper;
import com.meongnyang.shop.repository.OrderMapper;
import com.meongnyang.shop.repository.PetMapper;
import com.meongnyang.shop.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MembershipMapper membershipMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PetMapper petMapper;

    public RespGetUsersDto getUsers() {
        List<User> userList = userMapper.findAll().stream()
                .filter(user -> user.getUserRoles().stream()
                        .anyMatch(role -> role.getRole().getRoleName().equals("ROLE_USER")))
                .collect(Collectors.toList());

        System.out.println(userList.get(0));

        List<RespGetUsersDto.RespUserDto> respUserDtos = userList.stream().map(user -> {
            return RespGetUsersDto.RespUserDto.builder()
                    .id(user.getId())
                    .createDate(user.getCreateDate())
                    .username(user.getUsername())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .recentPurchaseDate(orderMapper.getRecentOrderDate(user.getId()))
                    .membershipName(user.getMembership().getMembershipLevelName())
                    .build();
        }).collect(Collectors.toList());

        return RespGetUsersDto.builder()
                .userRespList(respUserDtos)
                .userListCount(respUserDtos.size())
                .build();
    }

    public RespGetUsersDto getUsersByOption(ReqSearchDto dto) {
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();

        Map<String, Object> params = Map.of(
                "startIndex", startIndex,
                "limit", dto.getLimit(),
                "searchWord", dto.getSearch() == null ? "" : dto.getSearch(),
                "option", dto.getOption() == null || dto.getOption().isBlank() ? "all" : dto.getOption()
        );

        List<User> userList = userMapper.findUserByOption(params).stream()
                .filter(user -> user.getUserRoles().stream()
                        .anyMatch(role -> role.getRole().getRoleName().equals("ROLE_USER")))
                .collect(Collectors.toList());

        return RespGetUsersDto.builder()
                .userList(userList)
                .userListCount(userMapper.getCountByOption(params))
                .build();
    }

    public RespGetUserDetailDto getUserDetail(Long userId) {
        User user = userMapper.findUserDetailById(userId);
        List<RespGetUserDetailDto.RespUserDetailProductDto> userOrderList = orderMapper.getOrderDetailProductsById(userId);
        RespGetUserDetailDto.UserPurchaseData userPurchaseData = userMapper.findUserPurchaseDateById(userId);
        Pet pet = petMapper.findPetByUserId(userId);

        if (user == null) {
            throw new NotFoundUserException("사용자를 찾을 수 없습니다.");
        }

        return user.toDto(pet, userOrderList, userPurchaseData);
    }

    public void modifyUserMembership(ReqModifyMembershipLevelDto dto) {
        User user = userMapper.findUserById(dto.getUserId());
        if (user == null) {
            throw new NotFoundUserException("사용자를 찾을 수 없습니다.");
        }

        List<Long> membershipList = membershipMapper.findMembershipId();
        if (!membershipList.contains(dto.getMembershipId())) {
            throw new NotFoundMembershipException("존재하지 않는 등급입니다.");
        }
        userMapper.updateUserMembershipById(dto.getUserId(), dto.getMembershipId());
    }

    public RespGetMembershipsDto getMemberships() {
        return RespGetMembershipsDto.builder()
                .membershipList(membershipMapper.findMembershipAll())
                .build();
    }

    // 매일 자정에 최근 30일동안 구매한 금액이 500,000만원 이상이면 VIP 아니면 일반등급으로 업데이트
    @Scheduled(cron = "0 0 0 * * *")
    public void autoPurchaseConfirmation() {
        List<Order> orderList = orderMapper.getUserTotalAmount();
        for (Order order : orderList) {
            Long userId = order.getUserId();
            Long totalPrice = order.getTotalPrice();
            User user = userMapper.findUserMembershipLevel(userId);
            if (totalPrice >= 500000 && user.getMembershipLevelId() == 2) {
                userMapper.updateUserMembershipById(userId, 1L);
            }
            if (totalPrice < 500000 && user.getMembershipLevelId() == 1) {
                userMapper.updateUserMembershipById(userId, 2L);
            }
        }
        System.out.println("사용자 등급 업데이트 성공");
    }
}