package com.dunzo.model.ingredient;

public class Ingredient {

    private IngredientType ingredientType;
    private Quantity ingredientQuantity;

    public Ingredient(IngredientType ingredientType, Quantity ingredientQuantity) {
        this.ingredientType = ingredientType;
        this.ingredientQuantity = ingredientQuantity;
    }

    public void consumeIngredient(Quantity quantity) throws Exception {
        if (quantity.isGreater(this.ingredientQuantity)) {
            throw new Error("Insufficient ingredient quantity");
        }

        this.ingredientQuantity = this.ingredientQuantity.subtract(quantity);
    }

    public void addSupply(Quantity quantity) throws Exception {
        this.ingredientQuantity = this.ingredientQuantity.add(quantity);
    }

    public IngredientType getIngredientType() {
        return ingredientType;
    }

    public Quantity getIngredientQuantity() {
        return ingredientQuantity;
    }
}
