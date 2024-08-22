package org.shop.service.product;

import org.shop.model.product.BaseExtraProduct;
import org.shop.model.product.BaseMainProduct;
import org.shop.model.product.BaseMainProduct.PortionSizeType;
import org.shop.model.product.MeasurableProduct;
import org.shop.model.product.Product;
import org.shop.model.product.extra.ExtraMilk;
import org.shop.model.product.extra.FoamedMilk;
import org.shop.model.product.extra.SpecialRoast;
import org.shop.model.product.main.BaconRoll;
import org.shop.model.product.main.Coffee;
import org.shop.model.product.main.FreshlySqueezedOrangeJuice;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.shop.model.product.BaseMainProduct.PortionSizeType.*;
import static org.shop.model.product.BaseMainProduct.PortionVolumeType.L_0_25;
import static org.shop.model.product.ProductGroup.*;

public class ProductFactory {
    private final static Map<String, Class<?>> BASE_PRODUCT_MAP  = Map.of(
            normalizedName(Coffee.NAME), Coffee.class,
            normalizedName(BaconRoll.NAME), BaconRoll.class,
            normalizedName( FreshlySqueezedOrangeJuice.NAME), FreshlySqueezedOrangeJuice.class
    );

    private final static Map<String, Class<?>> EXTRA_MAP  = Map.of(
            normalizedName(ExtraMilk.NAME), ExtraMilk.class,
            normalizedName(FoamedMilk.NAME), FoamedMilk.class,
            normalizedName(SpecialRoast.NAME), SpecialRoast.class
    );

    private static final Map<String, Coffee.CoffeeSizeType> COFFE_SIZE_MAP =
            Stream.of(Coffee.CoffeeSizeType.values())
            .collect(Collectors.toMap(size -> size.name().toLowerCase(), size -> size));
    private static final Map<String, Coffee.CoffeeSizeType> JUICE_SIZE_MAP =
            Stream.of(Coffee.CoffeeSizeType.values())
                    .collect(Collectors.toMap(size -> size.name().toLowerCase(), size -> size));

    private static final Map<String, Coffee.CoffeeSizeType> NAME_TO_ENUM_MAP = Stream.of(Coffee.CoffeeSizeType.values())
            .collect(Collectors.toMap(
                    size -> size.name().toLowerCase(),
                    size -> size
            ));

    private final static Map<Class<?>, Map<String, ? extends MeasurableProduct>> SIZE_MAP  = Map.of(
            Coffee.class, NAME_TO_ENUM_MAP,
            FreshlySqueezedOrangeJuice.class, JUICE_SIZE_MAP
    );

    private final Map<String, BaseExtraProduct> BASE_PRODUCT_MAP = Stream.of(
            new BaseMainProduct("Coffee", BEVERAGE, LARGE, null, 3.55),
            new BaseMainProduct("Coffee", BEVERAGE, MEDIUM, null, 3.05),
            new BaseMainProduct("Coffee", BEVERAGE, SMALL, null, 2.55),
            new BaseMainProduct("Bacon Roll", SNACK, null, null, 4.53),
            new BaseMainProduct("Orange juice", BEVERAGE, null, L_0_25, 3.95),
            new BaseMainProduct("Freshly squeezed orange juice", BEVERAGE, null, L_0_25, 3.95),
            ).collect(Collectors.groupingBy(this::buildKey));

    public ProductFactory() {
        List.of(
                new BaseMainProduct("Coffee", BEVERAGE, LARGE, null, 3.55),
                new BaseMainProduct("Coffee", BEVERAGE, MEDIUM, null, 3.05),
                new BaseMainProduct("Coffee", BEVERAGE, SMALL, null, 2.55),
                new BaseMainProduct("Bacon Roll", SNACK, null, null, 4.53),
                new BaseMainProduct("Orange juice", BEVERAGE, null, L_0_25, 3.95),
                new BaseMainProduct("Freshly squeezed orange juice", BEVERAGE, null, L_0_25, 3.95),
        ).stream().collect(Collectors.groupingBy(this::buildKey));
    }

    public String buildKey(BaseMainProduct baseMainProduct) {
        StringBuilder sb = new StringBuilder();
        sb.append(Optional.ofNullable(baseMainProduct.getPortionSizeType())
                        .map(PortionSizeType::name)
                        .map(String::toLowerCase).orElse(""))
                .append(baseMainProduct.getProductName())
                .append(" ");

        return sb.toString();
    }

    public static Product produceProduct(String baseProductDescription, String productSize,
                                         List<String> extraDescriptions) {
        try {
            Class<?> baseProductClass = BASE_PRODUCT_MAP.get(normalizedName(baseProductDescription));
            Optional<MeasurableProduct> size = Optional.ofNullable(SIZE_MAP.get(baseProductClass))
                    .map(map -> map.get(normalizedName(productSize)));

            BaseMainProduct baseMainProduct;
            if (size.isPresent()) {
                Constructor<?> constructor = baseProductClass.getConstructor(size.get().getClass());
                baseMainProduct = (BaseMainProduct) constructor.newInstance(size.get());
            } else {
                Constructor<?> constructor =  baseProductClass.getConstructor();
                baseMainProduct = (BaseMainProduct) constructor.newInstance();
            }

            for (String extraDesc : extraDescriptions) {
               Class<?> extraClass = EXTRA_MAP.get(normalizedName(extraDesc));
               Constructor<?> constructor = extraClass.getConstructor();
               BaseExtraProduct baseExtraProduct = (BaseExtraProduct) constructor.newInstance();
                baseMainProduct.addExtraProduct(baseExtraProduct);
            }
            return baseMainProduct;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product instance", e);
        }
    }

    private static String normalizedName(String name) {
        return  name.toLowerCase().replace(" ","");
    }
}
