package org.shop.service.order.calculate;

import org.shop.model.bonus.BonusType;
import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;

import java.util.List;
import java.util.stream.IntStream;


/**
 * The {@code StampCardBonusApplyStrategy} class implements the {@link BonusApplyStrategy} interface
 * and provides the specific strategy for applying bonuses when a stamp card is present.
 */
public class StampCardBonusApplyStrategy implements BonusApplyStrategy {

    @Override
    public void applyBonus(Order order) {
        List<Product> canDiscountedBeverages = order.getStampCard().get().getCardProducts().stream()
                .filter(product -> ProductGroup.BEVERAGE == product.getProductGroup())
                .filter(product -> product.getBonus() != null && product.getBonus().isCostApplicable())
                .toList();

        if (canDiscountedBeverages.size() >= 5) {
            IntStream.range(1, canDiscountedBeverages.size())
                    .forEach(i -> {
                        Product product = canDiscountedBeverages.get(i);
                        if (i % 5 == 0) {
                            product.setBonus(BonusType.FREE);
                            product.setDiscountAmount(calculateDiscountAmount(product));
                        } else {
                            product.setBonus(BonusType.USED);
                        }
                    });
        }
    }
}