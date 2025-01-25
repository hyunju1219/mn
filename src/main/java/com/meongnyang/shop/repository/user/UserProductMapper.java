package com.meongnyang.shop.repository.user;

import com.meongnyang.shop.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface UserProductMapper {
    List<Product> findProducts(Map<String, Object> params);
    int findProductCount(Map<String, Object> params);
    Product findProductById(Long id);
    List<Product> findProductsById(List<Long> ids);
    List<Product> findAllBySearch(Map<String, Object> params);
    int findAllBySearchCount(String search);
}
