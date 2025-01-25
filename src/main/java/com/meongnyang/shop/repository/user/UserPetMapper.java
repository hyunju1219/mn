package com.meongnyang.shop.repository.user;

import com.meongnyang.shop.entity.Address;
import com.meongnyang.shop.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPetMapper {
    int savePet(Pet pet);
    Pet findPetByUserId(Long userId);
    Long UpdatePetByUserId(Pet pet);
}
