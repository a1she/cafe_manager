
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cafemanager.Customer;
import cafemanager.FileHandling;
import cafemanager.Supply;



public class Main {

    public static JsonObject recipeList;


    //method to check if user can create item
    public static void checkIfItemCanBeMade(JsonObject recipeList, int number){
        //know the quantity of each item, and then multiply for number of items, and check if user can buy that.
        var ingredients = recipeList.getAsJsonArray("ingredients");
        for (int i = 0; i<ingredients.size(); i++){
            System.out.println(ingredients.get(i));
        }
        System.out.println(recipeList.get("ingredients"));
    }

    //method to calculate the cost of item
    public static int calculatePrice(int inventoryAmount, int price, int userAmount ){
        return userAmount*(price/inventoryAmount);
    }

        public static void main(String[] args) {
        
        ArrayList<String> Menu = new ArrayList<>();
        Menu.add("Hot Chocolate");
        Menu.add("Chocolate Croissant");
        // Menu.add("Hot Chocolate");
        // Menu.add("Hot Chocolate");
        // Menu.add("Hot Chocolate");
        System.out.println("\nEnter your name\n");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        //explain how to play the game here?
        FileHandling introfile = new FileHandling();
        introfile.writeIntroductionFile(username);
        introfile.readIntroductionFile();
        System.out.println("\nThe options bellow will allow you to navigate to different sections of the game, good luck " +username);
        System.out.println("Would you like to continue? Y/N");
        String proceed = scanner.nextLine();

        if (proceed.equals("Y")) {

            do {
                System.out.println("\nOption 1: see forecast");
                System.out.println("Option 2: see inventory");
                System.out.println("Option 3: buy ingredients");
                System.out.println("Option 4: see customers");
                System.out.println("Option 5: make food\n");

                int choosenOption = scanner.nextInt();
    
                if (choosenOption == 1) {
                    //generates random number from 0, 1, 2
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
    
                //create hashmap to store ingredients
                int coins = 50;
                HashMap<String, Integer> ingredients  = new HashMap<String, Integer>();
    
                //default ingredients for user to use
                ingredients.put("Flour", 5);
                ingredients.put("Milk", 5);
                ingredients.put("Butter", 5);
                ingredients.put("Egg", 5);
                ingredients.put("Chocolate", 5);
    
                //displaying inventory
                if (choosenOption == 2){
                    System.out.println("\nYou currently have " + coins + " coins.");
                    System.out.println("The current items in your inventory are:");
                    for (String i: ingredients.keySet()){
                        System.out.println(" -Ingredient: " + i + ", Quantity: " + ingredients.get(i));
                    }
                }
    
                List<Supply> Supply = new ArrayList<>();
                Supply.add(new Supply("Flour", 10, 5));
                Supply.add(new Supply("Milk", 5, 5));
    
                if (choosenOption == 3) {
    
                    //display options which the user can buy
                    System.out.println("\nThese are the options you can buy from:");
                    for (int i =0 ; i< Supply.size(); i++){
                        System.out.println(Supply.get(i));
                    }
    
                    //want to loop this part?
    
                    //check what user wants to buy
                    System.out.println("\nWhat would you like to buy?\n");
                    //buffer
                    scanner.nextLine();
                    String buyOption = scanner.nextLine();
    
                    int checkingForNoItems = 0;
    
                    for (int i = 0; i<Supply.size(); i++){
                        if (Supply.get(i).getName().equals(buyOption)) {
    
                            System.out.println("\nYou can buy " + Supply.get(i).getAmount()+ " for " + Supply.get(i).getPrice());
                            System.out.println("How many would you like to buy?");
                            int buyAmount = scanner.nextInt();
    
                            if (Supply.get(i).getAmount() >= buyAmount) {
                                System.out.println("\nCalculating Cost...\n"); 
                                int price = calculatePrice(Supply.get(i).getAmount(), Supply.get(i).getPrice(), buyAmount);
                                System.out.println("\nYour total cost is " + price + " coins.");
                                System.out.println("Are you sure you want to buy this? Y/N\n");
                                scanner.nextLine();
                                String choice = scanner.nextLine();
    
                                if (choice.equals("Y")){
    
                                    if (coins>=price) {
                                        coins = coins - price;
                                        System.out.println("\nYou have successfully purchased " + Supply.get(i).getName() + " for " + price + " coins.\n");
                                        System.out.println("\nYou have " +coins+ " coins left.\n");
                                        // remember this Supply.get(i).setAmount(buyAmount);
                                    }
                                    else System.out.println("You don't have enough coins to buy this");
                                }
                            }
                        }
                        else checkingForNoItems++;
                    }
    
                    if (checkingForNoItems == Supply.size()) {
                        System.out.println("This item doesn't exist in the inventory, try again");
                    }
                } 

                if (choosenOption == 4) {

                List<Customer> customers = new ArrayList<>();
                customers.add(new Customer("Alex", "hot chocolate", 2));
                customers.add(new Customer("Nicole", "Cappucino", 1));
                customers.add(new Customer("Ashley", "coffee", 5));
                customers.add(new Customer("John", "matcha", 3));
                customers.add(new Customer("Smith", "latte", 1));
                for (int i =0 ; i< customers.size(); i++){
                        System.out.println("\n"+customers.get(i));
                    }
                }

                if (choosenOption == 5) {
                    System.out.println("What would you like to make?");
                    scanner.nextLine();
                    FileHandling file = new FileHandling();
                    file.writeFileForRecipes();
                    file.readRecipesFile();
                    System.out.println("printing smt out");
                    file.checkIfItemCanBeMade();
                    // String dish = scanner.nextLine();
                    // for (int i =0; i<Menu.size(); i++) {
                    //     if (Menu.get(i).equals(dish)){
                    //         FileHand
                    //         System.out.println("How many would you like to make?");
                    //         int itemNumber = scanner.nextInt();
                    //         checkIfItemCanBeMade(recipeList,itemNumber);





                    //     }
                    // }


                }

            }
            while (proceed.equals("Y"));
        }
        else{
            System.out.println("Have a nice day! see you soon?"); 
        } 
            

        scanner.close();
        
    }
}

