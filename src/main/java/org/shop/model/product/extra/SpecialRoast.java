package org.shop.model.product.extra;

import org.shop.model.product.Product;

public class SpecialRoast extends Extra {
    private static final String NAME = "Special roast";

    public SpecialRoast(Product product, double cost) {
        super(product, cost);
    }

    @Override
    public String getDescription() {
        return product.getDescription() + " with " + NAME.toLowerCase();
    }
}
