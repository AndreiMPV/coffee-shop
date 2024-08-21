package org.shop.service.order.calculate;

import org.shop.model.bonus.BonusType;
import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

public class StampCardCostCalculationStrategy implements CostCalculationStrategy {

    @Override
    public BigDecimal calculateTotalCost(Order order) {
        List<Product> products = order.getProducts();
        List<Product> notUsedBeverageAttachedToCard = order.getStampCard().get().getCardProducts().stream()
                .filter(product -> product.getBonusesApplied().isEmpty())
                .filter(product -> ProductGroup.BEVERAGE == product.getGroup())
                .toList();

        if (notUsedBeverageAttachedToCard.size() >= 5) {
            IntStream.range(1, notUsedBeverageAttachedToCard.size())
                    .forEach(i -> {
                        Product product = notUsedBeverageAttachedToCard.get(i);
                        if (i % 5 == 0) {
                            product.getBonusesApplied().add(BonusType.FREE);
                        } else {
                            product.getBonusesApplied().add(BonusType.USED);
                        }
                    });
        }

        return products.stream()
                .filter(product -> !product.getBonusesApplied().contains(BonusType.FREE))
                .map(Product::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}