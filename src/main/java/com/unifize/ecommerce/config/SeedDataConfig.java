package com.unifize.ecommerce.config;

import com.unifize.ecommerce.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class SeedDataConfig {

    @Bean
    public List<Product> products() {
        Product pumaTshirt = Product.builder()
                .id("P1")
                .brand("PUMA")
                .category("T-SHIRT")
                .brandTier(BrandTier.REGULAR)
                .basePrice(BigDecimal.valueOf(1000))
                .build();

        Product nikeShoes = Product.builder()
                .id("P2")
                .brand("NIKE")
                .category("SHOES")
                .brandTier(BrandTier.PREMIUM)
                .basePrice(BigDecimal.valueOf(5000))
                .build();

        Product levisJeans = Product.builder()
                .id("P3")
                .brand("LEVIS")
                .category("JEANS")
                .brandTier(BrandTier.BUDGET)
                .basePrice(BigDecimal.valueOf(2500))
                .build();

        Product appleWatch = Product.builder()
                .id("P4")
                .brand("APPLE")
                .category("WATCH")
                .brandTier(BrandTier.PREMIUM)
                .basePrice(BigDecimal.valueOf(20000))
                .build();

        return List.of(pumaTshirt, nikeShoes, levisJeans, appleWatch);
    }

    @Bean
    public List<CartItem> testCart(List<Product> products) {
        return List.of(
                CartItem.builder()
                        .product(products.get(0))
                        .quantity(2)
                        .size("M")
                        .build(),
                CartItem.builder()
                        .product(products.get(1))
                        .quantity(1)
                        .size("9")
                        .build()
        );
    }

    @Bean
    @Primary
    public CustomerProfile regularCustomer() {
        return CustomerProfile.builder()
                .id("C1")
                .tier("REGULAR")
                .build();
    }

    @Bean
    public CustomerProfile premiumCustomer() {
        return CustomerProfile.builder()
                .id("C2")
                .tier("PREMIUM")
                .build();
    }

    @Bean
    public PaymentInfo iciciPayment() {
        return PaymentInfo.builder()
                .method("CARD")
                .bankName("ICICI")
                .cardType("CREDIT")
                .build();
    }

    @Bean
    public PaymentInfo hdfcPayment() {
        return PaymentInfo.builder()
                .method("CARD")
                .bankName("HDFC")
                .cardType("DEBIT")
                .build();
    }

    @Bean
    public PaymentInfo upiPayment() {
        return PaymentInfo.builder()
                .method("UPI")
                .bankName("NONE")
                .cardType(null)
                .build();
    }

    @Bean
    public List<Voucher> vouchers() {
        Voucher v1 = Voucher.builder()
                .code("SUPER29")
                .description("29% off entire cart")
                .active(true)
                .expiry(LocalDate.now().plusDays(30))
                .percent(29.0)
                .build();

        Voucher v2 = Voucher.builder()
                .code("PREMIUM10")
                .description("10% off for PREMIUM customers only")
                .active(true)
                .expiry(LocalDate.now().plusDays(60))
                .percent(10.0)
                .minCustomerTier("PREMIUM")
                .build();

        return List.of(v1, v2);
    }

}