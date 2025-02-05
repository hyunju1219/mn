package com.meongnyang.shop.config;

import com.meongnyang.shop.security.filter.JwtAccessTokenFilter;
import com.meongnyang.shop.security.handler.AuthenticationHandler;
import com.meongnyang.shop.security.handler.OAuth2SuccessHandler;
import com.meongnyang.shop.service.auth.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAccessTokenFilter jwtAccessTokenFilter;
    private final OAuth2Service oAuth2Service;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final AuthenticationHandler authenticationHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable();
        http.httpBasic().disable();
        http.cors();
        http.csrf().disable();

        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(authenticationHandler);

        http.authorizeRequests()
                .antMatchers("/hc", "/env")
                .permitAll()
                .antMatchers("/auth/**",
//                        "/order/**",
//                        "/orders/**",
                        "/logo",
                        "/images/**",
                        "/product/**",
                        "/swagger-ui.html/**/**",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/api-docs/**")
                .permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/products/**",
                        "/product/**"
                )
                .permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated();

        http.oauth2Login()
                .successHandler(oAuth2SuccessHandler)
                .userInfoEndpoint()
                .userService(oAuth2Service);

        http.addFilterBefore(jwtAccessTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //oauth, 로그인 필터, 예외처리 핸들러
    }
}
