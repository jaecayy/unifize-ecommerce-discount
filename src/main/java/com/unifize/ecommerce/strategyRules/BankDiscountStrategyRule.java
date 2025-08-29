package com.unifize.ecommerce.strategyRules;

import com.unifize.ecommerce.constants.DiscountConstants;
import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.PaymentInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BankDiscountStrategyRule implements DiscountStrategyRule{

    @Override
    public BigDecimal apply(List<CartItem> cartItems,
                            CustomerProfile customer,
                            Optional<PaymentInfo> paymentInfo,
                            BigDecimal currentTotal,
                            Map<String, BigDecimal> appliedDiscounts) {
        if (paymentInfo.isPresent() && DiscountConstants.ICICI_BANK.equalsIgnoreCase(paymentInfo.get().getBankName())) {
            BigDecimal discount = currentTotal.multiply(BigDecimal.valueOf(DiscountConstants.ICICI_DISCOUNT_PERCENT));
            appliedDiscounts.merge("ICICI Bank Offer", discount, BigDecimal::add);
            return currentTotal.subtract(discount);
        }
        return currentTotal;
    }

}
