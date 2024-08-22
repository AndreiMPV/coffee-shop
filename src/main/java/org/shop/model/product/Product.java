package org.shop.model.product;

import org.shop.model.bonus.BonusType;
import java.math.BigDecimal;

/**
 * Represents a general interface in a coffee shop product, defining the basic properties and behaviors
 * that all products should have.
 */
public interface Product {

    /**
     * Gets the name of the product.
     *
     * @return the product name as a {@link String}.
     */
    String getProductName();

    /**
     * Gets the group or category to which this product belongs.
     *
     * @return the {@link ProductGroup} of the product.
     */
    ProductGroup getProductGroup();

    /**
     * Gets the bonus type associated with the product, which may include
     * discount percentages or other promotional bonuses.
     *
     * @return the {@link BonusType} of the product.
     */
    BonusType getBonus();

    /**
     * Sets the bonus type for the product, which may include discount percentages
     * or other promotional bonuses.
     *
     * @param bonus the {@link BonusType} to be associated with the product.
     */
    void setBonus(BonusType bonus);

    /**
     * Sets the discount amount for the product.
     *
     * @param discountAmount the discount amount as a {@link BigDecimal}.
     */
    void setDiscountAmount(BigDecimal discountAmount);


    /**
     * Gets the initial cost of the product without extra products.
     *
     * @return the initial cost as a {@link BigDecimal}.
     */
    BigDecimal getInitialCost();

    /**
     * Gets the initial total cost of the product, before any discounts are applied.
     *
     * @return the initial total cost as a {@link BigDecimal}.
     */
    BigDecimal getInitialTotalCost();

    /**
     * Gets the total discount applied to the product, including all applicable bonuses.
     *
     * @return the total discount as a {@link BigDecimal}.
     */
    BigDecimal getTotalDiscount();
}
