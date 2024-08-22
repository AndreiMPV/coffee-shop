package org.shop.model.product.main;

import org.shop.model.product.BaseMainProduct;
import org.shop.model.product.MeasurableProduct;
import org.shop.model.product.ProductGroup;

import java.math.BigDecimal;


public class Coffee extends BaseMainProduct implements MeasurableProduct {
    public static final String NAME = "Coffee";
    private final CoffeeSizeType size;

    public Coffee() {
        super(, );
        this.size = CoffeeSizeType.SMALL;
        this.cost = size.getCost();
    }

    public Coffee(CoffeeSizeType size) {
        super(, );
        this.cost = size.getCost();
        this.size = size;
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

    public enum CoffeeSizeType implements MeasurableProduct {
        SMALL(2.55), MEDIUM(3.05), LARGE(3.55);

        private BigDecimal cost;

        CoffeeSizeType(double cost) {
            this.cost = BigDecimal.valueOf(cost);
        }

        public BigDecimal getCost() {
            return cost;
        }

        @Override
        public String getSize() {
            return MEDIUM.name();
        }
    }
}
