package com.dunzo.model;

import com.dunzo.model.ingredient.Ingredient;

import java.util.Collections;
import java.util.List;

public class Beverage {
    private final String name;
    private final List<Ingredient> ingredients;


    public Beverage(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public boolean isSameName(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public String getName() {
        return name;
    }
}
