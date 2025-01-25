package com.meongnyang.shop.repository;

import com.meongnyang.shop.entity.ImgUrl;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgUrlMapper {
    int save(ImgUrl imgUrl);
    List<ImgUrl> findImgUrlByProductId(Long productId);
    int deleteImgUrlById(List<Long> idList);
    int deleteImgByNames(List<String> imgNameList);
    int deleteImgUrlAll();
    ImgUrl findImgNameByProductId(Long productId);
}