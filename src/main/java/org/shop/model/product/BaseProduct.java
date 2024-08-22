package org.shop.model.product;

import org.shop.model.bonus.BonusType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class BaseProduct implements Product {
    private final String productName;
    private final ProductGroup productGroup;
    private final BigDecimal initialCost;

    private BonusType bonus;
    private BigDecimal discountAmount = BigDecimal.ZERO;

    public BaseProduct(String productName, BaseMainProduct.PortionSizeType portionSizeType,
                           BaseMainProduct.PortionVolumeType portionVolumeType,
                           double initialCost, ProductGroup productGroup) {
        this.productName = productName;
        this.productGroup = productGroup;
        this.initialCost = BigDecimal.valueOf(initialCost);
    }

    @Override
    public BigDecimal getCost() {
        if (getBonus() != null && getBonus().isCostApplicable()) {
            return calculateDiscountedCost(this.cost, getBonus().getPercentage());
        }
        return this.cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseProduct that = (BaseProduct) o;
        return Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cost);
    }

    @Override
    public BonusType getBonus() {
        return bonus;
    }

    @Override
    public void setBonus(BonusType bonus) {
        this.bonus = bonus;
    }

    protected BigDecimal calculateDiscountedCost(BigDecimal cost, BigDecimal discountPercentage) {
        BigDecimal discountAmount = cost.multiply(discountPercentage)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return cost.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }
}
