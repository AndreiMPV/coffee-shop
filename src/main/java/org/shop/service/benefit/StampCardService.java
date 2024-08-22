package org.shop.service.benefit;

import org.shop.model.bonus.StampCard;

/**
 * Service class responsible for creating and managing stamp cards in the coffee shop system.
 * The stamp card typically tracks customer loyalty by recording the number of purchases
 * and offering rewards based on the number of stamps collected.
 */
public class StampCardService {

    /**
     * Creates a new stamp card for a customer with the specified first and last name.
     *
     * @param ownerFirstName the first name of the stamp card's owner.
     * @param ownerLastName the last name of the stamp card's owner.
     * @return a new {@link StampCard} instance associated with the given owner.
     */
   public StampCard createCard(String ownerFirstName, String ownerLastName) {
       return new StampCard(ownerFirstName, ownerLastName);
   }
}
