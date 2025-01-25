package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    int save(Product product);
    List<Product> findProducts();
    List<Product> findProductsByOption(Map<String, Object> params);
    int getCountByOption(Map<String, Object> params);
    int modifyProduct(Product product);
    int deleteProductAll();
    int deleteProductById(List<Long> idList);
    Product findProductById(Long id);
    Product findProductDetailById(Long id);

    Long getProductPriceById(Long productId);

}
