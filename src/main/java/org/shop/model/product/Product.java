package org.shop.model.product;

import org.shop.model.bonus.BonusType;
import java.math.BigDecimal;

public interface Product {

    String getDescription();
    BigDecimal getCost();
    ProductGroup getGroup();
    BonusType getBonus();
    void setBonus(BonusType bonus);
}
