package com.meongnyang.shop.repository.user;

import com.meongnyang.shop.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserCartMapper {
    int saveCart(Cart cart);
    List<Cart> getCart(Map<String, Object> params);
    int findCartCount(Long userId);
    Long deleteCartAll (List<Long> userIds);
    int deleteCartById(List<Long> idList);
    Cart findCartByUserProductId(Long userId,Long productId);
    int updateCart(Cart cart);
    int modifyCartItemCount(Cart cart);
    List<Long> findCartIdsByUserId(@Param("userId") Long userId, @Param("cartIds") List<Long> cartIds);
    List<Cart> findCartIdByUserId(Long userId);
}
