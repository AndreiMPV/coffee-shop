package org.shop.model.product.extra;

import org.shop.model.product.Product;

public class FoamedMilk extends Extra {
    private static final String NAME = "Extra milk";

    public FoamedMilk(Product product, double cost) {
        super(product, cost);
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " with " + NAME.toLowerCase();
    }
}
