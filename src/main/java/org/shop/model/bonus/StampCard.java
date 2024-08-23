package org.shop.model.bonus;

import org.shop.model.product.MainProduct;
import org.shop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
