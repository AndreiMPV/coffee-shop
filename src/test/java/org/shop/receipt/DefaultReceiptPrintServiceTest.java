package org.shop.receipt;

import org.shop.model.bonus.BonusType;
import org.shop.model.order.Order;
import org.shop.model.product.MainProduct;
import org.shop.model.product.ProductGroup;
import org.shop.service.receipt.DefaultReceiptPrintService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultReceiptPrintServiceTest {

    @Test
    void testPrintOrderReceipt() {
        // Arrange
        DefaultReceiptPrintService receiptPrintService = new DefaultReceiptPrintService();

        MainProduct snack = mockProduct(ProductGroup.SNACK, 4.00, 0, null);
        when(snack.getProductName()).thenReturn("Bacon Roll");

        MainProduct coffee = mockProduct(ProductGroup.BEVERAGE, 4.00, 1.00, BonusType.FREE);
        when(coffee.getProductName()).thenReturn("Coffee medium");


        Order order = new Order(List.of(snack, coffee, coffee), null);

        String expRoll = " 1 x bacon roll                                4.0";
        String expCoffee = " 2 x coffee medium                             8.0";
        String discountCoffee = "                                             -2.00";
        String expTotal = "Total:                                   10.00 CHF";
        // Act
        String actualReceipt = receiptPrintService.printReceipt(order);

        // Assert
        assertTrue(actualReceipt.contains(expRoll));
        assertTrue(actualReceipt.contains(expCoffee));
        assertTrue(actualReceipt.contains(discountCoffee));
        assertTrue(actualReceipt.contains(expTotal));
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