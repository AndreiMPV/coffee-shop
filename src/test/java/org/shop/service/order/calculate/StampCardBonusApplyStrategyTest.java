package org.shop.service.order.calculate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.shop.model.bonus.BonusType;
import org.shop.model.bonus.StampCard;
import org.shop.model.order.Order;
import org.shop.model.product.MainProduct;
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
        MainProduct coffee = mockProduct(ProductGroup.BEVERAGE, 3.00, 0, null);
        MainProduct coffee1 = mockProduct(ProductGroup.BEVERAGE, 10.00,0, null);
        MainProduct snack = mockProduct(ProductGroup.SNACK, 3.00, 0,null);

        List<MainProduct> products = List.of(coffee, coffee1, snack);
        Order order = new Order(products, stampCard);
        when(stampCard.getCardProducts()).thenReturn(List.of(coffee, coffee1, snack));
        // When
        strategy.applyBonus(order);
        // Then
        assertEquals(BigDecimal.valueOf(16.00), order.getTotalCost());
    }

    @Test
    @DisplayName("If no card then skip logic")
    void testApplyBonus_If_Card_Is_Null_Then_Skip_Logic() {
        // Setup
        MainProduct coffee = mockProduct(ProductGroup.BEVERAGE, 3.00, 0, null);
        MainProduct coffee1 = mockProduct(ProductGroup.BEVERAGE, 10.00,0, null);
        MainProduct snack = mockProduct(ProductGroup.SNACK, 3.00, 0,null);

        List<MainProduct> products = List.of(coffee, coffee1, snack);
        Order order = new Order(products, null);
        // When
        strategy.applyBonus(order);
        // Then
        assertEquals(BigDecimal.valueOf(16.00), order.getTotalCost());
    }

    @Test
    @DisplayName("When calculating the total cost with more than five beverages, the cost of one beverage will be excluded from the order's total cost.")
    void testCalculateTotalCost_excludesCostOfOneBeverage_whenMoreThanFiveBeveragesIncluded() {
        // Setup
        MainProduct coffee = mockProduct(ProductGroup.BEVERAGE, 3.00, 0,null);
        MainProduct coffee1 = mockProduct(ProductGroup.BEVERAGE, 10.00,0, null);
        MainProduct coffee2 = mockProduct(ProductGroup.BEVERAGE, 5.00, 0,null);
        MainProduct coffee3 = mockProduct(ProductGroup.BEVERAGE, 10.00,0, null);
        MainProduct coffee4 = mockProduct(ProductGroup.BEVERAGE, 10.00,10, null);
        MainProduct coffee5 = mockProduct(ProductGroup.BEVERAGE, 10.00, 0,null);
        MainProduct snack = mockProduct(ProductGroup.SNACK, 20.00,0, null);

        List<MainProduct> cardProducts = List.of(snack, coffee, coffee1, coffee2, coffee3, coffee4, coffee5);

        List<MainProduct> products = List.of(snack, coffee, coffee1, coffee2, coffee3, coffee4, coffee5);
        when(stampCard.getCardProducts()).thenReturn(cardProducts);
         //When
        Order order = new Order(products, stampCard);
        strategy.applyBonus(order);
         //Then
        assertEquals(BigDecimal.valueOf(58.00), order.getTotalCost());
    }

    private MainProduct mockProduct(ProductGroup productGroup, double initialCost, double discount, BonusType bonusType) {
        MainProduct product = mock(MainProduct.class);
        when(product.getProductGroup()).thenReturn(productGroup);
        when(product.getInitialCost()).thenReturn(BigDecimal.valueOf(initialCost));
        when(product.getInitialTotalCost()).thenReturn(BigDecimal.valueOf(initialCost));
        when(product.getTotalDiscount()).thenReturn(BigDecimal.valueOf(discount));
        when(product.getBonus()).thenReturn(bonusType);
        return product;
    }
}
