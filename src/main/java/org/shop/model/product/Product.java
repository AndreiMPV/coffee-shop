package org.shop.model.product;

import org.shop.model.bonus.BonusType;
import java.math.BigDecimal;
import java.util.List;

public interface Product {

    String getProductName();
    ProductGroup getProductGroup();
    BaseMainProduct.PortionSizeType getPortionSizeType();
    BaseMainProduct.PortionVolumeType getPortionVolumeType();
    BigDecimal getInitialCost();
    BonusType getBonus();
    void setBonus(BonusType bonus);
    BigDecimal getDiscountAmount();
    void setDiscountAmount(BigDecimal discountAmount);
    BigDecimal getInitialTotalCost();
    BigDecimal getTotalDiscount();
    List<BaseExtraProduct> getExtraProducts();
}
