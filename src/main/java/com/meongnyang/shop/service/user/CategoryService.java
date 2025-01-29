package com.meongnyang.shop.service.user;


import com.meongnyang.shop.entity.Category;
import com.meongnyang.shop.repository.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryMapper categoryMapper;

    public List<Category> getCategories() {
        return categoryMapper.findCategory();
    }
}
