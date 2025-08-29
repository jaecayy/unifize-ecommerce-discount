package com.unifize.ecommerce.service.impl;

import com.unifize.ecommerce.exception.DiscountCalculationException;
import com.unifize.ecommerce.model.*;
import com.unifize.ecommerce.service.DiscountService;
import com.unifize.ecommerce.strategyRules.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final List<DiscountStrategyRule> discountRules;

    public DiscountServiceImpl() {
        this.discountRules = List.of(
                new BrandDiscountStrategyRule(),
                new CategoryDiscountStrategyRule(),
                new VoucherDiscountStrategyRule("SUPER29"),
                new BankDiscountStrategyRule()
        );
    }

    @Override
    public DiscountedPrice calculateCartDiscounts(List<CartItem> cartItems,
                                                  CustomerProfile customer,
                                                  Optional<PaymentInfo> paymentInfo) {
        try {
            BigDecimal originalTotal = BigDecimal.ZERO;
            BigDecimal finalTotal = BigDecimal.ZERO;
            Map<String, BigDecimal> appliedDiscounts = new LinkedHashMap<>();

            for (CartItem item : cartItems) {
                BigDecimal itemPrice = item.getProduct().getBasePrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                originalTotal = originalTotal.add(itemPrice);
            }
            finalTotal = originalTotal;

            for (DiscountStrategyRule rule : discountRules) {
                finalTotal = rule.apply(cartItems, customer, paymentInfo, finalTotal, appliedDiscounts);
            }

            return DiscountedPrice.builder()
                    .originalPrice(originalTotal.setScale(2, RoundingMode.HALF_UP))
                    .finalPrice(finalTotal.setScale(2, RoundingMode.HALF_UP))
                    .appliedDiscounts(appliedDiscounts)
                    .message("Discounts applied successfully")
                    .build();

        } catch (Exception e) {
            throw new DiscountCalculationException("Error calculating discounts", e);
        }
    }

    @Override
    public boolean validateDiscountCode(String code, List<CartItem> cartItems, CustomerProfile customer) {
        return "SUPER29".equalsIgnoreCase(code);
    }
}