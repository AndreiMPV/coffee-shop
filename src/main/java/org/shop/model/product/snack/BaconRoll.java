package org.shop.model.product.snack;


public class BaconRoll extends Snack {
    private static final String NAME = "Bacon Roll";

    public BaconRoll(double cost) {
        super(cost);
    }

    @Override
    public String getDescription() {
        return NAME;
    }
}
