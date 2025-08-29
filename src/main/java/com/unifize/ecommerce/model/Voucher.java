package com.unifize.ecommerce.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Voucher {
    private String code;
    private String description;
    private boolean active;
    private LocalDate expiry;
    private String minCustomerTier;
    private double percent;
}
