package org.shop.model.product;


import org.shop.model.bonus.BonusType;

import java.math.BigDecimal;

/**
 * Represents an extra product in a coffee shop, such as an add-on or accessory
 * to the main product {@link MainProduct}. This class implements the {@link Product} interface,
 * providing concrete behavior for handling the product's name, group, initial
 * cost, bonuses, and discounts.
 */
public class ExtraProduct implements Product {
    private final String productName;
    private final ProductGroup productGroup;
    private final BigDecimal initialCost;

    private BonusType bonus;
    private BigDecimal discountAmount = BigDecimal.ZERO;

    public ExtraProduct(String productName, double initialCost) {
        this.productName = productName;
        this.productGroup = ProductGroup.EXTRA;
        this.initialCost = BigDecimal.valueOf(initialCost);
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public ProductGroup getProductGroup() {
        return productGroup;
    }

    @Override
    public BigDecimal getInitialCost() {
        return initialCost;
    }

    @Override
    public BonusType getBonus() {
        return bonus;
    }

    @Override
    public void setBonus(BonusType bonus) {
        this.bonus = bonus;
    }

    @Override
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public BigDecimal getInitialTotalCost() {
        return initialCost;
    }

    @Override
    public BigDecimal getTotalDiscount() {
        return discountAmount;
    }

    public ExtraProduct deepCopy() {
        return new ExtraProduct(this.productName, this.initialCost.doubleValue());
    }
}