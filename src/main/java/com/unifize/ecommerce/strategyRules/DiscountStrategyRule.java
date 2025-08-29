package com.unifize.ecommerce.strategyRules;

import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.PaymentInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DiscountStrategyRule {

    BigDecimal apply(List<CartItem> cartItems,
                     CustomerProfile customer,
                     Optional<PaymentInfo> paymentInfo,
                     BigDecimal currentTotal,
                     Map<String, BigDecimal> appliedDiscounts);

}
