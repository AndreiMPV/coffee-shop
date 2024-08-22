package org.shop.service.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shop.model.bonus.BonusType;
import org.shop.model.bonus.StampCard;
import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;
import org.shop.service.order.calculate.CalculateStrategyResolver;
import org.shop.service.order.calculate.BonusApplyStrategy;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderService orderService;
    private CalculateStrategyResolver calculateStrategyResolver;
    private StampCard stampCard;

    @BeforeEach
    void setUp() {
        calculateStrategyResolver = mock(CalculateStrategyResolver.class);
        orderService = new OrderService(calculateStrategyResolver);

        stampCard = mock(StampCard.class);
    }

    @Test
    void testMakeOrder_With_Stam_Card_Provided() {
        // Given
        Order order = mock(Order.class);

        when(order.getTotalCost(any())).thenReturn(BigDecimal.valueOf(100.00));
        Product product = mockProduct(ProductGroup.BEVERAGE, 3.00, BonusType.USED);
        List<Product> products = List.of(product);
        when(order.getProducts()).thenReturn(products);
        var strategy = mock(BonusApplyStrategy.class);
        when(calculateStrategyResolver.resolve(any(Order.class))).thenReturn(strategy);
        // When
        Order resultOrder = orderService.makeOrder(products, stampCard);
        // Then
        assertEquals(products, resultOrder.getProducts());
        assertEquals(stampCard, resultOrder.getStampCard().get());
        verify(calculateStrategyResolver, never()).resolve(any(Order.class));
    }

    @Test
    void testMakeOrder_Without_Stam_Card_Provided() {
        // Given
        Order order = mock(Order.class);

        when(order.getTotalCost(any())).thenReturn(BigDecimal.valueOf(100.00));
        Product product = mockProduct(ProductGroup.BEVERAGE, 3.00, BonusType.USED);
        List<Product> products = List.of(product);
        when(order.getProducts()).thenReturn(products);
        var strategy = mock(BonusApplyStrategy.class);
        when(calculateStrategyResolver.resolve(any(Order.class))).thenReturn(strategy);
        // When
        Order resultOrder = orderService.makeOrder(products);
        // Then
        assertEquals(products, resultOrder.getProducts());
        assertTrue(resultOrder.getStampCard().isEmpty());
        verify(calculateStrategyResolver, never()).resolve(any(Order.class));
    }

    @Test
    void orderShouldApplayBonusesAmount() {
        // Given
//        Order order = mock(Order.class);
//        var strategy = mock(BonusApplyStrategy.class);
//        when(calculateStrategyResolver.resolve(any(Order.class))).thenReturn(strategy);
//        when(orderService.applayBonuses(order)).thenReturn(BigDecimal.valueOf(150.00));
//        // When
//        BigDecimal total = orderService.applayBonuses(order);
//        // Then
//        assertEquals(BigDecimal.valueOf(150.00), total);
//        verify(order).getTotalCost(calculateStrategyResolver);
    }

    private Product mockProduct(ProductGroup productGroup, double cost, BonusType bonusType) {
        Product product = mock(Product.class);
//        when(product.getGroup()).thenReturn(productGroup);
//        when(product.getCost()).thenReturn(BigDecimal.valueOf(cost));
        when(product.getBonus()).thenReturn(bonusType);
        return product;
    }
}
