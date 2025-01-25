package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.Membership;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MembershipMapper {
    int save(Membership membership);
    Membership findMembershipByName(String name);
    List<Long> findMembershipId();
    List<Membership> findMembershipAll();
}
