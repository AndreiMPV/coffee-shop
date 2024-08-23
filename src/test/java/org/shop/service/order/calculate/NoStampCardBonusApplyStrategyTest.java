package org.shop.service.order.calculate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.shop.model.bonus.BonusType;
import org.shop.model.order.Order;
import org.shop.model.product.ExtraProduct;
import org.shop.model.product.MainProduct;
import org.shop.model.product.ProductGroup;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NoStampCardBonusApplyStrategyTest {

    private NoStampCardBonusApplyStrategy strategy = new NoStampCardBonusApplyStrategy();

    @Test
    @DisplayName("When calculating the total cost with both a snack and a beverage, the order's total amount should not include one extra amount.")
    void testCalculateTotalCost_WithSnackAndBeverage_Then_OrderTotalAmount_Is_Not_Included_One_Extra_amount() {
        // Given
        MainProduct coffee = mockProduct(ProductGroup.BEVERAGE, 3.00, null);
        ExtraProduct extraProduct = new ExtraProduct("extra milk", 0.5);
        when(coffee.getExtraProducts()).thenReturn(List.of(extraProduct));
        MainProduct snack = mockProduct(ProductGroup.SNACK, 2.00, null);

        List<MainProduct> products = List.of(coffee, snack);
        Order order = new Order(products, null);
        // When
        strategy.applyBonus(order);
       // Then
        assertEquals(BigDecimal.valueOf(5.00), order.getTotalCost(), "Total cost should be without extra amount");
        assertEquals(BonusType.FREE, extraProduct.getBonus(), "The extra should be free due to the presence of both snack and beverage");
    }

    @ParameterizedTest
    @EnumSource(value = ProductGroup.class, names = {"BEVERAGE", "SNACK"}, mode = EnumSource.Mode.INCLUDE)
    @DisplayName("When calculating the total cost with only beverages or snacks, the extra amount will be included in the total cost.")
    void testCalculateTotalCost_includesExtraAmounts_When_OnlyBeveragesOrSnacksAreIncluded(ProductGroup productGroup) {
        // Given
        MainProduct coffee = mockProduct(productGroup, 3.00, null);

        List<MainProduct> products = List.of(coffee, coffee);
        Order order = new Order(products, null);
        // When
        strategy.applyBonus(order);
        // Then
        assertEquals(BigDecimal.valueOf(6.00), order.getTotalCost());
    }

    @Test
    void testApplyBonus_WithEmptyProductList() {
        // Given
        List<MainProduct> products = List.of();
        Order order = new Order(products, null);
        //When
        strategy.applyBonus(order);
        //Then
        assertEquals(BigDecimal.ZERO, order.getTotalCost());
    }

    private MainProduct mockProduct(ProductGroup productGroup, double initialCost, BonusType bonusType) {
        MainProduct product = mock(MainProduct.class);
        when(product.getProductGroup()).thenReturn(productGroup);
        when(product.getInitialCost()).thenReturn(BigDecimal.valueOf(initialCost));
        when(product.getInitialTotalCost()).thenReturn(BigDecimal.valueOf(initialCost));
        when(product.getTotalDiscount()).thenReturn(BigDecimal.ZERO);
        when(product.getBonus()).thenReturn(bonusType);
        return product;
    }
}