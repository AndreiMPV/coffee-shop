package org.shop.service.order.calculate;

import org.shop.model.bonus.BonusType;
import org.shop.model.order.Order;
import org.shop.model.product.MainProduct;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The {@code NoStampCardBonusApplyStrategy} class implements the {@link BonusApplyStrategy} interface
 * and provides the strategy for applying bonuses when no stamp card is present.
 * <p>
 * This strategy is used in scenarios where the customer does not have a stamp card,
 * and therefore, the bonus application process should follow a different logic.
 * </p>
 */
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