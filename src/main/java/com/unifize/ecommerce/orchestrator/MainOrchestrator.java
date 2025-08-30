package com.unifize.ecommerce.orchestrator;

import com.unifize.ecommerce.constants.DiscountConstants;
import com.unifize.ecommerce.model.*;
import com.unifize.ecommerce.repository.VoucherRepository;
import com.unifize.ecommerce.service.DiscountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class MainOrchestrator {

    private final DiscountService discountService;
    private final List<Product> products;
    private final VoucherRepository voucherRepository;

    private final Scanner scanner = new Scanner(System.in);
    private final List<CartItem> cart = new ArrayList<>();
    private CustomerProfile currentCustomer;

    public MainOrchestrator(DiscountService discountService,
                            List<Product> products,
                            VoucherRepository voucherRepository) {
        this.discountService = discountService;
        this.products = products;
        this.voucherRepository = voucherRepository;
    }

    public void runApp() {

            System.out.println("=== Welcome to Unifize E-Commerce ===");
            selectCustomer();

            boolean exit = false;
            while (!exit) {
                System.out.println("\nChoose an action:");
                System.out.println("1. Show Products");
                System.out.println("2. Add Product to Cart");
                System.out.println("3. View Cart");
                System.out.println("4. Show Vouchers");
                System.out.println("5. Apply Vouchers");
                System.out.println("6. Show All Discounts");
                System.out.println("7. Checkout");
                System.out.println("8. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> showProducts();
                    case 2 -> addProductToCart();
                    case 3 -> viewCart();
                    case 4 -> showVouchers();
                    case 5 -> applyVoucher();
                    case 6 -> showAllDiscounts();
                    case 7 -> checkout();
                    case 8 -> exit = true;
                    default -> System.out.println("Invalid choice, try again.");
                }
            }
            System.out.println("Thank you for shopping with us!");

    }

    private void selectCustomer() {
        System.out.println("\nSelect Customer:");
        System.out.println("1. REGULAR Customer");
        System.out.println("2. PREMIUM Customer");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            currentCustomer = CustomerProfile.builder().id("C1").tier("REGULAR").build();
        } else {
            currentCustomer = CustomerProfile.builder().id("C2").tier("PREMIUM").build();
        }
        System.out.println("Customer set to: " + currentCustomer.getTier());
    }

    private void showProducts() {
        System.out.println("\nAvailable Products:");
        for (Product product : products) {
            System.out.println(product.getId() + " | " + product.getBrand() + " | " +
                    product.getCategory() + " | Price: " + product.getBasePrice());
        }
    }

    private void addProductToCart() {
        showProducts();
        System.out.print("Enter Product ID to add: ");
        String productId = scanner.nextLine();

        products.stream()
                .filter(p -> p.getId().equalsIgnoreCase(productId))
                .findFirst()
                .ifPresentOrElse(product -> {
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine();
                    cart.add(CartItem.builder().product(product).quantity(qty).build());
                    System.out.println("Added " + qty + " x " + product.getBrand() + " " + product.getCategory() + " to cart.");
                }, () -> System.out.println("Invalid Product ID."));
    }

    private void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("\nCart is empty.");
            return;
        }
        System.out.println("\nYour Cart:");
        for (CartItem item : cart) {
            System.out.println(item.getProduct().getBrand() + " " + item.getProduct().getCategory() +
                    " x " + item.getQuantity() + " = " +
                    item.getProduct().getBasePrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
    }

    private String appliedVoucherCode = null;
    private void applyVoucher() {
        System.out.print("Enter voucher code: ");
        String code = scanner.nextLine();
        try {
            if (discountService.validateDiscountCode(code, cart, currentCustomer)) {
                appliedVoucherCode = code;
                System.out.println("Voucher " + code + " is valid and will be applied at checkout.");
            }
        } catch (Exception e) {
            System.out.println("Voucher error: " + e.getMessage());
        }
    }

    private void checkout() {
        if (cart.isEmpty()) {
            System.out.println("\nCart is empty. Add items before checkout.");
            return;
        }

        Optional<PaymentInfo> payment = Optional.of(PaymentInfo.builder()
                .bankName(DiscountConstants.ICICI_BANK).method("CARD").build());

        try {
            DiscountedPrice result = discountService.calculateCartDiscounts(cart, currentCustomer, payment, appliedVoucherCode);
            System.out.println("\n=== Checkout Summary ===");
            System.out.println("Original Price: " + result.getOriginalPrice());
            System.out.println("Final Price: " + result.getFinalPrice());
            System.out.println("Applied Discounts: " );
            result.getAppliedDiscounts().forEach((k, v) ->
                    System.out.println(" - " + k + " : " + v));
            System.out.println(result.getMessage());
        } catch (Exception e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }
    }

    private void showAllDiscounts() {
        showBrandDiscounts();
        showCategoryDiscounts();
        showBankDiscounts();
    }

    private void showBrandDiscounts() {
        System.out.println("\n=== Available Brand Discounts ===");
        DiscountConstants.BRAND_DISCOUNTS.forEach((brand, percent) ->
                System.out.println(brand + " : " + (percent * 100) + "% off")
        );
    }

    private void showCategoryDiscounts() {
        System.out.println("\n=== Available Category Discounts ===");
        DiscountConstants.CATEGORY_DISCOUNTS.forEach((category, percent) ->
                System.out.println(category + " : " + (percent * 100) + "% off")
        );
    }

    private void showBankDiscounts() {
        System.out.println("\n=== Available Bank Discounts ===");
        DiscountConstants.BANK_DISCOUNTS.forEach((bank, percent) ->
                System.out.println(bank + " : " + (percent * 100) + "% off")
        );
    }


    private void showVouchers() {
        System.out.println("\n=== Available Vouchers ===");
        List<Voucher> allVouchers = voucherRepository.findAll();
        for (Voucher voucher : allVouchers) {
            System.out.println(voucher.getCode() + " - " + voucher.getPercent() +"% Off");
        }

    }
}
