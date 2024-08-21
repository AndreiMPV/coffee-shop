package org.shop.model.product.extra;

import org.shop.model.product.BaseProduct;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Extra extends BaseProduct {
    protected final Product product;

    protected Extra(Product product, double cost) {
        super(cost);
        this.product = product;
    }

    @Override
    public BigDecimal getCost() {
        return this.cost.add(product.getCost());
    }

    @Override
    public ProductGroup getGroup() {
        return ProductGroup.EXTRA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Extra extra = (Extra) o;
        return Objects.equals(product, extra.product);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(product);
    }
}
