package org.shop.model.product;

import org.shop.model.bonus.BonusType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MainProduct implements Product {
    private final List<ExtraProduct> extraProducts = new ArrayList<>();
    private final String productName;
    private final ProductGroup productGroup;
    private final PortionSizeType portionSizeType;
    private final PortionVolumeType portionVolumeType;
    private final BigDecimal initialCost;

    private BonusType bonus;
    private BigDecimal discountAmount = BigDecimal.ZERO;

    public MainProduct(String productName, ProductGroup productGroup,
                       PortionSizeType portionSizeType, PortionVolumeType portionVolumeType,
                       double initialCost) {
        this.productName = productName;
        this.productGroup = productGroup;
        this.initialCost = BigDecimal.valueOf(initialCost);
        this.portionSizeType = portionSizeType;
        this.portionVolumeType = portionVolumeType;
    }

    @Override
    public ProductGroup getProductGroup() {
        return productGroup;
    }


    public PortionVolumeType getPortionVolumeType() {
        return portionVolumeType;
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
    public BigDecimal getTotalDiscount() {
        BigDecimal extraDiscount = extraProducts.stream()
                .map(ExtraProduct::getTotalDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return extraDiscount.add(discountAmount);
    }

    @Override
    public BigDecimal getInitialTotalCost() {
        BigDecimal extraCost = extraProducts.stream()
                    .map(ExtraProduct::getInitialTotalCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        return extraCost.add(initialCost);
    }


    public PortionSizeType getPortionSizeType() {
        return portionSizeType;
    }

    public String getProductName() {
        return productName;
    }

    public void addExtraProduct(ExtraProduct extraProduct) {
        extraProducts.add(extraProduct);
    }

    public List<ExtraProduct> getExtraProducts() {
        return extraProducts;
    }

    public enum PortionSizeType {
        SMALL, MEDIUM, LARGE
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MainProduct that = (MainProduct) object;
        return Objects.equals(extraProducts, that.extraProducts) && Objects.equals(productName, that.productName) && productGroup == that.productGroup && portionSizeType == that.portionSizeType && portionVolumeType == that.portionVolumeType && Objects.equals(initialCost, that.initialCost) && bonus == that.bonus && Objects.equals(discountAmount, that.discountAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extraProducts, productName, productGroup, portionSizeType, portionVolumeType, initialCost, bonus, discountAmount);
    }

    public enum PortionVolumeType {
        L_0_25(0.25, "l");

        private final double volume;
        private final String measure;

        PortionVolumeType(double volume, String measure) {
            this.volume = volume;
            this.measure = measure;
        }

        @Override
        public String toString() {
            return this.volume + Optional.ofNullable(this.measure).orElse("");
        }
    }

    public MainProduct clone() {
        return new MainProduct(this.productName, this.productGroup, this.portionSizeType,
                this.portionVolumeType, this.initialCost.toBigInteger().doubleValue());
    }
}
