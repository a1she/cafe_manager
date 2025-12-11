package cafemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Utility {
    //creates the cafe's menu
    public static ArrayList<String> createMenu() {
        ArrayList<String> Menu = new ArrayList<>();
        Menu.add("Hot Chocolate");
        Menu.add("Chocolate Croissant");
        Menu.add("Cheese Toastie");
        Menu.add("Latte");
        Menu.add("Brownie");
        return Menu;
    }

    //creates default ingredients for user to use
    public static HashMap<String, Double> createIngredientsCustomerHas() {
        HashMap<String, Double> ingredientsCustomerHas  = new HashMap<String, Double>();
        ingredientsCustomerHas.put("Cheese", 4.0);
        ingredientsCustomerHas.put("Bread_Slice", 3.0);
        ingredientsCustomerHas.put("Milk", 5.0);
        ingredientsCustomerHas.put("Butter", 2.0);
        ingredientsCustomerHas.put("Coffe", 2.0);
        ingredientsCustomerHas.put("Chocolate", 2.0);
        ingredientsCustomerHas.put("Dough", 4.0);
        ingredientsCustomerHas.put("Sugar", 1.0);
        ingredientsCustomerHas.put("Cocoa_Powder", 1.0);
        return ingredientsCustomerHas;
    }

    //creates a set of customers
    public static List<Customer> createCustomers() {
    List<Customer> customers = new ArrayList<>();
    customers.add(new Customer("Alex", "Hot Chocolate", 2));
    customers.add(new Customer("Nicole", "Latte", 3));
    customers.add(new Customer("Ashley", "Brownie", 2));
    customers.add(new Customer("John", "Cheese Toastie", 2));
    customers.add(new Customer("Smith", "Chocolate Croissant", 3));
    return customers;
    }

    //creates a default amount of dishes user already has
    public static List<FoodInventory> createDishInventory() {
        List<FoodInventory> foodInventory = new ArrayList<>();
        foodInventory.add(new FoodInventory("Hot Chocolate", 2));
        foodInventory.add(new FoodInventory("Chocolate Croissant", 2));
        foodInventory.add(new FoodInventory("Brownie", 2));
        foodInventory.add(new FoodInventory("Latte", 2));
        foodInventory.add(new FoodInventory("Cheese Toastie", 2));
        return foodInventory;
    }

    //creates an ingredient supply list which user can buy from
    public static List<IngredientSupply> createIngredientsCustomerCanBuyFrom() {
        List<IngredientSupply> ingredientSupply = new ArrayList<>();
        ingredientSupply.add(new IngredientSupply("Milk", 10, 5));
        ingredientSupply.add(new IngredientSupply("Bread_Slice", 10, 5));
        ingredientSupply.add(new IngredientSupply("Butter", 10, 5));
        ingredientSupply.add(new IngredientSupply("Coffe", 10, 5));
        ingredientSupply.add(new IngredientSupply("Cheese", 10, 5));
        ingredientSupply.add(new IngredientSupply("Chocolate", 10, 5));
        ingredientSupply.add(new IngredientSupply("Dough", 10, 5));
        ingredientSupply.add(new IngredientSupply("Sugar", 10, 5));
        ingredientSupply.add(new IngredientSupply("Cocoa_Powder", 10, 5));
        return ingredientSupply;
    }

    //generates random number from 0, 1, 2 to create a random forecast
    public static void createForecast() {
        int randomNumber = (int)(Math.random()*3) ;
        switch (randomNumber) {
            case 0:
                System.out.println("\nToday is a rainy day with slow foot traffic. People linger around the cafe creating a really cozy atmosphere.");
                System.out.println("In demand we have:");
                System.out.println("Hot drinks - lattes, cappuccinos, hot chocolate, chai");
                System.out.println("Comfort bakes - warm pastries, banana bread, soup if available.\n");
                break;
            case 1:
                System.out.println("\nRush hour sucks! Everything is always so fast-paced since everyone wants food to takeout and is in a hurry.");
                System.out.println("In demand we have:");
                System.out.println("Quick coffees - americanos, drip/filter, flat whites; pre-batched cold brew.");
                System.out.println("Grab-and-go food - croissants, breakfast sandwiches, yogurt pots.\n");
                break;
            case 2:
                System.out.println("\nThere's so many people in today on a weekend. The room is full of couples and friend chattering their morning away.");
                System.out.println("In demand we have:");
                System.out.println("Specialty drinks - flavored lattes, matcha, iced options even in cooler weather.");
                System.out.println("Brunch items - avocado toast, pastries, cakes, shareables.\n");
                break;
        }
    }

    //method to calculate the cost of item
    public static int calculatePrice(int inventoryAmount, int price, int userAmount ){
        return userAmount*(price/inventoryAmount);
    }

    //this checks if the user has the ingredient in their inventory and then updates the amount, if they don't have it then create the ingredient with the amount they bought
    public static void createAndUpdateInventoryIngredients(String ingredientName, HashMap<String, Double> ingredientsCustomerHas, Double buyAmount){
        int counter =0;
        double amount = 0;
        for (String i : ingredientsCustomerHas.keySet()) {
            if (ingredientName.equals(i)){
                amount = ingredientsCustomerHas.get(i) + buyAmount;
            }
            else counter++;
        }
        
        if (counter == ingredientsCustomerHas.size()) {
            ingredientsCustomerHas.put(ingredientName, buyAmount);
        }
        else {
            ingredientsCustomerHas.remove(ingredientName);
            ingredientsCustomerHas.put(ingredientName, amount);  
        }
    }

     //this checks if the user has the dish in their inventory and then updates the amount, if they don't have it then create the new dish with the amount they bought
    public static void createAndUpdateInventoryDishes(List<FoodInventory> dishInventories, String dish, int buyAmount){
        int counter =0;
        for (int i = 0; i < dishInventories.size(); i++ ) {
            if (dishInventories.get(i).getName().equals(dish)){
                int amount = dishInventories.get(i).getQuantity();
                amount = amount + buyAmount;
                dishInventories.get(i).setQuantity(amount);;  
            }
            else counter++;
        }
        if (counter == dishInventories.size()) {
            dishInventories.add(new FoodInventory(dish, buyAmount));
        }
    }

    //allows user to serve the customer by checking if the user has the dish and dish amount in their dish inventory
    public static int serveCustomer( int counter, List<FoodInventory> foodInventory, String foodDesiredByCustomer, List<Customer> customers, int i) {
        int earnedCoins = 0;
        for (int c = 0; c < foodInventory.size(); c++) {
            String foodNameInInventory =foodInventory.get(c).getName();

            if (foodDesiredByCustomer.equals(foodNameInInventory)) {
                counter = 0;
                int foodAmountInInventory = foodInventory.get(c).getQuantity();
                int foodAmountCustomerWants = customers.get(i).getAmount();

                if (foodAmountInInventory >= foodAmountCustomerWants) {
                    System.out.println("\n serving customer ......");
                    foodInventory.get(c).setQuantity(foodAmountInInventory-foodAmountCustomerWants);
                    earnedCoins = foodAmountCustomerWants * 2;
                    System.out.println("\nYou have earned " + earnedCoins + " coins!");
                    System.out.println("\n You have " + foodInventory.get(c).getQuantity() + " " + foodNameInInventory + " left in your inventory");
                }
                else {
                    System.out.println(" \n You don't have enough " + foodNameInInventory + " in your inventory to serve this customer");
                }
            }
            else counter++;
        }

        if (counter == foodInventory.size()) {
            System.out.println("You don't have this dish within your food inventory.");
        }
        return earnedCoins; 
    }

    //validation function to ensure user inputs an integer
    public static int handleIntUserInput(Scanner scanner) {
        while (true) {
            try {
                int userInput = scanner.nextInt();
                scanner.nextLine();
                return userInput;
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid integer");
                scanner.nextLine();
            }     
        }
    }

    //validation function to ensure user inputs a string
    public static String handleStringUserInput(Scanner scanner) {
        while (true) {
            String userInput = scanner.nextLine();
            String userInputWithoutWhiteSpace = userInput.replaceAll(" ", "");
            if (userInputWithoutWhiteSpace.matches("^[a-zA-Z]*$")){
                return userInput;
            }
            else {System.out.println("Enter a valid string");}
        }
    }

    //validation function to ensure user inputs Y or N
    public static String handleStringUserInputForYesorNo(Scanner scanner) {
        while (true) {
            String userInput = scanner.nextLine();
            String userInputWithoutWhiteSpace = userInput.replaceAll(" ", "");
            if (userInputWithoutWhiteSpace.equalsIgnoreCase("Y") || userInputWithoutWhiteSpace.equalsIgnoreCase("N") ){
                return userInput;
            }
            else {System.out.println("Enter Y or N");}
        }
    }
}
