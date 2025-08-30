package com.unifize.ecommerce.strategyRules;

import com.unifize.ecommerce.constants.DiscountConstants;
import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.PaymentInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CategoryDiscountStrategyRule implements DiscountStrategyRule{
    @Override
    public BigDecimal apply(List<CartItem> cartItems, CustomerProfile customer, Optional<PaymentInfo> paymentInfo, BigDecimal currentTotal, Map<String, BigDecimal> appliedDiscounts) {
        BigDecimal discountTotal = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            String category = item.getProduct().getCategory().toUpperCase();
            if (DiscountConstants.CATEGORY_DISCOUNTS.containsKey(category)) {
                double percent = DiscountConstants.CATEGORY_DISCOUNTS.get(category);
                BigDecimal itemTotal = item.getProduct().getBasePrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                BigDecimal discount = itemTotal.multiply(BigDecimal.valueOf(percent));
                discountTotal = discountTotal.add(discount);
                appliedDiscounts.merge(category + " Category Discount", discount, BigDecimal::add);
                currentTotal = currentTotal.subtract(discount);
            }
        }

        return currentTotal;
    }
}
