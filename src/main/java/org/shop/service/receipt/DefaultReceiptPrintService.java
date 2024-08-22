package org.shop.service.receipt;

import org.shop.model.order.Order;
import org.shop.model.product.ExtraProduct;
import org.shop.model.product.MainProduct;
import org.shop.service.order.OrderService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * The {@code DefaultReceiptPrintService} class implements the {@link ReceiptPrintService} interface
 * and provides functionality for generating and printing receipts.
 * <p>
 * This implementation formats receipts with a structured layout, including product descriptions,
 * quantities, costs, and total amounts. The receipt can be printed to a {@link OutputStream} or
 * returned as a formatted string.
 * </p>
 */
public class DefaultReceiptPrintService implements ReceiptPrintService {
    private static final int DESCRIPTION_AMOUNT_LINE_LENGTH = 50;
    private static final int DESCRIPTION_LENGTH = 40;
    private static final int TOTAL_LENGTH = 14;

    private static final String TOTAL_SEPARATOR = "-".repeat(DESCRIPTION_AMOUNT_LINE_LENGTH);
    private static final String BOARDER = "-".repeat(DESCRIPTION_AMOUNT_LINE_LENGTH);

    /**
     * Generates a formatted receipt as a string for the given {@link Order}.
     * <p>
     * The receipt includes the shop name, a list of products with quantities and costs,
     * any discounts, and the total cost. The receipt is formatted with borders and centered text.
     * </p>
     *
     * @param order the {@link Order} to print
     * @return a formatted receipt string
     */
    @Override
    public String printReceipt(Order order) {
        StringBuilder sb = new StringBuilder();

        sb.append(BOARDER)
                .append(System.lineSeparator())
                .append(centerText("_Receipt_"))
                .append(System.lineSeparator())
                .append(centerText("_Coffee_Shop_"))
                .append(System.lineSeparator())
                .append(BOARDER)
                .append(System.lineSeparator());

        addProductLines(order, sb);

        sb.append(TOTAL_SEPARATOR)
                .append(System.lineSeparator())
                .append(formatTotal(order.getTotalCost()))
                .append(System.lineSeparator())
                .append(BOARDER)
                .append(System.lineSeparator())
                .append(centerText("_Contact:+48999922233_"))
                .append(System.lineSeparator())
                .append(BOARDER)
                .append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public void printReceipt(Order order, OutputStream outputStream) {
        try {
            outputStream.write(printReceipt(order).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Can't print receipt", e);
        }
    }

    private String rightFixedLengthString(String string) {
        return String.format("%1$" + 8 + "s", string);
    }

    private String leftFixedLengthString(String string) {
        return String.format("%-" + DESCRIPTION_LENGTH + "s", string);
    }

    private String formatDiscount(BigDecimal amount) {
        return String.format("%-" + (DESCRIPTION_AMOUNT_LINE_LENGTH - 10) + "s%10.2f","", amount);
    }

    private String formatTotal(BigDecimal amount) {
        return String.format("%-" + (DESCRIPTION_AMOUNT_LINE_LENGTH - TOTAL_LENGTH) + "s%10.2f %s", "Total: ", amount, "CHF");
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
                       .append(leftFixedLengthString(count + " x " + buildProductDescription(product)))
                       .append(" ")
                       .append(rightFixedLengthString(product.getInitialTotalCost().multiply(BigDecimal.valueOf(count)).toString()));
                    if (product.getTotalDiscount().compareTo(BigDecimal.ZERO) > 0) {
                        sb.append(System.lineSeparator())
                           .append(formatDiscount(product.getTotalDiscount().multiply(BigDecimal.valueOf(count)).negate()));
                    }
                    sb.append(System.lineSeparator());
                });
    }

    public String buildProductDescription(MainProduct product) {
        return Optional.ofNullable(product.getPortionSizeType())
                        .map(MainProduct.PortionSizeType::name)
                        .map(String::toLowerCase)
                        .map(name -> name + " ")
                        .orElse("") +
                    product.getProductName().toLowerCase() + " " +
                Optional.ofNullable(product.getPortionVolumeType())
                        .map(MainProduct.PortionVolumeType::name)
                        .map(String::toLowerCase).orElse("") +
                product.getExtraProducts().stream().map(ExtraProduct::getProductName)
                        .collect(Collectors.joining(" with "));
    }
}
