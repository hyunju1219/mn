package com.meongnyang.shop.service.admin;

import com.meongnyang.shop.dto.request.admin.ReqDeleteProductDto;
import com.meongnyang.shop.dto.request.admin.ReqModifyProductDto;
import com.meongnyang.shop.dto.request.admin.ReqRegisterProductDto;
import com.meongnyang.shop.dto.request.admin.ReqSearchDto;
import com.meongnyang.shop.dto.response.admin.RespGetProductsAllDto;
import com.meongnyang.shop.dto.response.admin.RespProductDetailDto;
import com.meongnyang.shop.entity.*;
import com.meongnyang.shop.exception.DeleteException;
import com.meongnyang.shop.exception.RegisterException;
import com.meongnyang.shop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AdminProductService {
    @Value("${file.path}")
    private String filePath;
    private final ProductMapper productMapper;
    private final StockMapper stockMapper;
    private final ImgUrlMapper imgUrlMapper;
    private final PetGroupMapper petGroupMapper;
    private final CategoryMapper categoryMapper;
    private final ProductDetailImgMapper productDetailImgMapper;

    @Transactional(rollbackFor = Exception.class)
    public void registerProduct(ReqRegisterProductDto dto) throws IOException {
        Product product = dto.toEntity();

        productMapper.save(product);
        stockMapper.save(Stock.builder().productId(product.getId()).build());

        List<MultipartFile> imgs = dto.getProductImage();
        if(imgs != null && !imgs.get(0).isEmpty()) {
            for(MultipartFile img : imgs) {
                registerProductImg(img);
                ImgUrl imgUrl = ImgUrl.builder()
                        .productId(product.getId())
                        .imgPath(filePath)
                        .imgName(img.getOriginalFilename())
                        .build();
                imgUrlMapper.save(imgUrl);
            }
        }
        List<MultipartFile> detailImgs = dto.getProductDetailImg();
        List<ProductDetailImg> productDetailImgs = new ArrayList<>();
        if(detailImgs != null && !detailImgs.get(0).isEmpty()) {
            for (MultipartFile img : detailImgs) {
                registerProductImg(img);
                ProductDetailImg productDetailImg = ProductDetailImg.builder()
                        .productId(product.getId())
                        .imgPath(filePath)
                        .imgName(img.getOriginalFilename())
                        .build();
                productDetailImgs.add(productDetailImg);
            }
            productDetailImgMapper.save(productDetailImgs);
        }
    }

    public RespGetProductsAllDto getProductsAll() {
        List<Product> productList = productMapper.findProducts();
        return RespGetProductsAllDto.builder()
                .productListCount(productList.size())
                .productList(productList)
                .build();
    }

    public RespGetProductsAllDto getProductsByOption(ReqSearchDto dto) {
        Long startIndex = (dto.getPage() - 1) * dto.getLimit();

        Map<String, Object> params = new java.util.HashMap<>(Map.of(
                "startIndex", startIndex,
                "limit", dto.getLimit(),
                "searchWord", dto.getSearch() == null ? "" : dto.getSearch(),
                "option", dto.getOption() == null || dto.getOption().isBlank() ? "all" : dto.getOption()
        ));

        List<Product> productList = productMapper.findProductsByOption(params);

        return RespGetProductsAllDto.builder()
                .productList(productList)
                .productListCount(productMapper.getCountByOption(params))
                .build();
    }

    public RespProductDetailDto getProductDetail(Long id) {
        Product product = productMapper.findProductDetailById(id);
        if(product == null) {
            throw new RegisterException("존재하지 않는 상품입니다.");
        }
        return product.toProductDetailDto(stockMapper.findStockByProductId(product.getId()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modifyProduct(ReqModifyProductDto dto) throws IOException {
        // 이미지 수정 기능 구현중
        Product product = null;
        product = productMapper.findProductById(dto.getId());

        if(product == null) {
            throw new RegisterException("존재하지 않는 상품입니다.");
        }
        //상품테이블 수정
        product = dto.toEntity();
        productMapper.modifyProduct(product);

        //상품 이미지 삭제
        List<String> deleteImgList = dto.getDeleteImgList();
        if (deleteImgList != null) {
            imgUrlMapper.deleteImgByNames(deleteImgList);
            deleteProductImgByImgNames(deleteImgList);
        }

        //이미지 추가
        List<MultipartFile> imgs = dto.getProductImage();
        if(imgs != null) {
            for(MultipartFile img : imgs) {
                registerProductImg(img);
                ImgUrl imgUrl = ImgUrl.builder()
                        .productId(product.getId())
                        .imgPath(filePath)
                        .imgName(img.getOriginalFilename())
                        .build();
                imgUrlMapper.save(imgUrl);
            }
        }

        //상세이미지 삭제
        List<String> deleteDetailImgList = dto.getDeleteProductDetailImgList();
        if (deleteDetailImgList != null) {
            deleteProductImgByImgNames(deleteDetailImgList);
            productDetailImgMapper.deleteByImgNames(deleteDetailImgList);
        }

        //상세이미지 추가
        List<MultipartFile> detailImgs = dto.getProductDetailImage();
        List<ProductDetailImg> productDetailImgs = new ArrayList<>();
        if(detailImgs != null && !detailImgs.get(0).isEmpty()) {
            for (MultipartFile img : detailImgs) {
                registerProductImg(img);
                ProductDetailImg productDetailImg = ProductDetailImg.builder()
                        .productId(product.getId())
                        .imgPath(filePath)
                        .imgName(img.getOriginalFilename())
                        .build();
                productDetailImgs.add(productDetailImg);
            }
            productDetailImgMapper.save(productDetailImgs);
        }
    }

    @Transactional(rollbackFor = DeleteException.class)
    public void deleteProductsAll() {
        try {
            productMapper.deleteProductAll();
            imgUrlMapper.deleteImgUrlAll();
            stockMapper.deleteStockAll();
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = DeleteException.class)
    public void deleteProductById(ReqDeleteProductDto dto) {
        try {
            List<Long> deleteProductIds = dto.getProductIds();
            //상품테이블에서 삭제
            productMapper.deleteProductById(deleteProductIds);

            for(Long productId : deleteProductIds) {
                //이미지 테이블에서 삭제
                List<ImgUrl> imgUrls = imgUrlMapper.findImgUrlByProductId(productId);
                if (!imgUrls.isEmpty()) {
                    deleteImgUrl(imgUrls);
                }
                //재고테이블에서 삭제
                stockMapper.deleteStockByProductId(productId);
            }
        } catch (Exception e) {
            throw new DeleteException(e.getMessage());
        }
    }

    //그룹아이디가 존재하면 true
    public boolean isPetGroupId(Long id) {
        for(PetGroup petGroup : petGroupMapper.findPetGroup()) {
            if(petGroup.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Boolean isCategoryId(Long id) {
        for(Category category : categoryMapper.findCategory()) {
            if(category.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void registerProductImg(MultipartFile img) throws IOException {
        String imgName = img.getOriginalFilename();
        File directory = new File(filePath);
        if(!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(filePath + imgName);
        img.transferTo(file);
    }

    public void deleteImgUrl(List<ImgUrl> imgUrls) {
        List<Long> deleteImgUrlIds = new ArrayList<>();
        for(ImgUrl imgUrl : imgUrls) {
            File file = new File(filePath + imgUrl.getImgName());
            file.delete();
            deleteImgUrlIds.add(imgUrl.getId());
        }
        imgUrlMapper.deleteImgUrlById(deleteImgUrlIds);
    }

    public void deleteProductImgByImgNames(List<String> fileNameList) {
        if(fileNameList == null || fileNameList.isEmpty()) {
            return;
        }

        // 서버컴퓨터 이미지 삭제
        for(String filename : fileNameList) {
            File file = new File(filePath + filename);
            file.delete();
        }
    }
}
