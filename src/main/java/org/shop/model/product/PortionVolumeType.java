package org.shop.model.product;

import java.util.Optional;

public enum PortionVolumeType {
    L_0_25(0.25, "l");

    private final double volume;
    private final String measure;

    PortionVolumeType(double volume, String measure) {
        this.volume = volume;
        this.measure = measure;
    }

    @Override
    public String toString() {
        return this.volume + Optional.ofNullable(this.measure).orElse("");
    }
}
