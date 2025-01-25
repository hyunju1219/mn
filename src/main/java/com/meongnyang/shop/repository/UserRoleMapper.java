package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    int save(UserRole userRole);
}
