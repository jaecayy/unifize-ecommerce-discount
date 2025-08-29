package com.unifize.ecommerce.integrationTest;

import com.unifize.ecommerce.model.*;
import com.unifize.ecommerce.repository.VoucherRepository;
import com.unifize.ecommerce.service.impl.DiscountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscountServiceIntegrationTest {

    private DiscountServiceImpl discountService;

    @BeforeEach
    void setup() {
        Voucher v = Voucher.builder()
                .code("SUPER39")
                .percent(39.0)
                .active(true)
                .expiry(LocalDate.now().plusDays(10))
                .build();

        discountService = new DiscountServiceImpl(new VoucherRepository(List.of(v)));
    }

    @Test
    void appliesAllDiscounts() {
        Product pumaTshirt = Product.builder().id("P1").brand("PUMA").category("T-SHIRT")
                .basePrice(BigDecimal.valueOf(1000)).build();

        CartItem item = CartItem.builder().product(pumaTshirt).quantity(1).build();
        CustomerProfile customer = CustomerProfile.builder().tier("REGULAR").build();
        PaymentInfo payment = PaymentInfo.builder().bankName("ICICI").method("CARD").build();

        DiscountedPrice result = discountService.calculateCartDiscounts(
                List.of(item), customer, Optional.of(payment));

        assertTrue(result.getAppliedDiscounts().size() >= 3);
        assertTrue(result.getFinalPrice().compareTo(result.getOriginalPrice()) < 0);
    }

    @Test
    void returnsSamePriceForEmptyCart() {
        CustomerProfile customer = CustomerProfile.builder().tier("REGULAR").build();
        DiscountedPrice result = discountService.calculateCartDiscounts(
                List.of(), customer, Optional.empty());

        assertEquals(BigDecimal.ZERO.setScale(2), result.getOriginalPrice());
        assertEquals(BigDecimal.ZERO.setScale(2), result.getFinalPrice());
    }

}
