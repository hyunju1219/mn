package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.Category;
import com.meongnyang.shop.entity.ImgUrl;
import com.meongnyang.shop.entity.PetGroup;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class RespProductDetailDto {
    private Long id;
    private String productName;
    private Long petGroupId;
    private Long categoryId;
    private PetGroup petGroup;
    private Category category;
    private Long productPrice;
    private Long productPriceDiscount;
    private String productDetail;
    private String productBrand;
    private String productModel;
    private String productMemo;
    private LocalDate productCreateDate;
    private LocalDate productUpdateDate;
    private int recommendation;
    private int onSale;
    private List<ImgUrl> imgUrls;
    private List<ImgUrl> productDetailImgUrls;
}
