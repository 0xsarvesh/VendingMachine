import com.dunzo.model.Beverage;
import com.dunzo.model.VendingMachine;
import com.dunzo.model.ingredient.Ingredient;
import com.dunzo.model.ingredient.IngredientType;
import com.dunzo.model.ingredient.Quantity;
import com.dunzo.model.ingredient.QuantityUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.Arrays.asList;

public class Main {


    public static void main(String[] args) {

        try {

            int outlet = 3;
            List<Ingredient> inventory = createInventory();
            List<Beverage> beverages = createBeverages();
            VendingMachine vendingMachine = new VendingMachine(
                    outlet,
                    beverages,
                    inventory
            );
            createParallelBeverageRequest(outlet, vendingMachine, new String[]{"hot_tea", "hot_coffee", "black_tea", "green_tea"});

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error while executing");
        }

    }

    public static List<Boolean> createParallelBeverageRequest(int outlet, VendingMachine vendingMachine, String[] requestedBeverages) throws InterruptedException, ExecutionException {

        List<Callable<Boolean>> taskList = new ArrayList<>();
        for (int index = 0; index < requestedBeverages.length; index++) {
            int finalIndex = index;
            taskList.add(() -> {
                boolean result = vendingMachine.serve(requestedBeverages[finalIndex]);
                return result;
            });
        }
        ExecutorService executor = Executors.newFixedThreadPool(outlet);
        List<Boolean> result = new ArrayList<>();
        for (Future<Boolean> f : executor.invokeAll(taskList)) {
            Boolean aBoolean = f.get();
            result.add(aBoolean);
        }
        return result;
    }

    private static List<Beverage> createBeverages() {
        List<Beverage> beverages = new ArrayList<>();
        beverages.add(new Beverage(
                "hot_tea",
                asList(
                        new Ingredient(IngredientType.HOT_WATER, new Quantity(200, QuantityUnit.ML)),
                        new Ingredient(IngredientType.HOT_MILK, new Quantity(100, QuantityUnit.ML)),
                        new Ingredient(IngredientType.GINGER_SYRUP, new Quantity(10, QuantityUnit.ML)),
                        new Ingredient(IngredientType.SUGAR_SYRUP, new Quantity(10, QuantityUnit.ML)),
                        new Ingredient(IngredientType.TEA_LEAVES_SYRUP, new Quantity(10, QuantityUnit.ML))
                )
        ));
        beverages.add(new Beverage(
                "hot_coffee",
                asList(
                        new Ingredient(IngredientType.HOT_WATER, new Quantity(100, QuantityUnit.ML)),
                        new Ingredient(IngredientType.GINGER_SYRUP, new Quantity(30, QuantityUnit.ML)),
                        new Ingredient(IngredientType.HOT_MILK, new Quantity(400, QuantityUnit.ML)),
                        new Ingredient(IngredientType.SUGAR_SYRUP, new Quantity(50, QuantityUnit.ML)),
                        new Ingredient(IngredientType.TEA_LEAVES_SYRUP, new Quantity(30, QuantityUnit.ML))
                )
        ));
        beverages.add(new Beverage(
                "black_tea",
                asList(
                        new Ingredient(IngredientType.HOT_WATER, new Quantity(300, QuantityUnit.ML)),
                        new Ingredient(IngredientType.GINGER_SYRUP, new Quantity(30, QuantityUnit.ML)),
                        new Ingredient(IngredientType.SUGAR_SYRUP, new Quantity(50, QuantityUnit.ML)),
                        new Ingredient(IngredientType.TEA_LEAVES_SYRUP, new Quantity(30, QuantityUnit.ML))
                )
        ));
        beverages.add(new Beverage(
                "green_tea",
                asList(
                        new Ingredient(IngredientType.HOT_WATER, new Quantity(100, QuantityUnit.ML)),
                        new Ingredient(IngredientType.GINGER_SYRUP, new Quantity(30, QuantityUnit.ML)),
                        new Ingredient(IngredientType.SUGAR_SYRUP, new Quantity(50, QuantityUnit.ML)),
                        new Ingredient(IngredientType.GREEN_MIXTURE, new Quantity(30, QuantityUnit.ML))
                )
        ));
        return beverages;
    }

    private static List<Ingredient> createInventory() {
        List<Ingredient> inventory = new ArrayList<>();
        inventory.add(new Ingredient(IngredientType.HOT_WATER, new Quantity(500, QuantityUnit.ML)));
        inventory.add(new Ingredient(IngredientType.HOT_MILK, new Quantity(500, QuantityUnit.ML)));
        inventory.add(new Ingredient(IngredientType.GINGER_SYRUP, new Quantity(100, QuantityUnit.ML)));
        inventory.add(new Ingredient(IngredientType.SUGAR_SYRUP, new Quantity(100, QuantityUnit.ML)));
        inventory.add(new Ingredient(IngredientType.TEA_LEAVES_SYRUP, new Quantity(100, QuantityUnit.ML)));
        return inventory;
    }
}
