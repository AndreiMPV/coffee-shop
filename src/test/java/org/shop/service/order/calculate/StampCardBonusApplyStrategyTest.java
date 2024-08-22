package org.shop.service.order.calculate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.shop.model.bonus.BonusType;
import org.shop.model.bonus.StampCard;
import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StampCardBonusApplyStrategyTest {

    private StampCardBonusApplyStrategy strategy;
    private Order order;
    private StampCard stampCard;

    @BeforeEach
    void setUp() {
        strategy = new StampCardBonusApplyStrategy();
        order = mock(Order.class);
        stampCard = mock(StampCard.class);
    }

    @Test
    @DisplayName("When calculating the total cost with fewer than five beverages, the order's total cost will include the costs of the beverages.")
    void testApplyBonus_Will_Include_Beverage_Costs() {
        // Setup
        Product coffee = mockProduct(ProductGroup.BEVERAGE, 3.00, new ArrayList<>());
        Product coffee1 = mockProduct(ProductGroup.BEVERAGE, 10.00, new ArrayList<>());
        Product snak = mockProduct(ProductGroup.SNACK, 3.00, new ArrayList<>());

        List<Product> products = List.of(coffee, coffee1, snak);
        when(order.getProducts()).thenReturn(products);
        when(order.getStampCard()).thenReturn(Optional.of(stampCard));
        when(stampCard.getCardProducts()).thenReturn(List.of(coffee, coffee1, snak));

        // Execution
        strategy.applyBonus(order);

        // Assertion
       // assertEquals(BigDecimal.valueOf(16.00), totalCost);
    }

    @Test
    @DisplayName("When calculating the total cost with more than five beverages, the cost of one beverage will be excluded from the order's total cost.")
    void testApplyBonus() {
        // Setup
        Product coffee = mockProduct(ProductGroup.BEVERAGE, 3.00, new ArrayList<>());
        Product coffee1 = mockProduct(ProductGroup.BEVERAGE, 10.00, new ArrayList<>());
        Product coffee2 = mockProduct(ProductGroup.BEVERAGE, 5.00, new ArrayList<>());
        Product coffee3 = mockProduct(ProductGroup.BEVERAGE, 10.00, new ArrayList<>());
        Product coffee4 = mockProduct(ProductGroup.BEVERAGE, 10.00, new ArrayList<>());
        Product coffee5 = mockProduct(ProductGroup.BEVERAGE, 10.00, new ArrayList<>());
        Product snak = mockProduct(ProductGroup.SNACK, 20.00, new ArrayList<>());

        List<Product> cardProducts = List.of(snak, coffee, coffee1, coffee2, coffee3, coffee4, coffee5);
       // when(coffee.getBonus()).thenReturn(new ArrayList<>());

        List<Product> products = List.of(snak, coffee, coffee1, coffee2, coffee3, coffee4, coffee5);
        when(order.getProducts()).thenReturn(products);
        when(order.getStampCard()).thenReturn(Optional.of(stampCard));
        when(stampCard.getCardProducts()).thenReturn(cardProducts);

        // Execution
      //  BigDecimal totalCost = strategy.applyBonus(order);

        // Assertion
     //   assertEquals(BigDecimal.valueOf(58.00), totalCost);
    }

    private Product mockProduct(ProductGroup productGroup, double cost, List<BonusType> bonusTypes) {
        Product product = mock(Product.class);
        when(product.getProductGroup()).thenReturn(productGroup);
        when(product.getInitialCost()).thenReturn(BigDecimal.valueOf(cost));
      //  when(product.getBonus()).thenReturn(bonusTypes);
        return product;
    }
}
