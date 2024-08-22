package org.shop.service.order.calculate;

import org.shop.model.order.Order;

/**
 * The {@code CalculateStrategyResolver} class is responsible for determining and providing the appropriate
 * {@link BonusApplyStrategy} implementation based on the state of the provided {@link Order}.
 */
public class CalculateStrategyResolver {

    /**
     * Resolves and returns the appropriate {@link BonusApplyStrategy}
     * based on the presence of a stamp card in the given {@link Order}.
     *
     * @param order the {@link Order} object for which the bonus apply strategy is to be resolved
     * @return a {@link BonusApplyStrategy} implementation corresponding to the state of the order's stamp card
     */
    public BonusApplyStrategy resolve(Order order) {
        return order.getStampCard().isPresent() ? new StampCardBonusApplyStrategy() : new NoStampCardBonusApplyStrategy();
    }
}
