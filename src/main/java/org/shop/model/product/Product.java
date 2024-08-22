package org.shop.model.product;

import org.shop.model.bonus.BonusType;
import java.math.BigDecimal;

public interface Product {
    String getProductName();
    ProductGroup getProductGroup();
    BigDecimal getInitialCost();
    BonusType getBonus();
    void setBonus(BonusType bonus);
    void setDiscountAmount(BigDecimal discountAmount);
    BigDecimal getInitialTotalCost();
    BigDecimal getTotalDiscount();
}
