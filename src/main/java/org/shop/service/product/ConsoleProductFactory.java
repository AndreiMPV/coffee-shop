package org.shop.service.product;

import org.shop.model.product.ExtraProduct;
import org.shop.model.product.MainProduct;
import org.shop.model.product.MainProduct.PortionSizeType;
import org.shop.model.product.Product;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.shop.model.product.MainProduct.PortionSizeType.*;
import static org.shop.model.product.MainProduct.PortionVolumeType.L_0_25;
import static org.shop.model.product.ProductGroup.*;

public class ConsoleProductFactory {
    public static final int BASE_PRODUCT_INDEX = 0;
    public static final String EXTRA_DELIMITER = "with";

    private static final Map<String, List<MainProduct>> BASE_PRODUCT_MAP = Stream.of(
            new MainProduct("Coffee", BEVERAGE, LARGE, null, 3.55),
            new MainProduct("Coffee", BEVERAGE, MEDIUM, null, 3.05),
            new MainProduct("Coffee", BEVERAGE, SMALL, null, 88),
            new MainProduct("Bacon Roll", SNACK, null, null, 4.53),
            new MainProduct("Orange juice", BEVERAGE, null, L_0_25, 3.95),
            new MainProduct("Freshly squeezed orange juice", BEVERAGE, null, L_0_25, 3.95)
            ).collect(Collectors.groupingBy(ConsoleProductFactory::buildBaseProductKey));

    private static final Map<String, List<ExtraProduct>> EXTRA_PRODUCT_MAP = Stream.of(
            new ExtraProduct("Extra milk", 0.32),
            new ExtraProduct("Foamed milk", 0.51),
            new ExtraProduct("Special roast", 0.95)
            ).collect(Collectors.groupingBy(ConsoleProductFactory::buildExtraProductKey));



    public static String buildBaseProductKey(MainProduct mainProduct) {
        return normalizedName(Optional.ofNullable(mainProduct.getPortionSizeType())
                                        .map(PortionSizeType::name).orElse("") +
                                        mainProduct.getProductName());
    }

    public static String buildExtraProductKey(ExtraProduct baseMainProduct) {
        return normalizedName(baseMainProduct.getProductName());
    }

    public static List<MainProduct> produceProduct(String productListInput) {
        String[] allProducts = productListInput.split(",");
        return Arrays.stream(allProducts)
                .map(product -> {
                    String[] fullProductDesc = product.split(EXTRA_DELIMITER);
                    String baseProductWithSize = fullProductDesc[BASE_PRODUCT_INDEX];
                    List<String> extras = extractExtras(fullProductDesc);
                    return ConsoleProductFactory.createProduct(baseProductWithSize, extras);
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private static Optional<MainProduct> createProduct(String baseProductDescription, List<String> extraDescriptions) {
        Optional<MainProduct> baseMainProductOpt = Optional.ofNullable(BASE_PRODUCT_MAP.get(normalizedName(baseProductDescription)))
                .orElse(Collections.emptyList())
                .stream()
                .findFirst();
                if (baseMainProductOpt.isPresent()) {
                    MainProduct mainProduct = baseMainProductOpt.get().clone();
                    extraDescriptions.forEach(extraDesc ->
                            Optional.ofNullable(EXTRA_PRODUCT_MAP.get(normalizedName(extraDesc)))
                                    .orElse(Collections.emptyList())
                                    .stream()
                                    .findFirst()
                                    .ifPresent(mainProduct::addExtraProduct)
                        );
                    return Optional.ofNullable(mainProduct);
                } else {
                    System.out.println("Can't create product : " + baseProductDescription);
                }
        return Optional.empty();
    }


    private static List<String> extractExtras(String[] fullProductDesc) {
        return Arrays.stream(fullProductDesc).skip(1).toList();
    }

    private static String normalizedName(String name) {
        return  name.toLowerCase().replace(" ","");
    }
}
