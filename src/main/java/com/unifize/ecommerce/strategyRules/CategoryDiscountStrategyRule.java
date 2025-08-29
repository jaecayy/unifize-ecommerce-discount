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
            if (DiscountConstants.CATEGORY_TSHIRT.equalsIgnoreCase(item.getProduct().getCategory())) {
                BigDecimal itemTotal = item.getProduct().getBasePrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                BigDecimal discount = itemTotal.multiply(BigDecimal.valueOf(DiscountConstants.TSHIRT_DISCOUNT_PERCENT));
                discountTotal = discountTotal.add(discount);
            }
        }

        if (discountTotal.compareTo(BigDecimal.ZERO) > 0) {
            appliedDiscounts.merge("T-Shirt Category Discount", discountTotal, BigDecimal::add);
            return currentTotal.subtract(discountTotal);
        }
        return currentTotal;
    }
}
