package cafemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utility {
    

    public static ArrayList<String> createMenu() {
        ArrayList<String> Menu = new ArrayList<>();
        Menu.add("Hot Chocolate");
        Menu.add("Chocolate Croissant");

        return Menu;
    }

    //default ingredients for user to use
    public static HashMap<String, Double> createIngredientsCustomerHas() {
        HashMap<String, Double> ingredientsCustomerHas  = new HashMap<String, Double>();
        
        ingredientsCustomerHas.put("Flour", 5.0);
        ingredientsCustomerHas.put("Milk", 5.0);
        ingredientsCustomerHas.put("Butter", 5.0);
        ingredientsCustomerHas.put("Egg", 5.0);
        ingredientsCustomerHas.put("Chocolate", 5.0);
        ingredientsCustomerHas.put("Dough", 5.0);
        ingredientsCustomerHas.put("Sugar", 5.0);
        ingredientsCustomerHas.put("Cocoa_Powder", 5.0);

        return ingredientsCustomerHas;
    }

    public static List<Customer> createCustomers() {
    List<Customer> customers = new ArrayList<>();
    customers.add(new Customer("Alex", "Hot Chocolate", 2));
    customers.add(new Customer("Nicole", "Cappucino", 1));
    // customers.add(new Customer("Ashley", "coffee", 5));
    // customers.add(new Customer("John", "matcha", 3));
    // customers.add(new Customer("Smith", "latte", 1));

    return customers;
    }

    public static List<FoodInventory> createFoodInventory() {
        List<FoodInventory> foodInventory = new ArrayList<>();
        foodInventory.add(new FoodInventory("Hot Chocolate", 2));
        foodInventory.add(new FoodInventory("Chocolate Croissant", 1));
        // foodInventory.add(new FoodInventory("Hot Chocolate", 1));
        // foodInventory.add(new FoodInventory("Hot Chocolate", 1));
        // foodInventory.add(new FoodInventory("Hot Chocolate", 1));
        return foodInventory;
    }

    //generates random number from 0, 1, 2
    public static void createForecast() {
        int randomNumber = (int)(Math.random()*3) ;
        switch (randomNumber) {
            case 0:
                System.out.println("\nToday is a rainy day with slow foot traffic. People linger around the cafe creating a really cozy atmosphere.");
                System.out.println("In demand we have:");
                System.out.println("Hot drinks - lattes, cappuccinos, hot chocolate, chai");
                System.out.println("Comfort bakes - warm pastries, banana bread, soup if available.\n");
                //what does break do here?
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
            default:
                break;
        }
    }

    //method to calculate the cost of item
    public static int calculatePrice(int inventoryAmount, int price, int userAmount ){
        return userAmount*(price/inventoryAmount);
    }

    public static void serveCustomer( int counter, List<FoodInventory> foodInventory, String foodDesiredByCustomer, List<Customer> customers, int i) {
        for (int c = 0; c < foodInventory.size(); c++) {
            String foodNameInInventory =foodInventory.get(c).getFoodName();

            if (foodDesiredByCustomer.equals(foodNameInInventory)) {
                counter = 0;
                int foodAmountInInventory = foodInventory.get(c).getItemQuantity();
                int foodAmountCustomerWants = customers.get(i).getAmount();

                if (foodAmountInInventory >= foodAmountCustomerWants) {
                    System.out.println("\n serving customer ......");
                    foodInventory.get(c).setItemQuantity(foodAmountInInventory-foodAmountCustomerWants);
                    System.out.println("\n You have " + foodInventory.get(c).getItemQuantity() + " " + foodNameInInventory + " left in your inventory");
                }
                else {
                    System.out.println(" \n You don't have enough " + foodNameInInventory + " in your inventory to serve this customer");
                }
            }
            else counter++;
        }

        if (counter == foodInventory.size() ) {
            System.out.println("You don't have this dish within your food inventory, go make some.");
        } 
    }
}
