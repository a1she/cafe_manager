
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import cafemanager.Customer;
import cafemanager.FileHandling;
import cafemanager.FoodInventory;
import cafemanager.IngredientSupply;
import cafemanager.Utility;
public class Main {

    public static void main(String[] args) {
        
        // variables created:
        List<FoodInventory> foodInventory = Utility.createFoodInventory();
        

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
                System.out.println("Option 5: make food");
                System.out.println("Option 6: serve customer\n");

                int choosenOption = scanner.nextInt();
    
                if (choosenOption == 1) {
                    Utility.createForecast();
                }
    
                //create hashmap to store ingredients
                int coins = 50;
                HashMap<String, Double> ingredientsCustomerHas = Utility.createIngredientsCustomerHas();
    
                //displaying inventory
                if (choosenOption == 2){
                    System.out.println("\nYou currently have " + coins + " coins.");
                    System.out.println("The current items in your inventory are:");
                    for (String i: ingredientsCustomerHas.keySet()){
                        System.out.println(" -Ingredient: " + i + ", Quantity: " + ingredientsCustomerHas.get(i));
                    }
                }
    
                List<IngredientSupply> Supply = new ArrayList<>();
                Supply.add(new IngredientSupply("Flour", 10, 5));
                Supply.add(new IngredientSupply("Milk", 5, 5));
    
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
                                int price = Utility.calculatePrice(Supply.get(i).getAmount(), Supply.get(i).getPrice(), buyAmount);
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

                List<Customer> customers =Utility.createCustomers();
                for (int i =0 ; i< customers.size(); i++){
                        System.out.println("\n"+customers.get(i));
                    }
                }

                if (choosenOption == 5) {
                    System.out.println("What would you like to make?");
                    scanner.nextLine();
                    String dish = scanner.nextLine();
                    ArrayList<String> Menu = Utility.createMenu();
                    for (int i =0; i<Menu.size(); i++) {
                        if (Menu.get(i).equals(dish)){
                            FileHandling file = new FileHandling();
                            file.writeFileForRecipes();
                            file.readRecipesFile(dish);
                            System.out.println("\nHow many would you like to make?");
                            int itemNumber = scanner.nextInt();
                            System.out.println("testing :");
                            file.checkIfItemCanBeCreated(dish,itemNumber,ingredientsCustomerHas);
                        }
                    }
                }

                if (choosenOption == 6){

                
                    // this will be looped until each customer is served
                    System.out.println("\nOption 1: Serve next customer");
                    System.out.println("Option 2: View queue and choose customer");
                    System.out.println("Select from 1-3");
                    int servingCustomerOption = scanner.nextInt();
                    scanner.nextLine();

                    if (servingCustomerOption == 1 ){

                        // might need to loop for invalid arguements?
                        List<Customer> customers =Utility.createCustomers();
                        
                        for (int i =0 ; i< customers.size(); i++){
                            
                            int counter = 0;
                            System.out.println("\n"+customers.get(i));
                            System.out.println("Serve this customer? (Y/N)");
                            String serveOption = scanner.next();
                            String foodDesiredByCustomer = customers.get(i).getItem();

                            if (serveOption.equals("Y")) {
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

                            if (serveOption.equals("N")) {
                                System.out.println("\n5 coins have been deducted.");
                                System.out.println("\nGoing back to the options menu....\n");
                                break;
                            }
                        }
                    }

                    if (servingCustomerOption == 2) {
                        
                    }
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

