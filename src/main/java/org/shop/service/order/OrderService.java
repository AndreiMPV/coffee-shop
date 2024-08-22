package org.shop.service.order;

import org.shop.model.bonus.StampCard;
import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.service.order.calculate.CalculateStrategyResolver;

import java.math.BigDecimal;
import java.util.List;

public class OrderService {
    private final CalculateStrategyResolver calculateStrategyResolver;

    public OrderService( CalculateStrategyResolver calculateStrategyResolver) {
        this.calculateStrategyResolver = calculateStrategyResolver;
    }

    public Order makeOrder(List<Product> products, StampCard stampCard) {
        Order order = convert(products, stampCard);
        applyBonuses(order);
        return order;
    }

    public Order makeOrder(List<Product> products) {
        Order order = convert(products, null);
        applyBonuses(order);
        return order;
    }

    private void applyBonuses(Order order) {
        var costCalculationStrategy = calculateStrategyResolver.resolve(order);
        costCalculationStrategy.applyBonus(order);
    }

    private Order convert(List<Product> products, StampCard stampCard) {
        return new Order(products, stampCard);
    }

}
