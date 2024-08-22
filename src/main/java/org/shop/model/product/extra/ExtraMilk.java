package org.shop.model.product.extra;

import org.shop.model.product.BaseExtraProduct;

public class ExtraMilk extends BaseExtraProduct {
    public static final String NAME = "Extra milk";

    @Override
    public String getDescription() {
        return NAME.toLowerCase();
    }
}
