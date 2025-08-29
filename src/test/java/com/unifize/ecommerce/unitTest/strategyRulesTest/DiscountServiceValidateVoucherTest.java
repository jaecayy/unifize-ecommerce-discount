package com.unifize.ecommerce.unitTest.strategyRulesTest;

import com.unifize.ecommerce.exception.DiscountValidationException;
import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.Product;
import com.unifize.ecommerce.model.Voucher;
import com.unifize.ecommerce.repository.VoucherRepository;
import com.unifize.ecommerce.service.impl.DiscountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountServiceValidateVoucherTest {

    private DiscountServiceImpl discountService;
    private VoucherRepository voucherRepository;
    private List<CartItem> dummyCart;
    private CustomerProfile regularCustomer;
    private CustomerProfile premiumCustomer;

    @BeforeEach
    void setup() {
        Voucher v1 = Voucher.builder()
                .code("WELCOME10")
                .percent(10.0)
                .active(true)
                .expiry(LocalDate.now().plusDays(5))
                .minCustomerTier("REGULAR")
                .build();

        Voucher v2 = Voucher.builder()
                .code("PREMIUM50")
                .percent(50.0)
                .active(true)
                .expiry(LocalDate.now().plusDays(10))
                .minCustomerTier("PREMIUM")
                .build();

        Voucher v3 = Voucher.builder()
                .code("EXPIRED20")
                .percent(20.0)
                .active(true)
                .expiry(LocalDate.now().minusDays(1))
                .minCustomerTier("REGULAR")
                .build();
        VoucherRepository voucherRepository = new VoucherRepository(List.of(v1,v2,v3));
        discountService = new DiscountServiceImpl(voucherRepository);

        Product shoe = Product.builder()
                .id("P1").brand("NIKE").category("SHOES")
                .basePrice(BigDecimal.valueOf(1000)).build();
        dummyCart = List.of(CartItem.builder().product(shoe).quantity(1).build());

        regularCustomer = CustomerProfile.builder().id("C1").tier("REGULAR").build();
        premiumCustomer = CustomerProfile.builder().id("C2").tier("PREMIUM").build();
    }

    @Test
    void validVoucherShouldReturnTrue() {
        boolean result = discountService.validateDiscountCode("WELCOME10", dummyCart, regularCustomer);
        assertTrue(result);
    }

    @Test
    void expiredVoucherShouldThrowException() {
        DiscountValidationException ex = assertThrows(
                DiscountValidationException.class,
                () -> discountService.validateDiscountCode("EXPIRED20", dummyCart, regularCustomer)
        );
        assertEquals("Voucher expired", ex.getMessage());
    }

    @Test
    void wrongTierVoucherShouldThrowException() {
        DiscountValidationException ex = assertThrows(
                DiscountValidationException.class,
                () -> discountService.validateDiscountCode("PREMIUM50", dummyCart, regularCustomer)
        );
        assertEquals("Voucher not valid for the customer tier", ex.getMessage());
    }

    @Test
    void invalidVoucherShouldThrowException() {
        DiscountValidationException ex = assertThrows(
                DiscountValidationException.class,
                () -> discountService.validateDiscountCode("FAKE123", dummyCart, regularCustomer)
        );
        assertEquals("Invalid voucher code", ex.getMessage());
    }
}
