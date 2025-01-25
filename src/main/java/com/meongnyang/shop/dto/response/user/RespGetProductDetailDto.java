package com.meongnyang.shop.dto.response.user;

import com.meongnyang.shop.entity.ImgUrl;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespGetProductDetailDto {
    private Long id;
    private String productName;
    private String petGroupName;
    private String categoryName;
    private String productPrice;
    private String productPriceDiscount;
    private String productDetail;
    private int recommendation;
    private int onSale;
    private Long currentStock;
    private List<ImgUrl> detailImgUrls;
    private List<String> imgNames;
}
