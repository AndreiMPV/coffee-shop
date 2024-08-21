package org.shop.model.product.beverage;

import org.shop.model.product.Measurable;


public class Coffee extends Beverage {
    private static final String NAME = "Coffee";

    public Coffee(CoffeeSizeType size, double cost) {
        super(size, cost);
    }

    @Override
    public String getDescription() {
        return NAME + " " + getSize().toLowerCase();
    }

    public enum CoffeeSizeType implements Measurable {
        SMALL, MEDIUM, LARGE;

        @Override
        public String getSize() {
            return MEDIUM.name();
        }
    }
}
