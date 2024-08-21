package org.shop.model.product.beverage;

import org.shop.model.product.Measurable;

import java.util.Optional;

public class OrangeFreshJuice extends Beverage {
    private static final String NAME = "Freshly squeezed orange juice";

    public OrangeFreshJuice(JuiceSizeType size, double cost) {
        super(size, cost);
    }

    @Override
    public String getDescription() {
        return NAME + " " + getSize().toLowerCase();
    }

    public enum JuiceSizeType implements Measurable {
        L_0_25(0.25, "l");

        private double volume;
        private String measure;

        JuiceSizeType(double volume, String measure) {
            this.volume = volume;
            this.measure = measure;
        }

        @Override
        public String getSize() {
            return this.volume + Optional.ofNullable(this.measure).orElse("");
        }
    }
}
