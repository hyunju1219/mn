package com.meongnyang.shop.service.user;

import com.meongnyang.shop.dto.request.user.*;
import com.meongnyang.shop.dto.response.user.RespGetCartDto;
import com.meongnyang.shop.entity.*;
import com.meongnyang.shop.exception.DeleteException;
import com.meongnyang.shop.repository.ImgUrlMapper;
import com.meongnyang.shop.repository.user.UserCartMapper;
import com.meongnyang.shop.security.principal.PrincipalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserCartService {

    @Autowired
    private ImgUrlMapper imgUrlMapper;

    @Autowired
    private UserCartMapper userCartMapper;

    private void validateCurrentUserId(Long userId) {
        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principalUser.getId() != userId) {
            throw new SecurityException("사용자 ID가 일치하지 않습니다");
        }
    }

    public List<Cart> getCartIdAll(Long userId) {
        return userCartMapper.findCartIdByUserId(userId);
    }

    public void changeCartItemCount(ReqModifyCartItemDto dto) {
        validateCurrentUserId(dto.getUserId());
        Cart cart = Cart.builder()
                .id(dto.getCartId())
                .productCount(dto.getProductCount())
                .build();
        userCartMapper.modifyCartItemCount(cart);
    }

    public void addCartItem(ReqPostCartDto dto) {
        validateCurrentUserId(dto.getUserId());

        Cart currentCartItem = findCurrentCart(dto);

        if (currentCartItem != null) {
            updateCartItem(currentCartItem, dto.getProductCount());
            return;
        }

        addNewCartItem(dto);
    }

    private Cart findCurrentCart(ReqPostCartDto dto) {
        return userCartMapper.findCartByUserProductId(
                dto.getUserId(),
                dto.getProductId()
        );
    }

    private void updateCartItem(Cart currentCartItem, Long newProductCount) {
        currentCartItem.setProductCount(currentCartItem.getProductCount() + newProductCount);
        userCartMapper.updateCart(currentCartItem);
    }

    private void addNewCartItem(ReqPostCartDto dto) {
        userCartMapper.saveCart(
                Cart.builder()
                        .userId(dto.getUserId())
                        .productId(dto.getProductId())
                        .productCount(dto.getProductCount())
                        .build()
        );
    }

    public RespGetCartDto getCartAll(ReqGetCartAllDto dto) {
        validateCurrentUserId(dto.getUserId());

        List<Cart> cartList = userCartMapper.getCart(createParamsMap(dto));

        List<RespGetCartDto.CartContent> cartContentList = mapToCartContetntList(cartList);

        return RespGetCartDto.builder()
                .cartList(cartContentList)
                .cartListCount(cartContentList.size())
                .build();
    }

    private Map<String, Object> createParamsMap(ReqGetCartAllDto dto) {
        return Map.of(
                "startIndex", (dto.getPage() - 1) * dto.getLimit(),
                "limit", dto.getLimit(),
                "userId", dto.getUserId()
        );
    }

    private List<RespGetCartDto.CartContent> mapToCartContetntList(List<Cart> cartList) {
        return cartList.stream()
                .map(this::mapToCartContent)
                .collect(Collectors.toList());
    }

    private RespGetCartDto.CartContent mapToCartContent(Cart cart) {
        String imgUrlName = imgUrlMapper.findImgNameByProductId(cart.getProductId()).getImgName();
        return cart.toDto(imgUrlName != null ? imgUrlName : "");
    }

    public int getCartAllCount(ReqGetCartAllCountDto dto) {
        validateCurrentUserId(dto.getUserId());

        return userCartMapper.findCartCount(dto.getUserId());
    }

    @Transactional(rollbackFor = DeleteException.class)
    public void deleteCartItem(ReqDeleteCartDto dto) {
        validateCurrentUserId(dto.getUserId());

        validCartIds(dto.getUserId(),dto.getCartIds());

        userCartMapper.deleteCartById(dto.getCartIds());

    }

    private void validCartIds(Long userId, List<Long> deleteCartIds) {
        if(deleteCartIds.isEmpty()) {
            throw new DeleteException("존재하지 않는 데이터입니다.");
        }

        List<Long> validCartIds = userCartMapper.findCartIdsByUserId(userId, deleteCartIds);

        if (deleteCartIds.size() != validCartIds.size()) {
            throw new DeleteException("존재하지 않는 데이터가 포함되어 있습니다.");
        }
    }
}
