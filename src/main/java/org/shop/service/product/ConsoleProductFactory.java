package org.shop.service.product;

import org.shop.model.product.ExtraProduct;
import org.shop.model.product.MainProduct;
import org.shop.model.product.PortionSizeType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.shop.model.product.PortionSizeType.*;
import static org.shop.model.product.PortionVolumeType.L_0_25;
import static org.shop.model.product.ProductGroup.*;


/**
 * The {@code ConsoleProductFactory} class is responsible for creating products
 * based on input strings. It provides functionality to parse product descriptions,
 * build product keys, and produce product objects with optional extra products.
 * <p>
 * The class maintains mappings for base products and extra products and utilizes these mappings
 * to create instances of {@link MainProduct} with associated extras based on user input.
 * </p>
 */
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

    /**
     * Produces a list of {@link MainProduct} instances based on the input string describing products and extras.
     * <p>
     * The input string is split into product descriptions, and each product is processed to create an instance
     * of {@link MainProduct} with any associated extras.
     * </p>
     *
     * @param productListInput a string describing products and optional extras
     * @return a list of {@link MainProduct} created from the input string
     */
    public List<MainProduct> produceProduct(String productListInput) {
        String[] allProducts = productListInput.split(",");
        return Arrays.stream(allProducts)
                .map(product -> {
                    String[] fullProductDesc = product.split(EXTRA_DELIMITER);
                    String baseProductWithSize = fullProductDesc[BASE_PRODUCT_INDEX];
                    List<String> extras = extractExtras(fullProductDesc);
                    return createProduct(baseProductWithSize, extras);
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private Optional<MainProduct> createProduct(String baseProductDescription, List<String> extraDescriptions) {
        Optional<MainProduct> baseMainProductOpt = Optional.ofNullable(BASE_PRODUCT_MAP.get(normalizedName(baseProductDescription)))
                .orElse(Collections.emptyList())
                .stream()
                .findFirst();
                if (baseMainProductOpt.isPresent()) {
                    MainProduct mainProduct = baseMainProductOpt.get().deepCopy();
                    extraDescriptions.forEach(extraDesc ->
                            Optional.ofNullable(EXTRA_PRODUCT_MAP.get(normalizedName(extraDesc)))
                                    .orElse(Collections.emptyList())
                                    .stream()
                                    .findFirst()
                                    .ifPresent(extra -> mainProduct.addExtraProduct(extra.deepCopy()))
                        );
                    return Optional.ofNullable(mainProduct);
                } else {
                    System.out.println("Can't create product : " + baseProductDescription);
                }
        return Optional.empty();
    }

    private static String buildBaseProductKey(MainProduct mainProduct) {
        return normalizedName(Optional.ofNullable(mainProduct.getPortionSizeType())
                .map(PortionSizeType::name).orElse("") +
                mainProduct.getProductName());
    }

    private static String buildExtraProductKey(ExtraProduct baseMainProduct) {
        return normalizedName(baseMainProduct.getProductName());
    }

    private static List<String> extractExtras(String[] fullProductDesc) {
        return Arrays.stream(fullProductDesc).skip(1).toList();
    }

    private static String normalizedName(String name) {
        return  name.toLowerCase().replace(" ","");
    }
}
