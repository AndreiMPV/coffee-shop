package org.shop.service.order.calculate;

import org.shop.model.order.Order;

public class CalculateStrategyResolver {

    public BonusApplyStrategy resolve(Order order) {
        return order.getStampCard().isPresent() ? new StampCardBonusApplyStrategy() : new NoStampCardBonusApplyStrategy();
    }
}
