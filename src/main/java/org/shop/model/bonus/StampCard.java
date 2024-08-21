package org.shop.model.bonus;

import org.shop.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StampCard {
    private final String uuid = UUID.randomUUID().toString();
    private final String ownerFirstName;
    private final String ownerLastName;
    private List<Product> stampCardProducts = new ArrayList<>();

    public StampCard(String ownerFirstName, String ownerLastName) {
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public List<Product> getCardProducts() {
        return stampCardProducts;
    }

    public void addOrderedItems(List<Product> stampCardProducts) {
        this.stampCardProducts.addAll(stampCardProducts);
    }

    public void addOrderedItems(Product stampCardProduct) {
        this.stampCardProducts.add(stampCardProduct);
    }
}
