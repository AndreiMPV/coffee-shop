package org.shop.order.calculate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.shop.model.bonus.BonusType;
import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;
import org.shop.model.product.beverage.Coffee;
import org.shop.model.product.extra.Extra;
import org.shop.service.order.calculate.NoStampCardCostCalculationStrategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NoStampCardCostCalculationStrategyTest {

    private NoStampCardCostCalculationStrategy strategy;
    private Order order;

    @BeforeEach
    void setUp() {
        strategy = new NoStampCardCostCalculationStrategy();
        order = mock(Order.class);
    }

    @Test
    @DisplayName("When calculating the total cost with both a snack and a beverage, the order's total amount should not include one extra amount.")
    void testCalculateTotalCost_WithSnackAndBeverage_Then_OrderTotalAmount_Is_Not_Included_One_Extra_amount() {
        // Given
        Product coffee = mockProduct(ProductGroup.BEVERAGE, 3.00, new ArrayList<>());
        Product snack = mockProduct(ProductGroup.SNAK, 2.00, new ArrayList<>());
        Product extra = mockProduct(ProductGroup.EXTRA, 1.00, new ArrayList<>());

        List<Product> products = List.of(coffee, snack, extra);
        when(order.getProducts()).thenReturn(products);
        // When
        BigDecimal totalCost = strategy.calculateTotalCost(order);
        // Then
        assertEquals(BigDecimal.valueOf(5.00), totalCost, "Total cost should be without extra amount");
        assertEquals(List.of(BonusType.FREE), extra.getBonusesApplied(), "The extra should be free due to the presence of both snack and beverage");
    }

    @ParameterizedTest
    @EnumSource(value = ProductGroup.class, names = {"BEVERAGE", "SNAK"}, mode = EnumSource.Mode.INCLUDE)
    @DisplayName("When calculating the total cost with only beverages or snacks, the extra amount will be included in the total cost.")
    void testCalculateTotalCost_WithOnlyBeverages_Or_Snak_Then_Extra_Amount_Will_Be_Included_In_Total_Cost(ProductGroup productGroup) {
        // Given
        Product coffee = mockProduct(productGroup, 3.00, new ArrayList<>());

        List<Product> products = List.of(coffee, coffee);
        when(order.getProducts()).thenReturn(products);
        // Then
        BigDecimal totalCost = strategy.calculateTotalCost(order);
        // Then
        assertEquals(BigDecimal.valueOf(6.00), totalCost);
    }

    @ParameterizedTest
    @EnumSource(value = ProductGroup.class, names = {"BEVERAGE", "SNAK"}, mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("When calculating the total cost with no snacks or beverages, the extra amount will not be included in the total cost.")
    void testCalculateTotalCost_NoSnackOrBeverage_Then_Extra_Amount_Will_Be_Included_In_Total_Cost(ProductGroup productGroup) {
        // Setup
        Product extra = mockProduct(ProductGroup.EXTRA, 1.00, new ArrayList<>());
        List<Product> products = List.of(extra, extra);
        when(order.getProducts()).thenReturn(products);
        // When
        BigDecimal totalCost = strategy.calculateTotalCost(order);
        // Then
        assertEquals(BigDecimal.valueOf(2.00), totalCost);
    }

    @Test
    void testCalculateTotalCost_WithEmptyProductList() {
        // Given
        List<Product> products = List.of();
        when(order.getProducts()).thenReturn(products);
        // When
        BigDecimal totalCost = strategy.calculateTotalCost(order);
        // Then
        assertEquals(BigDecimal.ZERO, totalCost);
    }

    private Product mockProduct(ProductGroup productGroup, double cost, List<BonusType> bonusTypes) {
        Product product = mock(Product.class);
        when(product.getGroup()).thenReturn(productGroup);
        when(product.getCost()).thenReturn(BigDecimal.valueOf(cost));
        when(product.getBonusesApplied()).thenReturn(bonusTypes);
        return product;
    }
}