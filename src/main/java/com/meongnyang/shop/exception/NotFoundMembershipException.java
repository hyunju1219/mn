package com.meongnyang.shop.exception;

public class NotFoundMembershipException extends RuntimeException {
    public NotFoundMembershipException(String message) {
        super(message);
    }
}
