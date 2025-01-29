package com.meongnyang.shop.service.auth;

import com.meongnyang.shop.dto.request.admin.ReqAdminSigninDto;
import com.meongnyang.shop.dto.request.auth.ReqUserSigninDto;
import com.meongnyang.shop.dto.request.auth.ReqUserSignupDto;
import com.meongnyang.shop.dto.response.admin.RespAdminSigninDto;
import com.meongnyang.shop.dto.response.auth.RespGetTokenDto;
import com.meongnyang.shop.entity.Role;
import com.meongnyang.shop.entity.User;
import com.meongnyang.shop.entity.UserRole;
import com.meongnyang.shop.exception.SignupException;
import com.meongnyang.shop.repository.*;
import com.meongnyang.shop.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AdminUserMapper adminUserMapper;
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final AddressMapper addressMapper;
    private final PetMapper petMapper;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public RespAdminSigninDto adminSignin(ReqAdminSigninDto dto) {
        User user = adminUserMapper.findUserByUsername(dto.getUsername());
        if(user == null) {
            throw new UsernameNotFoundException("관리자 정보를 확인하세요");
        }
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("관리자 정보를 확인하세요");
        }
        return RespAdminSigninDto.builder()
                .token(jwtProvider.generateToken(user))
                .build();
    }

    @Transactional(rollbackFor = SignupException.class)
    public void signup(ReqUserSignupDto dto) {
        try {
            User user = dto.toEntityByUser(passwordEncoder);
            adminUserMapper.save(user);
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
            //주소가 들어있다면
            if(!dto.getZipcode().isBlank() && !dto.getAddressDefault().isBlank()) {
                addressMapper.save(dto.toEntityByAddress(user.getId()));
            }
            //반려동물 이름이 있다면
            if (dto.getPetName() != null && !dto.getPetName().isBlank()) {
                petMapper.save(dto.toEntityByPet(user.getId()));
            }
        } catch (Exception e) {
            throw new SignupException(e.getMessage());
        }
    }
    //유저가 중복되면 false
    public Boolean isDuplicationUsername(String username) {
        User user = adminUserMapper.findUserByUsername(username);
        if (user == null)  {
            return true;
        }
        return false;
    }

    public RespGetTokenDto signin(ReqUserSigninDto dto) {
        User user = adminUserMapper.findUserByUsername(dto.getUsername());
        if(user == null) {
            throw new UsernameNotFoundException("사용자 정보를 확인하세요");
        }
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("사용자 정보를 확인하세요");
        }
        return generateToken(user);
    }

    public RespGetTokenDto generateToken(User user) {
        return RespGetTokenDto.builder()
                .accessToken(jwtProvider.generateToken(user))
                .build();
    }
}
