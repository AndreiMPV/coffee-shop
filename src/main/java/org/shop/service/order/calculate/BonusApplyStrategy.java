package org.shop.service.order.calculate;

import org.shop.model.order.Order;
import org.shop.model.product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Defines a strategy for applying bonuses to an order in the coffee shop.
 * Implementations of this interface will provide specific algorithms to apply
 * various types of bonuses, such as discounts to the products in an order.
 */
public interface BonusApplyStrategy {

    /**
     * Applies the bonus strategy to the given order. This method modifies the order
     * by applying any applicable bonuses to its products, potentially altering their
     * final prices or providing other benefits.
     *
     * @param order the {@link Order} to which the bonus should be applied.
     */
    void applyBonus(Order order);

    /**
     * Calculates the discount amount for a given product based on its associated bonus.
     *
     * @param product the {@link Product} for which to calculate the discount.
     * @return the discount amount as a {@link BigDecimal}, rounded to two decimal places.
     */
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