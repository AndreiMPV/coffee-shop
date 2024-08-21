package org.shop.model.product.extra;

import org.shop.model.product.Product;

public class ExtraMilk extends Extra {
    private static final String NAME = "Extra milk";

    public ExtraMilk(Product product, double cost) {
        super(product, cost);
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " with " + NAME.toLowerCase();
    }
}
