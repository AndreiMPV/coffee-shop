package org.shop.service.order.calculate;

import org.shop.model.order.Order;

import java.math.BigDecimal;

public interface BonusApplyStrategy {
    void applyBonus(Order order);
}