package org.shop.service.receipt;

import org.shop.model.order.Order;

import java.io.OutputStream;

public interface ReceiptPrintService {
    String printOrder(Order order);
    void printOrder(Order order, OutputStream outputStream);
}
