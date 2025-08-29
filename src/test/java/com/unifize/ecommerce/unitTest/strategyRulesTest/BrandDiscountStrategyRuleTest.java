package com.unifize.ecommerce.unitTest.strategyRulesTest;

import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.Product;
import com.unifize.ecommerce.strategyRules.BrandDiscountStrategyRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrandDiscountStrategyRuleTest {
    @Test
    void applies40PercentOnPuma() {
        Product pumaTshirt = Product.builder()
                .id("P1").brand("PUMA").category("T-SHIRT")
                .basePrice(BigDecimal.valueOf(1000))
                .build();

        CartItem item = CartItem.builder()
                .product(pumaTshirt).quantity(2).build();

        Map<String, BigDecimal> applied = new HashMap<>();
        BrandDiscountStrategyRule rule = new BrandDiscountStrategyRule();

        BigDecimal result = rule.apply(List.of(item),
                CustomerProfile.builder().tier("REGULAR").build(),
                Optional.empty(),
                BigDecimal.valueOf(2000),
                applied);

        assertEquals(BigDecimal.valueOf(1200.0), result);
        assertTrue(applied.containsKey("PUMA Brand Discount"));
        assertEquals(BigDecimal.valueOf(800.0), applied.get("PUMA Brand Discount"));
    }
}
