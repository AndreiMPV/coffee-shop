package org.shop.service.receipt;

import org.junit.jupiter.api.Test;
import org.shop.model.bonus.BonusType;
import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.model.product.ProductGroup;
import org.shop.model.product.main.Coffee;
import org.shop.service.order.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DefaultReceiptPrintServiceTest {

//    @Test
//    void testPrintOrderReceipt() {
//        // Arrange
//        OrderService orderService = mock(OrderService.class);
//        DefaultReceiptPrintService receiptPrintService = new DefaultReceiptPrintService(orderService);
//
//        Product snack = mockProduct(ProductGroup.SNACK, 4.00, new ArrayList<>());
//        when(snack.getDescription()).thenReturn("Bacon Roll");
//
//        Product coffee = mockCoffee(ProductGroup.BEVERAGE, 5.00, new ArrayList<>());
//        when(coffee.getDescription()).thenReturn("Coffee medium");
//
//
//        Order order = mock(Order.class);
//        when(order.getProducts()).thenReturn(List.of(snack, coffee, coffee));
//        when(orderService.calculateTotal(order)).thenReturn(BigDecimal.valueOf(14.00));
//
//        String expRoll = "1 x Bacon Roll                                4.0";
//        String expCoffee = "2 x Coffee medium                            10.0";
//        String expTotal = "Total:                                       14.00";
//        // Act
//        String actualReceipt = receiptPrintService.printOrder(order);
//
//        // Assert
//        assertTrue(actualReceipt.contains(expRoll));
//        assertTrue(actualReceipt.contains(expCoffee));
//        assertTrue(actualReceipt.contains(expTotal));
//    }
//
//    private Product mockProduct(ProductGroup productGroup, double cost, List<BonusType> bonusTypes) {
//        Product product = mock(Product.class);
//        when(product.getGroup()).thenReturn(productGroup);
//        when(product.getCost()).thenReturn(BigDecimal.valueOf(cost));
//        when(product.getBonuses()).thenReturn(bonusTypes);
//        return product;
//    }
//
//    private Coffee mockCoffee(ProductGroup productGroup, double cost, List<BonusType> bonusTypes) {
//        Coffee coffee = mock(Coffee.class);
//        when(coffee.getGroup()).thenReturn(productGroup);
//        when(coffee.getCost()).thenReturn(BigDecimal.valueOf(cost));
//        when(coffee.getBonuses()).thenReturn(bonusTypes);
//        return coffee;
//    }
}