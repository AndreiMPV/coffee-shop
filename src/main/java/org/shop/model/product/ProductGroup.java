package org.shop.model.product;

/**
 * Represents the different categories or groups that a product in the coffee shop can belong to.
 * This enum is used to categorize products such as beverages, snacks, and extras.
 */
public enum ProductGroup {

    /**
     * Represents the beverage group, which includes all drinkable products such as coffee, tea, etc.
     */
    BEVERAGE,

    /**
     * Represents the snack group, which includes edible items like pastries, sandwiches, etc.
     */
    SNACK,

    /**
     * Represents the extra group, which includes additional items or add-ons that can complement
     * the main products, such as syrups, extra toppings, etc.
     */
    EXTRA
}