package org.shop.model.product;

import org.shop.model.bonus.BonusType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public  abstract class BaseProduct implements Product {
    protected final BigDecimal cost;
    private final List<BonusType> bonusTypes = new ArrayList<>();

    protected BaseProduct(double cost) {
        this.cost = BigDecimal.valueOf(cost);
    }

    @Override
    public BigDecimal getCost() {
        return this.cost;
    }

    @Override
    public List<BonusType> getBonusesApplied() {
        return bonusTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseProduct that = (BaseProduct) o;
        return Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cost);
    }
}
