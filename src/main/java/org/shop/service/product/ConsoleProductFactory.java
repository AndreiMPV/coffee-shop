package org.shop.service.product;

import org.shop.model.product.BaseExtraProduct;
import org.shop.model.product.BaseMainProduct;
import org.shop.model.product.BaseMainProduct.PortionSizeType;
import org.shop.model.product.Product;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.shop.model.product.BaseMainProduct.PortionSizeType.*;
import static org.shop.model.product.BaseMainProduct.PortionVolumeType.L_0_25;
import static org.shop.model.product.ProductGroup.*;

public class ConsoleProductFactory {
    public static final int BASE_PRODUCT_INDEX = 0;
    public static final String EXTRA_DELIMITER = "with";

    private static final Map<String, List<BaseMainProduct>> BASE_PRODUCT_MAP = Stream.of(
            new BaseMainProduct("Coffee", BEVERAGE, LARGE, null, 3.55),
            new BaseMainProduct("Coffee", BEVERAGE, MEDIUM, null, 3.05),
            new BaseMainProduct("Coffee", BEVERAGE, SMALL, null, 88),
            new BaseMainProduct("Bacon Roll", SNACK, null, null, 4.53),
            new BaseMainProduct("Orange juice", BEVERAGE, null, L_0_25, 3.95),
            new BaseMainProduct("Freshly squeezed orange juice", BEVERAGE, null, L_0_25, 3.95)
            ).collect(Collectors.groupingBy(ConsoleProductFactory::buildBaseProductKey));

    private static final Map<String, List<BaseExtraProduct>> EXTRA_PRODUCT_MAP = Stream.of(
            new BaseExtraProduct("Extra milk", 0.32),
            new BaseExtraProduct("Foamed milk", 0.51),
            new BaseExtraProduct("Special roast", 0.95)
            ).collect(Collectors.groupingBy(ConsoleProductFactory::buildExtraProductKey));



    public static String buildBaseProductKey(BaseMainProduct baseMainProduct) {
        return normalizedName(Optional.ofNullable(baseMainProduct.getPortionSizeType())
                                        .map(PortionSizeType::name).orElse("") +
                                        baseMainProduct.getProductName());
    }

    public static String buildExtraProductKey(BaseExtraProduct baseMainProduct) {
        return normalizedName(baseMainProduct.getProductName());
    }

    public static List<Product> produceProduct(String productListInput) {
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

    private static Optional<Product> createProduct(String baseProductDescription, List<String> extraDescriptions) {
        Optional<BaseMainProduct> baseMainProductOpt = Optional.ofNullable(BASE_PRODUCT_MAP.get(normalizedName(baseProductDescription)))
                .orElse(Collections.emptyList())
                .stream()
                .findFirst();
                if (baseMainProductOpt.isPresent()) {
                    BaseMainProduct baseMainProduct = baseMainProductOpt.get().clone();
                    extraDescriptions.forEach(extraDesc ->
                            Optional.ofNullable(EXTRA_PRODUCT_MAP.get(normalizedName(extraDesc)))
                                    .orElse(Collections.emptyList())
                                    .stream()
                                    .findFirst()
                                    .ifPresent(baseMainProduct::addExtraProduct)
                        );
                    return Optional.ofNullable(baseMainProduct);
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
