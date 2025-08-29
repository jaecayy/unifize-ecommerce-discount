package com.unifize.ecommerce.config;

import com.unifize.ecommerce.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class SeedDataConfig {

    @Bean
    public List<CartItem> testCart() {
        Product pumaTshirt = Product.builder()
                .id("P1")
                .brand("PUMA")
                .category("T-SHIRT")
                .brandTier(BrandTier.PREMIUM)
                .basePrice(BigDecimal.valueOf(1000))
                .build();

        return List.of(CartItem.builder()
                .product(pumaTshirt)
                .quantity(1)
                .size("M")
                .build());
    }

    @Bean
    public CustomerProfile testCustomer() {
        return CustomerProfile.builder()
                .id("C1")
                .tier("REGULAR")
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
}