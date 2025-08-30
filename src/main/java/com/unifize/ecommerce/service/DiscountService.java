package com.unifize.ecommerce.service;

import com.unifize.ecommerce.exception.DiscountCalculationException;
import com.unifize.ecommerce.exception.DiscountValidationException;
import com.unifize.ecommerce.model.*;
import java.util.List;
import java.util.Optional;

public interface DiscountService {

    DiscountedPrice calculateCartDiscounts(
            List<CartItem> cartItems,
            CustomerProfile customer,
            Optional<PaymentInfo> paymentInfo,
            String voucherCode
    ) throws DiscountCalculationException;

    boolean validateDiscountCode(
            String code,
            List<CartItem> cartItems,
            CustomerProfile customer
    ) throws DiscountValidationException;
}