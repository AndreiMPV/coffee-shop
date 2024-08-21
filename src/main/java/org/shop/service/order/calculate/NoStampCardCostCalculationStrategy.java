package org.shop.service.order.calculate;

import org.shop.model.bonus.BonusType;
import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;
import org.shop.model.product.extra.Extra;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NoStampCardCostCalculationStrategy implements CostCalculationStrategy {

    @Override
    public BigDecimal calculateTotalCost(Order order) {
        List<Product> products = order.getProducts();
        Map<ProductGroup, List<Product>> groupedProducts = products.stream()
                .collect(Collectors.groupingBy(Product::getGroup));

        boolean hasSnack = groupedProducts.containsKey(ProductGroup.SNAK);
        boolean hasBeverage = groupedProducts.containsKey(ProductGroup.BEVERAGE);

        if (hasSnack && hasBeverage) {
            products.stream()
                    .filter(product -> ProductGroup.EXTRA == product.getGroup())
                    .findAny().ifPresent(extra -> extra.getBonusesApplied().add(BonusType.FREE));
        }
        return products.stream()
                .filter(product -> !product.getBonusesApplied().contains(BonusType.FREE))
                .map(Product::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}