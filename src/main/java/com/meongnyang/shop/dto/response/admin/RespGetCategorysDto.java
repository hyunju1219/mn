package com.meongnyang.shop.dto.response.admin;

import com.meongnyang.shop.entity.Category;
import com.meongnyang.shop.entity.PetGroup;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RespGetCategorysDto {
    List<PetGroup> petGroupList;
    List<Category> categoryList;
}
