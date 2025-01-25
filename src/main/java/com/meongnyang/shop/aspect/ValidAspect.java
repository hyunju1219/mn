package com.meongnyang.shop.aspect;

import com.meongnyang.shop.dto.request.admin.ReqModifyProductDto;
import com.meongnyang.shop.dto.request.admin.ReqOauth2SignupDto;
import com.meongnyang.shop.dto.request.auth.ReqUserSignupDto;
import com.meongnyang.shop.dto.request.admin.ReqRegisterProductDto;
import com.meongnyang.shop.exception.ValidException;
import com.meongnyang.shop.service.admin.AdminProductService;
import com.meongnyang.shop.service.auth.AuthService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
@Aspect
public class ValidAspect {

    @Autowired
    private AuthService userService;
    @Autowired
    private AdminProductService adminProductService;

    @Pointcut("@annotation(com.meongnyang.shop.aspect.annotation.ValidAop)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        BeanPropertyBindingResult bindingResult = null;

        for (Object arg : args) {
            if (arg instanceof BeanPropertyBindingResult) {
                bindingResult = (BeanPropertyBindingResult) arg;
                System.out.println(bindingResult.getFieldErrors());
                break;
            }
        }
        switch (joinPoint.getSignature().getName()) {
            case "signup":
                signupValid(args, bindingResult);
                break;
            case "oauth2Signup":
                oauth2SignupValid(args, bindingResult);
                break;
            case "registerProduct" :
                registerProductValid(args, bindingResult);
                break;
            case "modifyProduct":
                modifyProductValid(args, bindingResult);
                break;
        }

        if (bindingResult.hasErrors()) {
            throw new ValidException("유효성 검사 오류", bindingResult.getFieldErrors());
        }

        return joinPoint.proceed();
    }

    private void signupValid(Object[] args, BindingResult bindingResult) {
        for(Object arg : args) {
            if (arg instanceof ReqUserSignupDto) {
                ReqUserSignupDto dto = (ReqUserSignupDto) arg;
                FieldError fieldError = null;

                boolean isZipcodeBlank = dto.getZipcode() == null || dto.getZipcode().isBlank();
                boolean isAddressDefaultBlank = dto.getAddressDefault() == null || dto.getAddressDefault().isBlank();

                if (!dto.getPassword().equals(dto.getCheckPassword())) {
                    fieldError = new FieldError("checkPassword", "checkPassword", "비밀번호를 확인해주세요");
                    bindingResult.addError(fieldError);
                }
                if (!userService.isDuplicationUsername(dto.getUsername())) {
                    fieldError = new FieldError("username", "username", "중복된 아이디입니다.");
                    bindingResult.addError(fieldError);
                }

                if (isZipcodeBlank && !isAddressDefaultBlank) {
                    fieldError = new FieldError("address", "address", "주소정보를 확인하세요");
                    bindingResult.addError(fieldError);
                }
                if (isAddressDefaultBlank && !isZipcodeBlank) {
                    fieldError = new FieldError("address", "address", "주소정보를 확인하세요");
                    bindingResult.addError(fieldError);
                }
            }
        }
    }

    private void oauth2SignupValid(Object[] args, BindingResult bindingResult) {
        for(Object arg : args) {
            if (arg instanceof ReqOauth2SignupDto) {
                ReqOauth2SignupDto dto = (ReqOauth2SignupDto) arg;
                FieldError fieldError = null;
                if (!userService.isDuplicationUsername(dto.getUsername())) {
                    fieldError = new FieldError("username", "username", "중복된 아이디입니다.");
                    bindingResult.addError(fieldError);
                }
            }
        }
    }

    private void registerProductValid(Object[] args, BindingResult bindingResult) {
        for(Object arg : args) {
            if (arg instanceof ReqRegisterProductDto) {
                ReqRegisterProductDto dto = (ReqRegisterProductDto) arg;
                FieldError fieldError = null;
                if(!adminProductService.isPetGroupId(dto.getPetGroupId())) {
                    fieldError = new FieldError("petGroupId", "petGroupId", "존재하지 않는 카테고리입니다.");
                    bindingResult.addError(fieldError);
                }
                if(!adminProductService.isCategoryId(dto.getCategoryId())) {
                    fieldError = new FieldError("categoryId", "categoryId", "존재하지 않는 카테고리입니다.");
                    bindingResult.addError(fieldError);
                }
            }
        }
    }

    private void modifyProductValid(Object[] args, BindingResult bindingResult) {
        for(Object arg : args) {
            if (arg instanceof ReqRegisterProductDto) {
                ReqModifyProductDto dto = (ReqModifyProductDto) arg;
                FieldError fieldError = null;
                if(!adminProductService.isPetGroupId(dto.getPetGroupId())) {
                    fieldError = new FieldError("petGroupId", "petGroupId", "존재하지 않는 카테고리입니다.");
                    bindingResult.addError(fieldError);
                }
                if(!adminProductService.isCategoryId(dto.getCategoryId())) {
                    fieldError = new FieldError("categoryId", "categoryId", "존재하지 않는 카테고리입니다.");
                    bindingResult.addError(fieldError);
                }
            }
        }
    }
}
