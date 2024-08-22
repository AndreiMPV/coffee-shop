package org.shop.model.product;


import org.shop.model.bonus.BonusType;

import java.math.BigDecimal;
import java.util.List;

public class BaseExtraProduct implements Product{
    private final String productName;
    private final ProductGroup productGroup;
    private final BigDecimal initialCost;

    private BonusType bonus;
    private BigDecimal discountAmount = BigDecimal.ZERO;

    public BaseExtraProduct(String productName, double initialCost) {
        this.productName = productName;
        this.productGroup = ProductGroup.EXTRA;
        this.initialCost = BigDecimal.valueOf(initialCost);
    }

    public String getProductName() {
        return productName;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    @Override
    public BaseMainProduct.PortionSizeType getPortionSizeType() {
        return null;
    }

    @Override
    public BaseMainProduct.PortionVolumeType getPortionVolumeType() {
        return null;
    }

    public BigDecimal getInitialCost() {
        return initialCost;
    }

    public BonusType getBonus() {
        return bonus;
    }

    public void setBonus(BonusType bonus) {
        this.bonus = bonus;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public ProductGroup getGroup() {
        return ProductGroup.EXTRA;
    }

    public BaseExtraProduct clone() {
        return new BaseExtraProduct(this.productName, this.initialCost.toBigInteger().doubleValue());
    }

    @Override
    public BigDecimal getInitialTotalCost() {
        return initialCost;
    }

    @Override
    public BigDecimal getTotalDiscount() {
        return discountAmount;
    }

    @Override
    public List<BaseExtraProduct> getExtraProducts() {
        return List.of();
    }
}