package org.shop.model.product.extra;

import org.shop.model.product.BaseExtraProduct;

public class SpecialRoast extends BaseExtraProduct {
    public static final String NAME = "Special roast";

    @Override
    public String getDescription() {
        return NAME.toLowerCase();
    }
}
