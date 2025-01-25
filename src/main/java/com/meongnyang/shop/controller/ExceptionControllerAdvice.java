package com.meongnyang.shop.controller;

import com.meongnyang.shop.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ValidException.class)
    public ResponseEntity<?> validException(ValidException e) {
        return ResponseEntity.badRequest().body(e.getFieldErrors());
    }

    @ExceptionHandler(SignupException.class)
    public ResponseEntity<?> joinException(SignupException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationException(AuthenticationException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(RegisterException.class)
    public ResponseEntity<?> registerException(RegisterException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<?> deleteException(DeleteException e) {
        return ResponseEntity.badRequest().body(e.getMessage() );
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<?> notFoundUserException(NotFoundUserException e) {
        return ResponseEntity.badRequest().body(false);
    }

    @ExceptionHandler(NotFoundMembershipException.class)
    public ResponseEntity<?> notFoundMembershipException(NotFoundMembershipException e) {
        return ResponseEntity.badRequest().body(false);
    }

    @ExceptionHandler(AccessTokenException.class)
    public ResponseEntity<?> accessTokenException(AccessTokenException e) {
        return ResponseEntity.badRequest().body(false);
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<?> invalidUserException(InvalidUserException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(UpdateUserException.class)
    public ResponseEntity<?> updateUserException(UpdateUserException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
  
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> modifyfailedException(DataNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
