package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.PetGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetGroupMapper {
    int save(PetGroup petGroup);
    PetGroup findPetGroupByName(String name);
    List<PetGroup> findPetGroup();
}