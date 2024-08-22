package org.shop.model.product.extra;

import org.shop.model.product.BaseExtraProduct;

public class FoamedMilk extends BaseExtraProduct {
    public static final String NAME = "Foamed milk";

    @Override
    public String getDescription() {
        return NAME.toLowerCase();
    }

}
