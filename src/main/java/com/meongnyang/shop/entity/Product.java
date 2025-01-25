package com.meongnyang.shop.entity;

import com.meongnyang.shop.dto.response.admin.RespProductDetailDto;
import com.meongnyang.shop.dto.response.user.RespGetProductDetailDto;
import com.meongnyang.shop.dto.response.user.RespProductAllDto;
import com.meongnyang.shop.dto.response.user.RespProductListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String productName;
    private Long petGroupId;
    private Long categoryId;
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
    private PetGroup petGroup;
    private Category category;
    private Stock stock;
    private StockDetail stockDetail;

    public RespProductDetailDto toProductDetailDto(Stock stock) {
        return RespProductDetailDto.builder()
                .id(id)
                .productName(productName)
                .petGroupId(petGroupId)
                .categoryId(categoryId)
                .petGroup(petGroup)
                .category(category)
                .productPrice(productPrice)
                .productPriceDiscount(productPriceDiscount)
                .productDetail(productDetail)
                .productBrand(productBrand)
                .productModel(productModel)
                .productMemo(productMemo)
                .productCreateDate(getProductCreateDate())
                .productUpdateDate(getProductUpdateDate())
                .recommendation(recommendation)
                .onSale(onSale)
                .imgUrls(imgUrls)
                .productDetailImgUrls(productDetailImgUrls)
                .build();
    }

    public RespProductAllDto.ProductContent toDto(String imgName) {
        return RespProductAllDto.ProductContent.builder()
                .productId(id)
                .productName(productName)
                .petGroupName(petGroup.getCategoryGroupName())
                .categoryName(category.getCategoryName())
                .productPrice(productPrice)
                .productPriceDiscount(productPriceDiscount)
                .productDetail(productDetail)
                .productBrand(productBrand)
                .productModel(productModel)
                .imgName(imgName)
                .build();
    }

    public RespGetProductDetailDto toUserProductDetailDto(List<String> imgNames, Long currentStock) {
        return RespGetProductDetailDto.builder()
                .id(id)
                .productName(productName)
                .petGroupName(petGroup.getCategoryGroupName())
                .categoryName(category.getCategoryName())
                .productDetail(productDetail)
                .productPrice(productPrice.toString())
                .productPriceDiscount(String.valueOf(productPriceDiscount))
                .recommendation(recommendation)
                .onSale(onSale)
                .currentStock(currentStock)
                .detailImgUrls(productDetailImgUrls)
                .imgNames(imgNames)
                .build();
    }

    public RespProductListDto.SearchContent toSearchContent(String imgName) {
        return RespProductListDto.SearchContent.builder()
                .productId(id)
                .productName(productName)
                .petGroupName(petGroup.getCategoryGroupName())
                .categoryName(category.getCategoryName())
                .productPrice(productPrice)
                .productPriceDiscount(productPriceDiscount)
                .productDetail(productDetail)
                .productBrand(productBrand)
                .productModel(productModel)
                .imgName(imgName)
                .build();
    }
}
