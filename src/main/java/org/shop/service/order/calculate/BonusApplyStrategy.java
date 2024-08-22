package org.shop.service.order.calculate;

import org.shop.model.order.Order;
import org.shop.model.product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface BonusApplyStrategy {
    void applyBonus(Order order);

    default BigDecimal calculateDiscountAmount(Product product) {
        if (product.getBonus() != null && product.getBonus().isCostApplicable()) {
            BigDecimal discountPercentage = product.getBonus().getPercentage();
            return product.getInitialCost().multiply(discountPercentage)
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }
}