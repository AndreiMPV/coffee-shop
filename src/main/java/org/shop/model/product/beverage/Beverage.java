package org.shop.model.product.beverage;

import org.shop.model.product.BaseProduct;
import org.shop.model.product.ProductGroup;
import org.shop.model.product.Measurable;

import java.util.Objects;

public abstract class Beverage extends BaseProduct implements Measurable {
    private final Measurable size;

    protected Beverage(Measurable size, double cost) {
        super(cost);
        this.size = size;
    }

    @Override
    public ProductGroup getGroup() {
        return ProductGroup.BEVERAGE;
    }

    @Override
    public String getSize() {
        return size.getSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beverage beverage = (Beverage) o;
        return Objects.equals(size, beverage.size);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(size);
    }
}
