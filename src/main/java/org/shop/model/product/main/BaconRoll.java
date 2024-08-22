package org.shop.model.product.main;

import org.shop.model.product.BaseMainProduct;
import org.shop.model.product.ProductGroup;

import java.math.BigDecimal;

public class BaconRoll extends BaseMainProduct {
    public static final String NAME = "Bacon Roll";

    public BaconRoll() {
        super(, );
        this.cost = BigDecimal.valueOf(4.53);
    }

    @Override
    public ProductGroup getGroup() {
        return ProductGroup.SNACK;
    }

    @Override
    public String getDescription() {
        return NAME;
    }
}
