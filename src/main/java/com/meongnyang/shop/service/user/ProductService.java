package com.meongnyang.shop.service.user;

import com.meongnyang.shop.dto.request.user.ReqGetCheckProductsDto;
import com.meongnyang.shop.dto.request.user.ReqProductAllDto;
import com.meongnyang.shop.dto.request.user.ReqProductCountDto;
import com.meongnyang.shop.dto.request.user.ReqSearchProductDto;
import com.meongnyang.shop.dto.response.admin.RespGetCategorysDto;
import com.meongnyang.shop.dto.response.user.RespCheckProductsDto;
import com.meongnyang.shop.dto.response.user.RespGetProductDetailDto;
import com.meongnyang.shop.dto.response.user.RespProductAllDto;
import com.meongnyang.shop.dto.response.user.RespProductListDto;
import com.meongnyang.shop.entity.ImgUrl;
import com.meongnyang.shop.entity.Product;
import com.meongnyang.shop.entity.Stock;
import com.meongnyang.shop.repository.ImgUrlMapper;
import com.meongnyang.shop.repository.StockMapper;
import com.meongnyang.shop.repository.user.UserProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final PetGroupService petGroupService;
    private final CategoryService categoryService;
    private final ImgUrlService imgUrlService;
    private final UserStockService stockService;
    private final UserProductMapper userProductMapper;

    private Map<String, Object> petGroupIdMap = Map.of(
            "all", 0,
            "dog", 1,
            "cat", 2,
            "recommend", 3
    );

    private Map<String, Object> createParmsMap(ReqProductAllDto dto) {
        return Map.of(
                "startIndex", (dto.getPage() - 1) * dto.getLimit(),
                "limit", dto.getLimit(),
                "petGroupId", petGroupIdMap.get(dto.getGroupName()),
                "categoryId", dto.getCategoryId()
        );
    }

    public RespProductAllDto getProductsAll(ReqProductAllDto dto) {
        List<Product> products = userProductMapper.findProducts(createParmsMap(dto));

        List<RespProductAllDto.ProductContent> productContentList = mapToProductContentList(products);

        return RespProductAllDto.builder()
                .productList(productContentList)
                .productListCount(productContentList.size())
                .build();
    }

    private List<RespProductAllDto.ProductContent> mapToProductContentList(List<Product> products) {
        return products.stream()
                .map(this::mapToProductContent)
                .collect(Collectors.toList());
    }

    private RespProductAllDto.ProductContent mapToProductContent(Product product) {
        String imgUrlName = imgUrlService.getImgUrlName(product.getId());
        return product.toDto(imgUrlName != null ? imgUrlName : "");
    }

    public int getProductsCount(ReqProductCountDto dto) {
        Map<String, Object> params = Map.of(
                "petGroupId", petGroupIdMap.get(dto.getGroupName()),
                "categoryId", dto.getCategoryId()
        );
        return userProductMapper.findProductCount(params);
    }

    public RespGetCategorysDto getCategorys() {
        return RespGetCategorysDto.builder()
                .petGroupList(petGroupService.getPetGroups())
                .categoryList(categoryService.getCategories())
                .build();
    }

    public RespGetProductDetailDto getProductDetail(Long productId) {
        Product product = userProductMapper.findProductById(productId);

        Stock stock = stockService.getStock(productId);

        List<String> imgNames = getImgUrlList(product);

        return product.toUserProductDetailDto(imgNames, stock.getCurrentStock());
    }

    private List<String> getImgUrlList(Product product) {
        return product.getImgUrls().stream()
                .map(ImgUrl::getImgName)
                .collect(Collectors.toList());
    }

    public RespCheckProductsDto getCheckProduct(ReqGetCheckProductsDto dto) {
        List<Product> productList = userProductMapper.findProductsById(dto.getProductIds());

        List<RespCheckProductsDto.CheckProduct> checkProductList = productList.stream()
                .map(product -> {
                    String imgName = imgUrlService.getImgUrlName(product.getId());

                    return RespCheckProductsDto.CheckProduct.builder()
                            .productId(product.getId())
                            .productName(product.getProductName())
                            .productPrice(product.getProductPrice())
                            .productPriceDiscount(product.getProductPriceDiscount())
                            .groupName(product.getPetGroup().getCategoryGroupName())
                            .categoryName(product.getCategory().getCategoryName())
                            .imgName(imgName != null ? imgName : "")
                            .build();
                })
                .collect(Collectors.toList());

        return RespCheckProductsDto.builder()
                .checkProducts(checkProductList)
                .productsCount(checkProductList.size())
                .build();
    }

    private RespProductListDto.SearchContent mapToSearchContent(Product product) {
        String imgUrlName = imgUrlService.getImgUrlName(product.getId());
        return product.toSearchContent(imgUrlName != null ? imgUrlName : "");
    }

    private List<RespProductListDto.SearchContent> mapToSearchProductList(List<Product> products) {
        return products.stream()
                .map(this::mapToSearchContent)
                .collect(Collectors.toList());
    }

    public RespProductListDto getProductSearch(ReqSearchProductDto dto) {
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();

        Map<String, Object> params = Map.of(
                "startIndex", startIndex,
                "limit", dto.getLimit(),
                "searchValue", dto.getSearch() == null ? "" : dto.getSearch()
        );

        List<Product> products = userProductMapper.findAllBySearch(params);

        List<RespProductListDto.SearchContent> searchList = mapToSearchProductList(products);

        return  RespProductListDto.builder()
                .products(searchList)
                .productCount(searchList.size())
                .build();
    }

    public int getProductSearchCount(String search) {
        return userProductMapper.findAllBySearchCount(search);
    }
}
