package org.shop.model.product.snack;

import org.shop.model.product.BaseProduct;
import org.shop.model.product.ProductGroup;

public abstract class Snack extends BaseProduct {

    protected Snack(double cost) {
        super(cost);
    }

    @Override
    public ProductGroup getGroup() {
        return ProductGroup.SNACK;
    }
}
