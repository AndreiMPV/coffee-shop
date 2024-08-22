package org.shop.service.product;

import org.shop.model.product.Measurable;
import org.shop.model.product.Product;
import org.shop.model.product.beverage.Beverage;
import org.shop.model.product.extra.Extra;

import java.lang.reflect.Constructor;

public class ProductFactory {

    public static <T extends Beverage, S extends  Measurable> T produceProduct(Class<T> clazz, S clazzSize, double cost) {
        try {
            Constructor<T> constructor = clazz.getConstructor(clazzSize.getClass(), double.class);
            return constructor.newInstance(clazzSize, cost);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create beverage instance", e);
        }
    }

    public static <T extends Extra> T produceProductWithExtra(Class<T> clazzExtra, Product clazzBeverage, double cost) {
        try {
            Constructor<T> constructor = clazzExtra.getConstructor(Product.class, double.class);
            return constructor.newInstance(clazzBeverage, cost);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create beverage instance", e);
        }
    }
}
