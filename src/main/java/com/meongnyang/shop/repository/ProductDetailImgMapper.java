package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.ProductDetailImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDetailImgMapper {
    int save(List<ProductDetailImg> imgs);
    int deleteByImgNames(List<String> imgNames);
}