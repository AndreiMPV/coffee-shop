package org.shop.service.order.calculate;

import org.shop.model.bonus.BonusType;
import org.shop.model.order.Order;
import org.shop.model.product.MainProduct;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class NoStampCardBonusApplyStrategy implements BonusApplyStrategy {

    @Override
    public void applyBonus(Order order) {
        List<MainProduct> products = order.getProducts();
        Map<ProductGroup, List<Product>> groupedProducts = products.stream()
                .filter(product -> Objects.nonNull(product.getProductGroup()))
                .collect(Collectors.groupingBy(Product::getProductGroup));

        boolean hasSnack = groupedProducts.containsKey(ProductGroup.SNACK);
        boolean hasBeverage = groupedProducts.containsKey(ProductGroup.BEVERAGE);

        if (hasSnack && hasBeverage) {
            products.stream()
                    .flatMap(product -> product.getExtraProducts().stream())
                    .findAny().ifPresent(extra -> {
                        extra.setBonus(BonusType.FREE);
                        extra.setDiscountAmount(calculateDiscountAmount(extra));
                    });
        }
    }
}