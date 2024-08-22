package org.shop.service.receipt;

import org.shop.model.order.Order;

import java.io.OutputStream;

public interface ReceiptPrintService {
    String printInvoice(Order order);
    void printInvoice(Order order, OutputStream outputStream);
}
