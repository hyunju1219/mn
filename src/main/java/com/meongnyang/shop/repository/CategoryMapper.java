package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    int save(Category category);
    Category findCategoryByName(String name);
    List<Category> findCategory();
}
