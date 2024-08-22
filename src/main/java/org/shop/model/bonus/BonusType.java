package org.shop.model.bonus;

import java.math.BigDecimal;

public enum BonusType {
    FREE(100), USED(0);

    final BigDecimal percentage;
    boolean costApplicable;

    BonusType(int percentage) {
        this.percentage = BigDecimal.valueOf(percentage);
        if (percentage >= 0) {
            costApplicable = true;
        }
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public boolean isCostApplicable() {
        return costApplicable;
    }
}
