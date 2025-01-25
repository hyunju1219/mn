package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
    int save(Role role);
    Role findByRoleName(String roleName);
}
