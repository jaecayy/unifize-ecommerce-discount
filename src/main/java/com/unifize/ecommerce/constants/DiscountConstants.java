package com.unifize.ecommerce.constants;

import java.util.Map;

public class DiscountConstants {

    public static final String PUMA = "PUMA";

    public static final Map<String, Double> CATEGORY_DISCOUNTS = Map.of(
            "T-SHIRT", 0.1,
            "SHOES", 0.15,
            "JEANS", 0.2,
            "WATCH", 0.25
    );


    public static final Map<String, Double> BRAND_DISCOUNTS = Map.of(
            "NIKE", 0.20,
            "PUMA", 0.40,
            "LEVIS", 0.30,
            "APPLE", 0.10
    );

    public static final Map<String, Double> BANK_DISCOUNTS = Map.of(
            "ICICI", 0.1,
            "HDFC", 0.2
    );
    public static final String ICICI_BANK = "ICICI";
}
