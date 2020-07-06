package com.dunzo.model.ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientTest {

    Ingredient ingredient;
    int initialValue = 10;
    Quantity initialQuantity = new Quantity(initialValue, QuantityUnit.ML);

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient(IngredientType.SUGAR_SYRUP, initialQuantity);
    }

    @Test
    void shouldDecreaseQuantityOnConsumeIngredient() throws Exception {
        int consumptionValue = 2;
        ingredient.consumeIngredient(new Quantity(consumptionValue, QuantityUnit.ML));
        assertEquals(
                initialValue - consumptionValue,
                ingredient.getIngredientQuantity().getValue(),
                "Ingredient quantity must match"
        );
    }

    @Test
    void shouldIncreaseQuantityOnAddSupply() throws Exception {
        int supplyValue = 2;
        ingredient.addSupply(new Quantity(supplyValue, QuantityUnit.ML));
        assertEquals(
                initialValue + supplyValue,
                ingredient.getIngredientQuantity().getValue(),
                "Ingredient quantity must match"
        );
    }
}