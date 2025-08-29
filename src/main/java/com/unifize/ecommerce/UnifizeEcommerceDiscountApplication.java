package com.unifize.ecommerce;

import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.DiscountedPrice;
import com.unifize.ecommerce.model.PaymentInfo;
import com.unifize.ecommerce.service.DiscountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class UnifizeEcommerceDiscountApplication implements CommandLineRunner {

    private final DiscountService discountService;
    private final List<CartItem> testCart;
    private final CustomerProfile testCustomer;
    private final PaymentInfo iciciPayment;

    public UnifizeEcommerceDiscountApplication(DiscountService discountService,
                               List<CartItem> testCart,
                               CustomerProfile testCustomer,
                               PaymentInfo iciciPayment) {
        this.discountService = discountService;
        this.testCart = testCart;
        this.testCustomer = testCustomer;
        this.iciciPayment = iciciPayment;
    }

	public static void main(String[] args) {
		SpringApplication.run(UnifizeEcommerceDiscountApplication.class, args);
	}

    @Override
    public void run(String... args) {
        System.out.println("=== Discount Demo ===");

        DiscountedPrice result = discountService.calculateCartDiscounts(
                testCart, testCustomer, Optional.of(iciciPayment));

        System.out.println("Original Price: ₹" + result.getOriginalPrice());
        System.out.println("Final Price   : ₹" + result.getFinalPrice());
        System.out.println("Applied Discounts:");
        result.getAppliedDiscounts().forEach((k, v) ->
                System.out.println(" - " + k + ": ₹" + v));
    }

}
