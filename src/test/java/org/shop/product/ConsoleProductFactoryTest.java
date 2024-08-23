package org.shop.product;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.shop.model.product.MainProduct;
import org.shop.service.product.ConsoleProductFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.shop.model.product.PortionSizeType.*;

class ConsoleProductFactoryTest {

    private static ConsoleProductFactory consoleProductFactory;

    @BeforeAll
    static void setUp() {
        consoleProductFactory = new ConsoleProductFactory();
    }

    @Test
    void testProduceProduct_withValidBaseProduct() {
        String productListInput = "Large Coffee";
        List<MainProduct> products = consoleProductFactory.produceProduct(productListInput);
        assertEquals(1, products.size());
        assertEquals("coffee", products.get(0).getProductName().toLowerCase());
        assertEquals(LARGE, products.get(0).getPortionSizeType());
    }

    @Test
    void testProduceProduct_withValidBaseProductAndExtras() {
        String productListInput = "Medium Coffee with Extra milk,Small Coffee with Foamed milk";
        List<MainProduct> products = consoleProductFactory.produceProduct(productListInput);
        assertEquals(2, products.size());

        MainProduct firstProduct = products.get(0);
        assertEquals("coffee", firstProduct.getProductName().toLowerCase());
        assertEquals(MEDIUM, firstProduct.getPortionSizeType());
        assertEquals(1, firstProduct.getExtraProducts().size());
        assertEquals("Extra milk", firstProduct.getExtraProducts().get(0).getProductName());

        MainProduct secondProduct = products.get(1);
        assertEquals("coffee", secondProduct.getProductName().toLowerCase());
        assertEquals(SMALL, secondProduct.getPortionSizeType());
        assertEquals(1, secondProduct.getExtraProducts().size());
        assertEquals("Foamed milk", secondProduct.getExtraProducts().get(0).getProductName());
    }

    @Test
    void testProduceProduct_withInvalidBaseProduct() {
        String productListInput = "Large Tea";
        List<MainProduct> products = consoleProductFactory.produceProduct(productListInput);
        assertEquals(0, products.size());
    }

    @Test
    void testProduceProduct_withUnknownExtra() {
        String productListInput = "Large Coffee with Honey";
        List<MainProduct> products = consoleProductFactory.produceProduct(productListInput);
        assertEquals(1, products.size());
        assertTrue(products.get(0).getExtraProducts().isEmpty());
    }

    @Test
    void testProduceProduct_withMultipleExtras() {
        String productListInput = "Large Coffee with Extra milk with Special roast";
        List<MainProduct> products = consoleProductFactory.produceProduct(productListInput);
        assertEquals(1, products.size());
        MainProduct product = products.get(0);
        assertEquals(2, product.getExtraProducts().size());
        assertEquals("Extra milk", product.getExtraProducts().get(0).getProductName());
        assertEquals("Special roast", product.getExtraProducts().get(1).getProductName());
    }
}
