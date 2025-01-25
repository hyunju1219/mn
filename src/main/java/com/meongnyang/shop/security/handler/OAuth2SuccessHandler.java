package com.meongnyang.shop.security.handler;

import com.meongnyang.shop.entity.User;
import com.meongnyang.shop.repository.UserMapper;
import com.meongnyang.shop.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = defaultOAuth2User.getAttributes();

        String oAuth2Name = attributes.get("id").toString();
        String provider = attributes.get("provider").toString();

        User user = userMapper.findUserByUsername(oAuth2Name);

        //oauth회원가입
        if (user == null) {
            response.sendRedirect("http://localhost:3000/user/signup/oauth2?oAuth2Name=" + oAuth2Name + "&provider=" + provider);
            return;
        }
        //oauth로그인
        String accessToken = jwtProvider.generateToken(user);
        response.sendRedirect("http://localhost:3000/user/signin/oauth2?accessToken=" + accessToken);
    }
}