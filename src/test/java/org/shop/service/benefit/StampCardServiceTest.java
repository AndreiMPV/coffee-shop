package org.shop.service.benefit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.shop.model.bonus.StampCard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StampCardServiceTest {

    @Test
    @DisplayName("The card owner's first and last names should match the provided values")
    void shouldCreateCard() {
        // Given
        StampCardService stampCardService = new StampCardService();
        String firstName = "John";
        String lastName = "Doe";
        // When
        StampCard stampCard = stampCardService.createCard(firstName, lastName);
        // Then
        assertEquals(firstName, stampCard.getOwnerFirstName(), "The owner's first name should match.");
        assertEquals(lastName, stampCard.getOwnerLastName(), "The owner's last name should match.");
    }
}