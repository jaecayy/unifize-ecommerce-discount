package com.unifize.ecommerce.service.impl;

import com.unifize.ecommerce.exception.DiscountCalculationException;
import com.unifize.ecommerce.model.*;
import com.unifize.ecommerce.service.DiscountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Override
    public DiscountedPrice calculateCartDiscounts(List<CartItem> cartItems,
                                                  CustomerProfile customer,
                                                  Optional<PaymentInfo> paymentInfo) {
        try {
            BigDecimal originalTotal = BigDecimal.ZERO;
            BigDecimal finalTotal = BigDecimal.ZERO;
            Map<String, BigDecimal> appliedDiscounts = new HashMap<>();

            for (CartItem item : cartItems) {
                BigDecimal itemPrice = item.getProduct().getBasePrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                originalTotal = originalTotal.add(itemPrice);
            }
            finalTotal = originalTotal;

            for (CartItem item : cartItems) {
                if ("PUMA".equalsIgnoreCase(item.getProduct().getBrand())) {
                    BigDecimal itemTotal = item.getProduct().getBasePrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
                    BigDecimal discount = itemTotal.multiply(BigDecimal.valueOf(0.40));
                    finalTotal = finalTotal.subtract(discount);
                    appliedDiscounts.put("PUMA Brand Discount", discount);
                }
            }

            for (CartItem item : cartItems) {
                if ("T-SHIRT".equalsIgnoreCase(item.getProduct().getCategory())) {
                    BigDecimal itemTotal = item.getProduct().getBasePrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
                    BigDecimal discount = itemTotal.multiply(BigDecimal.valueOf(0.10));
                    finalTotal = finalTotal.subtract(discount);
                    appliedDiscounts.put("T-Shirt Category Discount", discount);
                }
            }

            for(CartItem item : cartItems) {
                if(BrandTier.PREMIUM.equals(item.getProduct().getBrandTier())) {
                    BigDecimal itemTotal = item.getProduct().getBasePrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
                    BigDecimal voucherDiscount = itemTotal.multiply(BigDecimal.valueOf(0.29));
                    finalTotal = finalTotal.subtract(voucherDiscount);
                    appliedDiscounts.put("Premium Brand Voucher SUPER69", voucherDiscount);

                }
            }

            if (paymentInfo.isPresent() && "ICICI".equalsIgnoreCase(paymentInfo.get().getBankName())) {
                BigDecimal bankDiscount = finalTotal.multiply(BigDecimal.valueOf(0.10));
                finalTotal = finalTotal.subtract(bankDiscount);
                appliedDiscounts.put("ICICI Bank Offer", bankDiscount);
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
        return "SUPER69".equalsIgnoreCase(code);
    }
}