package org.shop.service.order;

import org.shop.model.bonus.StampCard;
import org.shop.model.order.Order;
import org.shop.model.product.MainProduct;
import org.shop.service.order.calculate.BonusApplyStrategy;
import org.shop.service.order.calculate.CalculateStrategyResolver;

import java.util.List;

/**
 * The {@code OrderService} class provides services for creating and processing orders.
 */
public class OrderService {
    private final CalculateStrategyResolver calculateStrategyResolver;

    public OrderService(CalculateStrategyResolver calculateStrategyResolver) {
        this.calculateStrategyResolver = calculateStrategyResolver;
    }

    /**
     * Creates an order with the specified products and a stamp card, and applies the appropriate bonuses.
     * <p>
     * @param products a list of {@link MainProduct} to be included in the order
     * @param stampCard the {@link StampCard} to be associated with the order, or {@code null} if not applicable
     * @return the created {@link Order} with bonuses applied
     */
    public Order makeOrder(List<MainProduct> products, StampCard stampCard) {
        Order order = convert(products, stampCard);
        applyBonuses(order);
        return order;
    }

    /**
     * Creates an order with the specified products without a stamp card, and applies the appropriate bonuses.
     * @param products a list of {@link MainProduct} to be included in the order
     * @return the created {@link Order} with bonuses applied
     */
    public Order makeOrder(List<MainProduct> products) {
        Order order = convert(products, null);
        applyBonuses(order);
        return order;
    }

    /**
     * Applies bonuses to the given {@link Order} using the appropriate strategy.
     * <p>
     * This method retrieves the appropriate {@link BonusApplyStrategy} from the
     * {@link CalculateStrategyResolver} and applies the bonus to the order.
     * </p>
     *
     * @param order the {@link Order} to which bonuses will be applied
     */
    private void applyBonuses(Order order) {
        var costCalculationStrategy = calculateStrategyResolver.resolve(order);
        costCalculationStrategy.applyBonus(order);
    }

    /**
     * Converts a list of products and an optional stamp card into an {@link Order} object.
     *
     * @param products a list of {@link MainProduct} to be included in the order
     * @param stampCard the {@link StampCard} to be associated with the order, or {@code null} if not applicable
     * @return the created {@link Order} object
     */
    private Order convert(List<MainProduct> products, StampCard stampCard) {
        return new Order(products, stampCard);
    }
}
