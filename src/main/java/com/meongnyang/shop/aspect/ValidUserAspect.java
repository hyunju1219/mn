package com.meongnyang.shop.aspect;

import com.meongnyang.shop.dto.request.user.*;

import com.meongnyang.shop.security.principal.PrincipalUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidUserAspect {

    @Pointcut("@annotation(com.meongnyang.shop.aspect.annotation.ValidUserAop)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            Long userId = extractUserIdFromDto(arg);

            isValidUser(userId);

            break;
        }
        return joinPoint.proceed(args);
    }

    private void isValidUser(Long userId) {
        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principalUser.getId() != userId) {
            throw new AuthenticationServiceException("사용자 ID가 일치하지 않습니다.");
        }
    }

    private Long extractUserIdFromDto(Object arg) {
        if (arg instanceof ReqUpdateUserDto) {
            return ((ReqUpdateUserDto) arg).getId();
        } else if (arg instanceof ReqUpdatePetDto) {
            return ((ReqUpdatePetDto) arg).getId();
        } else if (arg instanceof ReqModifyCartItemDto) {
            return ((ReqModifyCartItemDto) arg).getUserId();
        } else if (arg instanceof ReqPostCartDto) {
            return ((ReqPostCartDto) arg).getUserId();
        } else if (arg instanceof ReqGetCartAllDto) {
            return ((ReqGetCartAllDto) arg).getUserId();
        } else if (arg instanceof ReqGetCartAllCountDto) {
            return ((ReqGetCartAllCountDto) arg).getUserId();
        } else if (arg instanceof ReqDeleteCartDto) {
            return ((ReqDeleteCartDto) arg).getUserId();
        } else if (arg instanceof ReqPostOrderDto) {
            return ((ReqPostOrderDto) arg).getUserId();
        } else if (arg instanceof ReqModifyOrderDto) {
            return ((ReqModifyOrderDto) arg).getUserId();
        } else if (arg instanceof ReqGetOrderListDto) {
            return ((ReqGetOrderListDto) arg).getUserId();
        }

        return null;
    }
}
