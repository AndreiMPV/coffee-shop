package org.shop.service;

import org.shop.model.product.Product;
import org.shop.model.product.main.Coffee;
import org.shop.service.product.ProductFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleOrderInputToProductsConverter {
    private static final String BASE_PRODUCT_WITH_SIZE_PATTERN = Arrays.stream(Coffee.CoffeeSizeType.values())
                                .map(Coffee.CoffeeSizeType::name) // Get the name of each enum value
                                .map(String::toLowerCase) // Convert to uppercase if necessary
                                .collect(Collectors.joining("|", "(", ")"));
    private static final String EXTRA_REGEXP = String.format("(?:%s\\s*)", BASE_PRODUCT_WITH_SIZE_PATTERN);

    public static final int BASE_PRODUCT_INDEX = 0;
    public static final String EXTRA_DELIMITER = "with";
    public static final String EMPTY_STRING = "";
    public static final String SPACE_STRING = " ";

    public List<Product> convert(String productListInput) {
        String[] allProducts = productListInput.split(",");
        return Arrays.stream(allProducts)
                .map(product -> {
                    String[] fullProductDesc = product.split(EXTRA_DELIMITER);
                    String baseProductWithSize = fullProductDesc[BASE_PRODUCT_INDEX];
                    String baseProductDesc = extractBaseProductDesc(baseProductWithSize);
                    String productSizeDesc = baseProductWithSize.replace(baseProductDesc, EMPTY_STRING);
                    List<String> extras = extractExtras(fullProductDesc);
                    return ProductFactory.produceProduct(baseProductDesc, productSizeDesc, extras);
                })
                .toList();
    }

    private static List<String> extractExtras(String[] fullProductDesc) {
        return Arrays.stream(fullProductDesc).skip(1).toList();
    }

    private static String extractBaseProductDesc(String baseProductWithSize) {
        return Arrays.stream(baseProductWithSize.split(EXTRA_REGEXP))
                        .filter(prName -> prName !=null && !prName.isBlank())
                        .findFirst().get();
    }

}
