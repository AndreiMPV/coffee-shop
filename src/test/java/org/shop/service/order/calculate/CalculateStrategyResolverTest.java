package org.shop.service.order.calculate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shop.model.bonus.StampCard;
import org.shop.model.order.Order;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculateStrategyResolverTest {
    private CalculateStrategyResolver resolver;
    private StampCard stampCard;

    @BeforeEach
    void setUp() {
        resolver = new CalculateStrategyResolver();
        stampCard = mock(StampCard.class);
    }

    @Test
    void testResolveWithStampCard() {
        //Given
        var orderWithStampCard = mock(Order.class);
        when(orderWithStampCard.getStampCard()).thenReturn(Optional.of(stampCard));
        // When
        CostCalculationStrategy strategy = resolver.resolve(orderWithStampCard);
        // Then
        assertEquals(StampCardCostCalculationStrategy.class, strategy.getClass());
    }

    @Test
    void testResolveWithoutStampCard() {
        //Given
        var orderWithoutStampCard = mock(Order.class);
        when(orderWithoutStampCard.getStampCard()).thenReturn(Optional.empty());
        // When
        CostCalculationStrategy strategy = resolver.resolve(orderWithoutStampCard);

        // Then
        assertEquals(NoStampCardCostCalculationStrategy.class, strategy.getClass());
    }
}