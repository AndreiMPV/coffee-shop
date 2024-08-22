package org.shop;

import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.model.product.beverage.Coffee;
import org.shop.model.product.beverage.OrangeFreshJuice;
import org.shop.model.product.extra.ExtraMilk;
import org.shop.model.product.extra.SpecialRoast;
import org.shop.model.product.snak.BaconRoll;
import org.shop.service.order.OrderService;
import org.shop.service.order.calculate.CalculateStrategyResolver;
import org.shop.service.receipt.DefaultReceiptPrintService;
import org.shop.service.receipt.ReceiptPrintService;

import java.util.List;

import static org.shop.service.product.ProductFactory.*;

public class Main {
    public static void main(String[] args) {
        Product largeCoffee = produceProduct(Coffee.class, Coffee.CoffeeSizeType.LARGE, 3.55);
        Product coffeeWithMilk = produceProductWithExtra(ExtraMilk.class, largeCoffee, 0.32);
        Product smallCoffee = produceProduct(Coffee.class, Coffee.CoffeeSizeType.SMALL, 2.55);
        Product smallCoffeeWithMilk = produceProductWithExtra(SpecialRoast.class, smallCoffee, 0.95);
        Product baconRoll = new BaconRoll( 4.53);
        Product juice = produceProduct(OrangeFreshJuice.class, OrangeFreshJuice.JuiceSizeType.L_0_25, 3.95);

        CalculateStrategyResolver calculateStrategyResolver = new CalculateStrategyResolver();
        OrderService orderService = new OrderService(calculateStrategyResolver);
        ReceiptPrintService defaultReceiptPrintService = new DefaultReceiptPrintService(orderService);

        Order order = orderService.makeOrder(List.of(coffeeWithMilk, smallCoffeeWithMilk, juice, baconRoll));
        defaultReceiptPrintService.printOrder(order, System.out);
    }
}