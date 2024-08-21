package org.shop.service.order.calculate;

import org.shop.model.order.Order;

import java.math.BigDecimal;

public interface CostCalculationStrategy {
    BigDecimal calculateTotalCost(Order order);
}