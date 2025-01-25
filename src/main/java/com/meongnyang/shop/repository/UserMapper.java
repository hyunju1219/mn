package com.meongnyang.shop.repository;

import com.meongnyang.shop.dto.response.admin.RespDashboardDto;
import com.meongnyang.shop.dto.response.admin.RespGetUserDetailDto;
import com.meongnyang.shop.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    int save(User user);
    User findUserByUsername(String username);
    User findUserById(Long id);
    User findUserDetailById(Long id);
    RespGetUserDetailDto.UserPurchaseData findUserPurchaseDateById(Long id);
    RespDashboardDto getDashboardCustomer();
    List<User> findAll();
    List<User> findUserByOption(Map<String, Object> params);
    User findUserMembershipLevel(Long id);
    int getCountByOption(Map<String, Object> params);
    int updateUserMembershipById(@Param("id")Long id, @Param("membershipId")Long membershipId);
}
