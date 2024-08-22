package org.shop.service.receipt;

import org.shop.model.order.Order;

import java.io.OutputStream;

public interface ReceiptPrintService {
    String printReceipt(Order order);
    void printReceipt(Order order, OutputStream outputStream);
}
