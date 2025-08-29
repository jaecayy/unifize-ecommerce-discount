package com.unifize.ecommerce.unitTest.strategyRulesTest;

import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.Product;
import com.unifize.ecommerce.model.Voucher;
import com.unifize.ecommerce.repository.VoucherRepository;
import com.unifize.ecommerce.strategyRules.VoucherDiscountStrategyRule;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class VoucherDiscountStrategyRuleTest {

    @Test
    void appliesVoucherIfActiveAndValid() {
        Voucher voucher = Voucher.builder()
                .code("WELCOME10")
                .percent(10.0)
                .active(true)
                .expiry(LocalDate.now().plusDays(5))
                .build();

        VoucherRepository repo = new VoucherRepository(List.of(voucher));
        VoucherDiscountStrategyRule rule = new VoucherDiscountStrategyRule("WELCOME10", repo);

        Product shoes = Product.builder().id("P3").brand("NIKE").category("SHOES")
                .basePrice(BigDecimal.valueOf(2000)).build();

        CartItem item = CartItem.builder().product(shoes).quantity(1).build();

        Map<String, BigDecimal> applied = new HashMap<>();
        BigDecimal result = rule.apply(List.of(item),
                CustomerProfile.builder().tier("REGULAR").build(),
                Optional.empty(),
                BigDecimal.valueOf(2000),
                applied);

        assertEquals(BigDecimal.valueOf(1800.0), result);
        assertTrue(applied.containsKey("Voucher WELCOME10"));
        assertEquals(BigDecimal.valueOf(200.0), applied.get("Voucher WELCOME10"));
    }

    @Test
    void ignoresExpiredVoucher() {
        Voucher voucher = Voucher.builder()
                .code("OLD50")
                .percent(50.0)
                .active(true)
                .expiry(LocalDate.now().minusDays(1))
                .build();

        VoucherRepository repo = new VoucherRepository(List.of(voucher));
        VoucherDiscountStrategyRule rule = new VoucherDiscountStrategyRule("OLD50", repo);

        Product jeans = Product.builder().id("P4").brand("LEVIS").category("JEANS")
                .basePrice(BigDecimal.valueOf(1000)).build();

        CartItem item = CartItem.builder().product(jeans).quantity(1).build();

        Map<String, BigDecimal> applied = new HashMap<>();
        BigDecimal result = rule.apply(List.of(item),
                CustomerProfile.builder().tier("REGULAR").build(),
                Optional.empty(),
                BigDecimal.valueOf(1000),
                applied);

        assertEquals(BigDecimal.valueOf(1000), result); // no discount
        assertFalse(applied.containsKey("Voucher OLD50"));
    }
}
