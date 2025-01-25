package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespGetProductsAllDto {
    private List<Product> productList;
    private int productListCount;
}
