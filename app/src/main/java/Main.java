
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import cafemanager.Customer;
import cafemanager.FileHandling;
import cafemanager.FoodInventory;
import cafemanager.IngredientSupply;
import cafemanager.Utility;

public class Main {

    public static Scanner scanner = new Scanner(System.in); 
    public static List<FoodInventory> foodInventory = Utility.createFoodInventory();
    public static List<Customer> customers =Utility.createCustomers();
    public static HashMap<String, Double> ingredientsCustomerHas = Utility.createIngredientsCustomerHas();

    public static void showOptionMenu() {
        System.out.println("\nOption 1: see forecast");
        System.out.println("Option 2: see inventory");
        System.out.println("Option 3: buy ingredients");
        System.out.println("Option 4: see customers");
        System.out.println("Option 5: make food");
        System.out.println("Option 6: serve customer");
        System.out.println("Select from 1-6");
    }

    public static void showInventory(int coins) {
        System.out.println("1) see ingredients");
        System.out.println("2) see dishes");
        System.out.println("3) see your points");
        System.out.println("Select from 1-3");
        int inventoryOption = scanner.nextInt();
        scanner.nextLine();
        //TODO: can also be out of range.
        
        try {
            switch (inventoryOption) {
                case 1:
                    for (int i = 0; i < foodInventory.size(); i++ ){
                        System.out.println(" - Dish: " +foodInventory.get(i).getName() + ", Quantity" + foodInventory.get(i).getQuantity());
                    }
                    break;
                case 2:
                    for (String i: ingredientsCustomerHas.keySet()){
                        System.out.println(" - Ingredient: " + i + ", Quantity: " + ingredientsCustomerHas.get(i));
                    }
                    break;
                case 3:
                    System.out.println("\nYou currently have " + coins + " coins.");
                    break;
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Invalid selection. Please enter an integer");
        }
    }
    
    public static void buyIngredients(int coins) {
        List<IngredientSupply> ingredientSupply = Utility.createIngredientsCustomerCanBuyFrom();
        System.out.println("\nThese are the options you can buy from:");
        for (int i =0 ; i< ingredientSupply.size(); i++){
            System.out.println(ingredientSupply.get(i));
        }
        //want to loop this part?
        System.out.println("\nWhat would you like to buy?\n");
        scanner.nextLine();
        String buyOption = scanner.nextLine();
            
        
        int itemsNotPresentInSupply =0; 
        for (int i = 0; i < ingredientSupply.size(); i++){
            if (ingredientSupply.get(i).getName().equals(buyOption)) {
                
                System.out.println("\nYou can buy " + ingredientSupply.get(i).getQuantity() + " for " + ingredientSupply.get(i).getPrice());
                System.out.println("How many would you like to buy?");
                int buyAmount = scanner.nextInt();
                
                if (ingredientSupply.get(i).getQuantity() >= buyAmount) {
                    System.out.println("\nCalculating Cost...\n"); 
                    int price = Utility.calculatePrice(ingredientSupply.get(i).getQuantity(), ingredientSupply.get(i).getPrice(), buyAmount);
                    System.out.println("\nYour total cost is " + price + " coins.");
                    System.out.println("Are you sure you want to buy this? Y/N\n");
                    scanner.nextLine();
                    String choice = scanner.nextLine();
                    
                    if (choice.equals("Y")){
                        if (coins>=price) {
                            coins = coins - price;
                            System.out.println("\nYou have successfully purchased " + ingredientSupply.get(i).getName() + " for " + price + " coins.\n");
                            System.out.println("\nYou have " +coins+ " coins left.\n");
                            ingredientSupply.get(i).setQuantity(buyAmount);
                            //TODO: need to add to inventory- first check if it exists and then update amount it or add it as new
                        }
                        else System.out.println("You don't have enough coins to buy this");
                    }
                }
            }
            else itemsNotPresentInSupply++;
        }
        if (itemsNotPresentInSupply == ingredientSupply.size()) {
            System.out.println("This item doesn't exist in the inventory, try again");
        }
    }

    public static void makeFood() {
        System.out.println("What would you like to make?");
        String dish = scanner.nextLine();
        ArrayList<String> menu = Utility.createMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).equals(dish)){
                FileHandling file = new FileHandling();
                file.writeFileForRecipes();
                file.readRecipesFile(dish);
                System.out.println("\nHow many would you like to make?");
                int itemNumber = scanner.nextInt();
                scanner.nextLine();
                file.checkIfItemCanBeCreated(dish,itemNumber,ingredientsCustomerHas);
            }
        }
    }

    public static void serveNextCustomer() {
        for (int i =0 ; i< customers.size(); i++){
            int counter = 0;
            System.out.println("\n"+customers.get(i));
            System.out.println("Serve this customer? (Y/N)");
            String serveOption = scanner.next();
            String foodDesiredByCustomer = customers.get(i).getItem();
            
            if (serveOption.equals("Y")) {
                Utility.serveCustomer(counter, foodInventory, foodDesiredByCustomer, customers, i);
            }
            
            if (serveOption.equals("N")) {
                System.out.println("\n5 coins have been deducted.");
                System.out.println("\nGoing back to the options menu....\n");
                break;
            }
        }
    }

    public static void serveChosenCustomer() {

        int customerPosition = 1;
        for (int i =0; i<customers.size(); i++){
            System.out.println(customerPosition + ")" + customers.get(i).printAsList());
            customerPosition++;
        }
            
        System.out.println("Choose customer from the list");
        int chosenCustomer = scanner.nextInt();
        scanner.nextLine();
            
        System.out.println(customers.get(chosenCustomer-1).toString());
        System.out.println("Serve this customer? [Y/N]");
        String serveOption = scanner.next();
        String foodDesiredByCustomer = customers.get(chosenCustomer-1).getItem();
            
        if (serveOption.equals("Y")) {
            Utility.serveCustomer(0, foodInventory, foodDesiredByCustomer, customers, chosenCustomer-1);
        }
            
        if (serveOption.equals("N")) {
            System.out.println("\n5 coins have been deducted.");
            System.out.println("\nGoing back to the options menu....\n");
        }
    }

    public static void menuHandler(int chosenOption, int coins) {

        if (chosenOption >0 && chosenOption < 7) {
            switch (chosenOption) {
                case 1:
                    Utility.createForecast();
                    break;
                case 2:
                    showInventory(coins);
                case 3:
                    buyIngredients(coins);
                    break;
                case 4:
                    for (int i =0 ; i< customers.size(); i++){
                        System.out.println("\n"+customers.get(i));
                    }
                    break;
                case 5:
                    makeFood();
                    break;
                case 6:
                    System.out.println("\nOption 1: Serve next customer");
                    System.out.println("Option 2: View queue and choose customer");
                    System.out.println("Select from 1-2");
                    int servingCustomerOption = scanner.nextInt();
                    scanner.nextLine();
                    switch (servingCustomerOption) {
                        case 1:
                            serveNextCustomer();
                            break;
                        case 2:
                            serveChosenCustomer();
                    }
            }
        }
        else System.out.println("Please select from 1-6");
    }


    public static void main(String[] args) {
        // variables created:
        int coins = 50;

        System.out.println("\nEnter your name\n");
        String username = scanner.nextLine();
        
        //greets and introduces the user to the game
        FileHandling introfile = new FileHandling();
        introfile.writeIntroductionFile(username);
        introfile.readIntroductionFile();

        System.out.println("\nThe options bellow will allow you to navigate to different sections of the game, good luck " +username);
        System.out.println("Would you like to continue? Y/N");
        String proceed = scanner.nextLine();

        do {
            showOptionMenu();
            int chosenOption = scanner.nextInt();
            scanner.nextLine();
            menuHandler(chosenOption, coins);
            
        } while (proceed.equals("Y"));

        if (proceed.equals("N")) {
            //TODO: have a message being sent here?
            System.out.println("Have a nice day! see you soon?"); 
        }

        scanner.close();       
    }
}


