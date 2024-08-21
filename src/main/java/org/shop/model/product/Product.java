package org.shop.model.product;

import org.shop.model.bonus.BonusType;
import java.math.BigDecimal;
import java.util.List;

public interface Product {

    String getDescription();
    BigDecimal getCost();
    ProductGroup getGroup();
    List<BonusType> getBonusesApplied();
}
