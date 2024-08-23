package org.shop.model.order;

import org.shop.model.bonus.StampCard;
import org.shop.model.product.MainProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Represents an order in the coffee shop, containing a list of main products and an optional
 * stamp card that tracks loyalty rewards. The order also records the date and time it was created.
 */
public class Order {

    /**
     * A list of main products included in the order.
     */
    private final List<MainProduct> products;

    /**
     * An optional stamp card associated with the order, used for tracking loyalty rewards.
     */
    private final StampCard stampCard;

    /**
     * Constructs a new {@code Order} with the specified list of products and a stamp card.
     * If a stamp card is provided, it is automatically updated with the products from this order.
     *
     * @param products the list of main products included in the order.
     * @param stampCard the stamp card associated with the order, or {@code null} if none is used.
     */
    public Order(List<MainProduct> products, StampCard stampCard) {
        this.products = products;
        this.stampCard = stampCard;
        if (stampCard != null) {
            stampCard.addOrderedProducts(products);
        }
    }

    /**
     * Calculates and returns the total cost of the order. The total cost is computed by summing the
     * initial total cost of all products in the order, minus any applicable discounts.
     *
     * @return the total cost of the order as a {@link BigDecimal}.
     */
    public BigDecimal getTotalCost() {
        return products.stream()
                .map(product -> product.getInitialTotalCost().subtract(product.getTotalDiscount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Returns list of the main products included in the order.
     *
     * @return a new list containing the main products in the order.
     */
    public List<MainProduct> getProducts() {
        return products;
    }

    /**
     * Returns the optional stamp card associated with the order. If no stamp card was provided,
     * this method returns an empty {@link Optional}.
     *
     * @return an {@link Optional} containing the stamp card, or an empty {@link Optional} if none is present.
     */
    public Optional<StampCard> getStampCard() {
        return Optional.ofNullable(stampCard);
    }
}
