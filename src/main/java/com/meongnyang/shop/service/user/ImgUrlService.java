package com.meongnyang.shop.service.user;

import com.meongnyang.shop.repository.ImgUrlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImgUrlService {
    private final ImgUrlMapper imgUrlMapper;

    public String getImgUrlName(Long productId) {
        return imgUrlMapper.findImgNameByProductId(productId).getImgName();
    }
}
