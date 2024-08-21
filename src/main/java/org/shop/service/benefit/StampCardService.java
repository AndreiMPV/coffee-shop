package org.shop.service.benefit;

import org.shop.model.bonus.StampCard;

public class StampCardService {

   public StampCard createCard(String ownerFirstName, String ownerLastName) {
       return new StampCard(ownerFirstName, ownerLastName);
   }
}
