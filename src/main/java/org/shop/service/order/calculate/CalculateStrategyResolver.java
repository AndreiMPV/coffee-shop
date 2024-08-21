package org.shop.service.order.calculate;

import org.shop.model.order.Order;

public class CalculateStrategyResolver {

    public CostCalculationStrategy resolve(Order order) {
        return order.getStampCard().isPresent() ? new StampCardCostCalculationStrategy() : new NoStampCardCostCalculationStrategy();
    }
}
