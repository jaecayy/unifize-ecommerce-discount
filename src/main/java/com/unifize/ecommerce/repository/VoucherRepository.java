package com.unifize.ecommerce.repository;

import com.unifize.ecommerce.model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VoucherRepository {
    private final List<Voucher> vouchers;

    public VoucherRepository(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public Optional<Voucher> findByCode(String code) {
        return vouchers.stream()
                .filter(v -> v.getCode().equalsIgnoreCase(code) && v.isActive())
                .findFirst();
    }

    public List<Voucher> findAll() {
        return vouchers;
    }
}