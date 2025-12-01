
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
        System.out.println("""
                ╔══════════════════════════════════════╗
                ║             Café Manager             ║
                ╟──────────────────────────────────────╢
                ║                                      ║
                ║     1) See forecast ˚ ☁️⋅♡𓂃 ࣪ ִֶָ☾.☂      ║
                ║     2) See inventory 🛒              ║
                ║     3) Buy ingredients 💰🛍           ║
                ║     4) See customers 🧑‍🤝‍🧑          ║
                ║     5) Make food ‧₊˚ ⋅ 𓐐𓎩 ‧₊˚⋅       ║
                ║     6) Serve customer (˘▽˘)っ 𓌉◯𓇋    ║
                ║                                      ║
                ╚══════════════════════════════════════╝
                """);
        System.out.println(" \nSelect from 1-6");
    }

    //method for option 2
    public static void showInventory(int coins) {
        boolean valid = true;
        while (valid){
            System.out.println("""
                                ╔══════════════════════════════════════╗
                                ║              Inventory               ║
                                ╟──────────────────────────────────────╢
                                ║                                      ║
                                ║    1) See ingredients    🧺          ║
                                ║    2) See dishes         🍽️           ║
                                ║    3) See your points    ✨          ║
                                ║    4) Back to main menu              ║
                                ║                                      ║
                                ╚══════════════════════════════════════╝
                                """);
            System.out.println("Select 1-4");
            int inventoryOption = handleIntUserInput();

            if (inventoryOption >0 && inventoryOption< 5){
            
                switch (inventoryOption) {
                    case 1:
                        for (int i = 0; i < foodInventory.size(); i++ ){
                            System.out.println(" - Dish: " +foodInventory.get(i).getName() + ", Quantity" + foodInventory.get(i).getQuantity());
                        }
                        System.out.println("\nPress Enter to return to Inventory");
                        scanner.nextLine();
                        break;
                    case 2:
                        for (String i: ingredientsCustomerHas.keySet()){
                            System.out.println(" - Ingredient: " + i + ", Quantity: " + ingredientsCustomerHas.get(i));
                        }
                        System.out.println("\nPress Enter to return to Inventory");
                        scanner.nextLine();
                        break;
                    case 3:
                        System.out.println("\nYou currently have " + coins + " coins.");
                        System.out.println("\nPress Enter to return to Inventory");
                        scanner.nextLine();
                        break;
                    case 4:
                        valid = false;
                        break;
                }
            }
            else{
                System.out.println("\n Select a number within the specified range\n");
            }
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
        String buyOption = handleStringUserInput();
            
        int itemsNotPresentInSupply =0; 
        for (int i = 0; i < ingredientSupply.size(); i++){
            if (ingredientSupply.get(i).getName().equals(buyOption)) {
                
                System.out.println("\nYou can buy " + ingredientSupply.get(i).getQuantity() + " for " + ingredientSupply.get(i).getPrice());
                System.out.println("How many would you like to buy?");
                int buyAmount = handleIntUserInput();
                
                if (ingredientSupply.get(i).getQuantity() >= buyAmount) {
                    System.out.println("\nCalculating Cost...\n"); 
                    int price = Utility.calculatePrice(ingredientSupply.get(i).getQuantity(), ingredientSupply.get(i).getPrice(), buyAmount);
                    System.out.println("\nYour total cost is " + price + " coins.");
                    System.out.println("Are you sure you want to buy this? Y/N\n");
                    String choice = handleStringUserInput();
                    
                    if (choice.equalsIgnoreCase("Y")){
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
        System.out.println("\nWhat would you like to make?");
        String dish = handleStringUserInput();
        ArrayList<String> menu = Utility.createMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).equals(dish)){
                FileHandling file = new FileHandling();
                file.writeFileForRecipes();
                file.readRecipesFile(dish);
                System.out.println("\nHow many would you like to make?");
                int itemNumber = handleIntUserInput();
                file.checkIfItemCanBeCreated(dish,itemNumber,ingredientsCustomerHas);
            }
        }
    }

    public static void serveNextCustomer() {
        for (int i =0 ; i< customers.size(); i++){
            int counter = 0;
            System.out.println("\n"+customers.get(i));
            System.out.println("Serve this customer? (Y/N)");
            String serveOption = handleStringUserInput();
            String foodDesiredByCustomer = customers.get(i).getItem();
            
            if (serveOption.equalsIgnoreCase("Y")) {
                Utility.serveCustomer(counter, foodInventory, foodDesiredByCustomer, customers, i);
            }
            
            if (serveOption.equalsIgnoreCase("N")) {
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
        int chosenCustomer = handleIntUserInput();
            
        System.out.println(customers.get(chosenCustomer-1).toString());
        System.out.println("Serve this customer? [Y/N]");
        String serveOption = handleStringUserInput();
        String foodDesiredByCustomer = customers.get(chosenCustomer-1).getItem();
            
        if (serveOption.equalsIgnoreCase("Y")) {
            Utility.serveCustomer(0, foodInventory, foodDesiredByCustomer, customers, chosenCustomer-1);
        }
            
        if (serveOption.equalsIgnoreCase("N")) {
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
                    break;
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
                    System.out.println("Select from 1-2\n");
                    int servingCustomerOption = handleIntUserInput();
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

    public static int handleIntUserInput() {
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

    public static String handleStringUserInput() {
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.matches("^[a-zA-Z]*$")){
                return userInput;
            }
            else {System.out.println("Enter a valid string");}
            }
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
        String proceed = handleStringUserInput();
    
        while (proceed.equalsIgnoreCase("Y")) {
            showOptionMenu();
            int chosenOption = handleIntUserInput();
            menuHandler(chosenOption, coins);
            System.out.println("\nWould you like to continue? Y/N");
            proceed = handleStringUserInput();
        }
    
        if (proceed.equalsIgnoreCase("N")) {
            //TODO: have a message being sent here?
            System.out.println("Have a nice day! see you soon?"); 
        }
        scanner.close();       
    }
}


