package org.shop.model.order;

import org.shop.model.bonus.StampCard;
import org.shop.model.product.MainProduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Order {
    private final List<MainProduct> products;
    private final StampCard stampCard;
    private final LocalDateTime dateCreated = LocalDateTime.now();

    public  Order(List<MainProduct> products, StampCard stampCard) {
        this.products = products;
        this.stampCard = stampCard;
        if (stampCard != null) {
            stampCard.addOrderedProducts(products);
        }
    }

    public BigDecimal getTotalCost() {
        return products.stream()
                .map( product -> product.getInitialTotalCost().subtract(product.getTotalDiscount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MainProduct> getProducts() {
        return new ArrayList<>(products);
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public Optional<StampCard> getStampCard() {
        return Optional.ofNullable(stampCard);
    }
}
