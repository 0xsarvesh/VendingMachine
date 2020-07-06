package com.dunzo.model;

import com.dunzo.model.ingredient.Ingredient;
import com.dunzo.model.ingredient.IngredientType;
import com.dunzo.model.ingredient.Quantity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Semaphore;

public class VendingMachine {

    private final int outletCount;
    private final List<Beverage> beverageList;
    private final Map<IngredientType, Ingredient> stock;
    private final Semaphore semaphore;


    public VendingMachine(int outletCount, List<Beverage> beverageList, List<Ingredient> ingredients) {
        this.outletCount = outletCount;
        this.beverageList = beverageList;
        Map<IngredientType, Ingredient> ingredientTypeIngredientMap = new HashMap<>();
        for (Ingredient i : ingredients) {
            ingredientTypeIngredientMap.put(i.getIngredientType(), i);
        }
        this.stock = ingredientTypeIngredientMap;
        semaphore = new Semaphore(outletCount);
    }

    public void refill(IngredientType ingredientType, Quantity quantity) throws Exception {
        Ingredient existingIngredient = this.stock.get(ingredientType);

        if (existingIngredient != null) {
            existingIngredient.addSupply(quantity);
            return;
        }

        this.stock.put(ingredientType, new Ingredient(ingredientType, quantity));
    }

    public boolean serve(String beverageName) throws InterruptedException {
        boolean isBeveragePrepared = false;
        try {
            semaphore.acquire();
            Optional<Beverage> beverageOptional = searchBeverage(beverageName);

            if (!beverageOptional.isPresent()) {
                System.out.println("Beverage not found");
                return isBeveragePrepared;
            }
            isBeveragePrepared = prepareBeverage(beverageOptional.get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            semaphore.release();
        }
        return isBeveragePrepared;
    }

    private Optional<Beverage> searchBeverage(String beverageName) {
        return beverageList
                .stream()
                .filter(beverage -> beverage.isSameName(beverageName))
                .findFirst();
    }

    private synchronized boolean prepareBeverage(Beverage beverage) throws Exception {
        List<Ingredient> requiredIngredients = beverage.getIngredients();

        if (!checkAllIngredientAvailable(beverage)) return false;

        consumeIngredients(requiredIngredients);
        System.out.println(beverage.getName() + " is prepared");
        return true;
    }

    private void consumeIngredients(List<Ingredient> requiredIngredients) throws Exception {
        for (Ingredient currentIngredient : requiredIngredients) {
            Ingredient ingredientFromStock = stock.get(currentIngredient.getIngredientType());
            if (ingredientFromStock.getIngredientQuantity().isGreater(currentIngredient.getIngredientQuantity())
            ) {
                ingredientFromStock.consumeIngredient(currentIngredient.getIngredientQuantity());

            }
        }
    }

    private boolean checkAllIngredientAvailable(Beverage beverage) throws Exception {
        for (Ingredient currentIngredient : beverage.getIngredients()) {
            Ingredient ingredientFromStock = stock.get(currentIngredient.getIngredientType());
            if (ingredientFromStock == null ||
                    currentIngredient.getIngredientQuantity()
                            .isGreater(
                                    ingredientFromStock.getIngredientQuantity()
                            )
            ) {
                System.out.println(beverage.getName() + " cannot be prepared because " + currentIngredient.getIngredientType().toString().toLowerCase() + " is not available");
                return false;
            }
        }
        return true;
    }

}
