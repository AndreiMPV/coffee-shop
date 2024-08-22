package org.shop;

import org.shop.model.order.Order;
import org.shop.model.product.Product;
import org.shop.service.order.OrderService;
import org.shop.service.order.calculate.CalculateStrategyResolver;
import org.shop.service.product.ConsoleProductFactory;
import org.shop.service.receipt.DefaultReceiptPrintService;
import org.shop.service.receipt.ReceiptPrintService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.println("Please enter the products for your order, separated by commas (e.g., 'large coffee with extra milk, bacon roll, orange juice'):");
        String input = scanner.nextLine();
        scanner.close();

        List<Product> products = ConsoleProductFactory.produceProduct(input);

        CalculateStrategyResolver calculateStrategyResolver = new CalculateStrategyResolver();
        OrderService orderService = new OrderService(calculateStrategyResolver);
        ReceiptPrintService defaultReceiptPrintService = new DefaultReceiptPrintService(orderService);

        Order order = orderService.makeOrder(products);
        defaultReceiptPrintService.printReceipt(order, System.out);
    }
}