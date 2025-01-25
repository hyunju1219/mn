package com.meongnyang.shop.service.auth;

import com.meongnyang.shop.dto.request.auth.ReqAccessDto;
import com.meongnyang.shop.dto.response.admin.RespTokenUserInfoDto;
import com.meongnyang.shop.entity.User;
import com.meongnyang.shop.exception.AccessTokenException;
import com.meongnyang.shop.repository.UserMapper;
import com.meongnyang.shop.security.jwt.JwtProvider;
import com.meongnyang.shop.security.principal.PrincipalUser;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProvider jwtProvider;

    public Boolean access(ReqAccessDto dto) {
        try {
            String token = jwtProvider.removeBearer(dto.getAccessToken());
            Claims claims = jwtProvider.getClaims(token);
            Long userId = ((Integer) claims.get("userId")).longValue();
            User user = userMapper.findUserById(userId);
            if(user == null) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new AccessTokenException();
        }
        return true;
    }

    public RespTokenUserInfoDto getUserMe() {
        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userMapper.findUserById(principalUser.getId());

        return RespTokenUserInfoDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .phone(user.getPhone())
                .membership(user.getMembership())
                .userRoles(user.getUserRoles().stream().map(
                        userRole -> userRole.getRole().getRoleName()
                ).collect(Collectors.toSet()))
                .build();
    }
}
