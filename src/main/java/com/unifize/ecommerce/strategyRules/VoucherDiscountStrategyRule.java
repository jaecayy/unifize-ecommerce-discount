package com.unifize.ecommerce.strategyRules;

import com.unifize.ecommerce.model.CartItem;
import com.unifize.ecommerce.model.CustomerProfile;
import com.unifize.ecommerce.model.PaymentInfo;
import com.unifize.ecommerce.model.Voucher;
import com.unifize.ecommerce.repository.VoucherRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VoucherDiscountStrategyRule implements DiscountStrategyRule{

    private final String voucherCode;
    private final VoucherRepository voucherRepository;

    public VoucherDiscountStrategyRule(String voucherCode, VoucherRepository voucherRepository) {
        this.voucherCode = voucherCode;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public BigDecimal apply(List<CartItem> cartItems, CustomerProfile customer, Optional<PaymentInfo> paymentInfo, BigDecimal currentTotal, Map<String, BigDecimal> appliedDiscounts) {

        if (voucherCode == null || voucherCode.isBlank()) return currentTotal;

        Optional<Voucher> optionalVoucher = voucherRepository.findByCode(voucherCode);
        if(optionalVoucher.isPresent()) {
            Voucher voucher = optionalVoucher.get();
            if(voucher.getExpiry()!=null && voucher.getExpiry().isBefore(LocalDate.now())) {
                return currentTotal;
            }

            if (voucher.getMinCustomerTier() != null && !voucher.getMinCustomerTier().equalsIgnoreCase(customer.getTier())) {
                return currentTotal;
            }

            BigDecimal discount = currentTotal.multiply(BigDecimal.valueOf(voucher.getPercent() / 100.0));
            appliedDiscounts.merge("Voucher " + voucher.getCode(), discount, BigDecimal::add);
            return currentTotal.subtract(discount);

        }

        return currentTotal;
    }
}
