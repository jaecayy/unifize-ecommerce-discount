package com.unifize.ecommerce.strategyRules;

import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.PaymentInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VoucherDiscountStrategyRule implements DiscountStrategyRule{

    private final String voucherCode;

    public VoucherDiscountStrategyRule(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    @Override
    public BigDecimal apply(List<CartItem> cartItems, CustomerProfile customer, Optional<PaymentInfo> paymentInfo, BigDecimal currentTotal, Map<String, BigDecimal> appliedDiscounts) {
        if ("SUPER29".equalsIgnoreCase(voucherCode)) {
            BigDecimal discount = currentTotal.multiply(BigDecimal.valueOf(0.29));
            appliedDiscounts.merge("Voucher SUPER29", discount, BigDecimal::add);
            return currentTotal.subtract(discount);
        }
        return currentTotal;
    }
}
