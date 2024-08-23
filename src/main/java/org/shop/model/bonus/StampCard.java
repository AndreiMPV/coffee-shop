package org.shop.model.bonus;

import org.shop.model.product.MainProduct;

import java.util.ArrayList;
import java.util.List;

public class StampCard {
    private final String ownerFirstName;
    private final String ownerLastName;
    private final List<MainProduct> stampCardProducts = new ArrayList<>();

    public StampCard(String ownerFirstName, String ownerLastName) {
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public List<MainProduct> getCardProducts() {
        return stampCardProducts;
    }

    public void addOrderedProducts(List<MainProduct> stampCardProducts) {
        this.stampCardProducts.addAll(stampCardProducts);
    }
}
