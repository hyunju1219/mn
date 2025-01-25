package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {
    int save(Address address);
}
