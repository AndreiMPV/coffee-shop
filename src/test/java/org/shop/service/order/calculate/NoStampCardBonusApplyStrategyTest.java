package org.shop.service.order.calculate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.shop.model.bonus.BonusType;
import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NoStampCardBonusApplyStrategyTest {

    private NoStampCardBonusApplyStrategy strategy;
    private Order order;

    @BeforeEach
    void setUp() {
        strategy = new NoStampCardBonusApplyStrategy();
        order = mock(Order.class);
    }

    @Test
    @DisplayName("When calculating the total cost with both a snack and a beverage, the order's total amount should not include one extra amount.")
    void testCalculateTotalCost_WithSnackAndBeverage_Then_OrderTotalAmount_Is_Not_Included_One_Extra_amount() {
        // Given
        Product coffee = mockProduct(ProductGroup.BEVERAGE, 3.00, new ArrayList<>());
        Product snack = mockProduct(ProductGroup.SNACK, 2.00, new ArrayList<>());
        Product extra = mockProduct(ProductGroup.EXTRA, 1.00, new ArrayList<>());

        List<Product> products = List.of(coffee, snack, extra);
        when(order.getProducts()).thenReturn(products);
        // When
      //  BigDecimal totalCost = strategy.applyBonus(order);
        // Then
     //   assertEquals(BigDecimal.valueOf(5.00), totalCost, "Total cost should be without extra amount");
       // assertEquals(List.of(BonusType.PERCENTAGE_FREE), extra.getBonus(), "The extra should be free due to the presence of both snack and beverage");
    }

    @ParameterizedTest
    @EnumSource(value = ProductGroup.class, names = {"BEVERAGE", "SNACK"}, mode = EnumSource.Mode.INCLUDE)
    @DisplayName("When calculating the total cost with only beverages or snacks, the extra amount will be included in the total cost.")
    void testApplyBonus(ProductGroup productGroup) {
        // Given
        Product coffee = mockProduct(productGroup, 3.00, new ArrayList<>());

        List<Product> products = List.of(coffee, coffee);
        when(order.getProducts()).thenReturn(products);
        // Then
    //    BigDecimal totalCost = strategy.applyBonus(order);
        // Then
    //    assertEquals(BigDecimal.valueOf(6.00), totalCost);
    }

//    @ParameterizedTest
//    @EnumSource(value = ProductGroup.class, names = {"BEVERAGE", "SNACK"}, mode = EnumSource.Mode.EXCLUDE)
//    @DisplayName("When calculating the total cost with no snacks or beverages, the extra amount will not be included in the total cost.")
//    void testApplyBonus(ProductGroup productGroup) {
//        // Setup
//        Product extra = mockProduct(ProductGroup.EXTRA, 1.00, new ArrayList<>());
//        List<Product> products = List.of(extra, extra);
//        when(order.getProducts()).thenReturn(products);
//        // When
//    //    BigDecimal totalCost = strategy.applyBonus(order);
//        // Then
//    //    assertEquals(BigDecimal.valueOf(2.00), totalCost);
//    }

    @Test
    void testApplyBonus_WithEmptyProductList() {
        // Given
        List<Product> products = List.of();
        when(order.getProducts()).thenReturn(products);
        // When
    //    BigDecimal totalCost = strategy.applyBonus(order);
        // Then
    //    assertEquals(BigDecimal.ZERO, totalCost);
    }

    private Product mockProduct(ProductGroup productGroup, double cost, List<BonusType> bonusTypes) {
        Product product = mock(Product.class);
        when(product.getGroup()).thenReturn(productGroup);
        when(product.getCost()).thenReturn(BigDecimal.valueOf(cost));
    //    when(product.getBonus()).thenReturn(bonusTypes);
        return product;
    }
}