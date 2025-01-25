package com.meongnyang.shop.service.auth;

import com.meongnyang.shop.dto.request.admin.ReqOauth2SignupDto;
import com.meongnyang.shop.entity.Role;
import com.meongnyang.shop.entity.User;
import com.meongnyang.shop.entity.UserRole;
import com.meongnyang.shop.exception.SignupException;
import com.meongnyang.shop.repository.RoleMapper;
import com.meongnyang.shop.repository.UserMapper;
import com.meongnyang.shop.repository.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class OAuth2Service implements OAuth2UserService {

    @Autowired
    private DefaultOAuth2UserService defaultOAuth2UserService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> aAuth2Attributes = new HashMap<>();
        aAuth2Attributes.put("provider", userRequest.getClientRegistration().getClientName());

        switch (userRequest.getClientRegistration().getClientName()) {
            case "Google":
                aAuth2Attributes.put("id", attributes.get("sub").toString());
                break;
            case "Naver":
                aAuth2Attributes.put("id", ((Map<String, Object>) attributes.get("response")).get("id"));
                break;
            case "Kakao":
                aAuth2Attributes.put("id", attributes.get("id").toString());
                break;
        }

        return new DefaultOAuth2User(new HashSet<>(), aAuth2Attributes, "id");
    }

    @Transactional(rollbackFor = SignupException.class)
    public void oauth2Signup (ReqOauth2SignupDto dto) {
        try {
            User user = dto.toEntity();
            userMapper.save(user);
            Role role = roleMapper.findByRoleName("ROLE_USER");
            if(role == null) {
                role = Role.builder()
                        .roleName("ROLE_USER")
                        .build();
                roleMapper.save(role);
            }
            UserRole userRole = UserRole.builder()
                    .userId(user.getId())
                    .roleId(role.getId())
                    .build();
            userRoleMapper.save(userRole);
            user.setUserRoles(Set.of(userRole));
        } catch (Exception e) {
            throw new SignupException(e.getMessage());
        }
    }
}
