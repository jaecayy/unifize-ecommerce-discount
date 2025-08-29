package com.unifize.ecommerce.exception;


public class DiscountValidationException extends RuntimeException {
    public DiscountValidationException(String message) {
        super(message);
    }

    public DiscountValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}