package org.shop.model.product.main;

import org.shop.model.product.BaseMainProduct;
import org.shop.model.product.MeasurableProduct;
import org.shop.model.product.ProductGroup;

import java.math.BigDecimal;
import java.util.Optional;

public class FreshlySqueezedOrangeJuice extends BaseMainProduct implements MeasurableProduct {
    public static final String NAME = "Orange juice";

    private final JuiceSizeType size;

    public FreshlySqueezedOrangeJuice(JuiceSizeType size) {
        super(, );
        this.size = size;
        this.cost = size.getCost();
    }

    @Override
    public String getDescription() {
        return NAME + " " + getSize().toLowerCase();
    }

    @Override
    public ProductGroup getGroup() {
        return ProductGroup.BEVERAGE;
    }

    @Override
    public String getSize() {
        return size.getSize();
    }

    public enum JuiceSizeType implements MeasurableProduct {
        L_0_25(0.25, "l", 3.95);

        private double volume;
        private String measure;
        private BigDecimal cost;

        public BigDecimal getCost() {
            return cost;
        }
        JuiceSizeType(double volume, String measure, double cost) {
            this.volume = volume;
            this.measure = measure;
            this.cost = BigDecimal.valueOf(cost);
        }

        @Override
        public String getSize() {
            return this.volume + Optional.ofNullable(this.measure).orElse("");
        }
    }
}
