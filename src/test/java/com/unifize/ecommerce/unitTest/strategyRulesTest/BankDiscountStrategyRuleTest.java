package com.unifize.ecommerce.unitTest.strategyRulesTest;

import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.PaymentInfo;
import com.unifize.ecommerce.model.Product;
import com.unifize.ecommerce.strategyRules.BankDiscountStrategyRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankDiscountStrategyRuleTest {

    @Test
    void applies10PercentForIciciBank() {
        Product jeans = Product.builder().id("P5").brand("LEVIS").category("JEANS")
                .basePrice(BigDecimal.valueOf(2000)).build();

        CartItem item = CartItem.builder().product(jeans).quantity(1).build();

        PaymentInfo payment = PaymentInfo.builder().bankName("ICICI").method("CARD").build();

        Map<String, BigDecimal> applied = new HashMap<>();
        BankDiscountStrategyRule rule = new BankDiscountStrategyRule();

        BigDecimal result = rule.apply(List.of(item),
                CustomerProfile.builder().tier("REGULAR").build(),
                Optional.of(payment),
                BigDecimal.valueOf(2000),
                applied);

        assertEquals(BigDecimal.valueOf(1800.0), result);
        assertTrue(applied.containsKey("ICICI Bank Offer"));
        assertEquals(BigDecimal.valueOf(200.0), applied.get("ICICI Bank Offer"));
    }
}
