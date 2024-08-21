package org.shop.service.order;

import org.shop.model.Currency;
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
        return order;
    }

    public Order makeOrder(List<Product> products) {
        Order order = convert(products, null);
        return order;
    }

    public BigDecimal calculateTotal(Order order) {
        return order.getTotalCost(calculateStrategyResolver);
    }
    private Order convert(List<Product> products, StampCard stampCard) {
        return new Order(products, stampCard, Currency.CHF);
    }

}
