package org.shop.model.order;

import org.shop.model.bonus.StampCard;
import org.shop.model.product.Product;
import org.shop.service.order.calculate.CalculateStrategyResolver;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Order {
    private final List<Product> products;
    private final StampCard stampCard;
    private final LocalDateTime dateCreated = LocalDateTime.now();

    public  Order(List<Product> products, StampCard stampCard) {
        this.products = products;
        this.stampCard = stampCard;
        if (stampCard != null) {
            stampCard.addOrderedItems(products);
        }
    }

    public BigDecimal getTotalCost(CalculateStrategyResolver calculateStrategyResolver) {
        var costCalculationStrategy = calculateStrategyResolver.resolve(this);
        return costCalculationStrategy.calculateTotalCost(this);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public Optional<StampCard> getStampCard() {
        return Optional.ofNullable(stampCard);
    }
}
