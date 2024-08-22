package org.shop.service.receipt;

import org.shop.model.order.Order;
import org.shop.service.order.OrderService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class DefaultReceiptPrintService implements ReceiptPrintService {
    private static final int DESCRIPTION_AMOUNT_LINE_LENGTH = 50;
    private static final int DESCRIPTION_LENGTH = 40;
    private static final int TOTAL_LENGTH = 10;

    private static final String TOTAL_SEPARATOR = "-".repeat(DESCRIPTION_AMOUNT_LINE_LENGTH);
    private static final String BOARDER = "-".repeat(DESCRIPTION_AMOUNT_LINE_LENGTH);

    private final OrderService orderService;


    public DefaultReceiptPrintService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String printInvoice(Order order) {
        StringBuilder sb = new StringBuilder();

        sb.append(buildBorder())
                .append(System.lineSeparator())
                .append(centerText("_Receipt_"))
                .append(System.lineSeparator())
                .append(centerText("_Coffee_Shop_"))
                .append(System.lineSeparator())
                .append(buildBorder())
                .append(System.lineSeparator());

        addProductLines(order, sb);

        sb.append(TOTAL_SEPARATOR)
                .append(System.lineSeparator())
                .append(formatTotal(orderService.calculateTotal(order)))
                .append(System.lineSeparator())
                .append(buildBorder())
                .append(System.lineSeparator())
                .append(centerText("_Contact:+48999922233_"))
                .append(System.lineSeparator())
                .append(buildBorder())
                .append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public void printInvoice(Order order, OutputStream outputStream) {
        try {
            outputStream.write(printInvoice(order).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Can't print receipt", e);
        }
    }

    private String buildBorder() {
        return BOARDER;
    }

    private String rightFixedLengthString(String string) {
        return String.format("%1$"+ 8 + "s", string);
    }

    private String leftFixedLengthString(String string) {
        return String.format("%-" + DESCRIPTION_LENGTH + "s", string);
    }

    private String formatTotal(BigDecimal amount) {
        return String.format("%-" + (DESCRIPTION_AMOUNT_LINE_LENGTH - TOTAL_LENGTH) + "s%10.2f", "Total: ", amount);
    }

    private String centerText(String text) {
        int paddingSize = (DESCRIPTION_AMOUNT_LINE_LENGTH - text.length()) / 2;
        return String.format("%" + paddingSize + "s%s%" + paddingSize + "s", "", text, "")
                .replace(' ', '#');
    }

    private void addProductLines(Order order, StringBuilder sb) {
        order.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()))
                .forEach((product, count) -> {
                    sb.append(" ")
                            .append(leftFixedLengthString(count + " x " + product.getDescription()))
                            .append(" ")
                            .append(rightFixedLengthString(product.getCost().multiply(BigDecimal.valueOf(count)).toString()))
                            .append(System.lineSeparator());
                });
    }
}
