package com.meongnyang.shop.dto.request.admin;

import com.meongnyang.shop.entity.Product;
import com.meongnyang.shop.entity.Stock;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.List;

@Data
public class ReqModifyProductDto {
    //상품 테이블
    @NotNull
    private Long id;
    @NotBlank
    private String productName;
    @NotNull
    private Long petGroupId;
    @NotNull
    private Long categoryId;
    @NotNull
    private Long productPrice;
    private Long productPriceDiscount;
    private String productDetail;
    private String productBrand;
    private String productModel;
    private String productMemo;
    private int recommendation;
    private int onSale;
    private List<MultipartFile> productImage;
    private List<String> deleteImgList;
    private List<MultipartFile> productDetailImage;
    private List<String> deleteProductDetailImgList;

    public Product toEntity() {
        return Product.builder()
                .id(id)
                .productName(productName)
                .petGroupId(petGroupId)
                .categoryId(categoryId)
                .productPrice(productPrice)
                .productPriceDiscount(productPriceDiscount)
                .productDetail(productDetail)
                .productBrand(productBrand)
                .productModel(productModel)
                .productMemo(productMemo)
                .recommendation(recommendation)
                .onSale(onSale)
                .build();
    }
}