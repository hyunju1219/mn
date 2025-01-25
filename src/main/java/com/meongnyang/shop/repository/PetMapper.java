package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PetMapper {
    int save(Pet pet);
    Pet findPetByUserId(Long userId);
}
