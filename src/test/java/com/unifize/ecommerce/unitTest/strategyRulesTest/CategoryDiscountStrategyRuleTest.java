package com.unifize.ecommerce.unitTest.strategyRulesTest;

import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.Product;
import com.unifize.ecommerce.strategyRules.CategoryDiscountStrategyRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryDiscountStrategyRuleTest {

    @Test
    void applies10PercentOnTShirts() {
        Product tshirt = Product.builder()
                .id("P2").brand("NIKE").category("T-SHIRT")
                .basePrice(BigDecimal.valueOf(1000))
                .build();

        CartItem item = CartItem.builder()
                .product(tshirt).quantity(2).build();

        Map<String, BigDecimal> applied = new HashMap<>();
        CategoryDiscountStrategyRule rule = new CategoryDiscountStrategyRule();

        BigDecimal result = rule.apply(List.of(item),
                CustomerProfile.builder().tier("REGULAR").build(),
                Optional.empty(),
                BigDecimal.valueOf(2000),
                applied);

        assertEquals(BigDecimal.valueOf(1800.0), result);
    }
}
