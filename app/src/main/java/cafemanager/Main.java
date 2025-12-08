package cafemanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in); 
    public static List<FoodInventory> dishInventory = Utility.createDishInventory();
    public static List<Customer> customers =Utility.createCustomers();
    public static HashMap<String, Double> ingredientsCustomerHas = Utility.createIngredientsCustomerHas();
    public static List<Customer> customersUserHasServed = new ArrayList<>();

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

        public static void messageForNegativeCoins(int coins) {
        String message = """
                    ╔══════════════════════════════════════╗
                    ║               GAME OVER              ║
                    ╟──────────────────────────────────────╢
                    ║                                      ║
                    ║  ˚₊‧꒰❀ The café is in debt… ❀꒱‧₊˚  ║
                    ║                                      ║
                    ║   You now have %d coins**… yikes!    ║
                    ║  The target was 20 coins, so…        ║
                    ║              (╥﹏╥)                  ║
                    ║                                      ║
                    ║     The accountant is crying…        ║
                    ║                                      ║
                    ╚══════════════════════════════════════╝
                    """;
        System.out.println(message.formatted(coins));
    }

    public static void messageIfUserWins(int coins, String username) {
            String message = """

                    ╔══════════════════════════════════════╗
                    ║                YOU WIN!              ║
                    ╟──────────────────────────────────────╢
                    ║                                      ║
                    ║   ✧˖° Congrats, Manager %s! °˖✧      ║
                    ║                                      ║
                    ║   The café is thriving and cozy ★    ║
                    ║     Customers adore your food!       ║
                    ║                                      ║
                    ║     ₊˚⊹🌟 Enjoy your success! 🌟⊹˚₊  ║
                    ║                                      ║
                    ╚══════════════════════════════════════╝
                    """;
            System.out.println(message.formatted(username));
        
    }

    public static void checkCoinsIfUserLeavesEarly(int coins, String username) {
            String message = """

            ╔═══════════════════════════════════════╗
            ║               GAME OVER               ║
            ╟───────────────────────────────────────╢
            ║                                       ║
            ║ ˚₊‧꒰❀ The café lights go dark… ❀꒱‧₊˚║
            ║                                       ║
            ║     You earned only %d coins…...       ║
            ║  You needed 20 coins to stay open     ║
            ║            .·°՞(っ-ᯅ-ς)՞°·.           ║
            ║                                       ║
            ║      Maybe next time, Manager %s…  ║
            ║                                       ║
            ╚═══════════════════════════════════════╝
            """;
        System.out.println(message.formatted(coins, username));
        
    }

    public static boolean checkCoins(int coins, String username){
        if (coins >= 20){
            messageIfUserWins(coins, username);
            return false;
        }
        else if (coins < 0) {
            messageForNegativeCoins(coins);
            return false;
        }
        else return true;
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
                ║    1) See dishes         🍽️           ║         
                ║    2) ingredients    🧺              ║
                ║    3) See your points    ✨          ║
                ║    4) Back to main menu              ║
                ║                                      ║
                ╚══════════════════════════════════════╝
            """);
            System.out.println("Select 1-4");
            int inventoryOption = handleIntUserInput();
            if (inventoryOption > 0 && inventoryOption < 5){
            
                switch (inventoryOption) {
                    case 1:
                        for (int i = 0; i < dishInventory.size(); i++ ){
                            System.out.println(" - Dish: " +dishInventory.get(i).getName() + ", Quantity: " + dishInventory.get(i).getQuantity());
                        }
                        System.out.println("\nPress Enter to return to Inventory");
                        scanner.nextLine();
                        break;
                    case 2:
                        for (String i : ingredientsCustomerHas.keySet()){
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
    
    public static int buyIngredients(int coins) {
        List<IngredientSupply> ingredientSupply = Utility.createIngredientsCustomerCanBuyFrom();
        System.out.println("\nThese are the options you can buy from:");
        for (int i = 0 ; i < ingredientSupply.size(); i++){
            System.out.println(ingredientSupply.get(i));
        }
        //want to loop this part?
        System.out.println("\nWhat would you like to buy?\n");
        String buyOption = handleStringUserInput();
            
        int itemsNotPresentInSupply =0; 
        for (int i = 0; i < ingredientSupply.size(); i++){
            if (ingredientSupply.get(i).getName().equals(buyOption)) {
                
                System.out.println("\nYou can buy " + ingredientSupply.get(i).getQuantity() + " for " + ingredientSupply.get(i).getPrice() + " coins.");
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
                            ingredientSupply.get(i).reduceQuantity(buyAmount);
                            Double buyAmountDouble = (double) buyAmount;
                            Utility.createAndUpdateInventoryIngredients(buyOption, ingredientsCustomerHas, buyAmountDouble);
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
        return coins;
    }

    public static void makeFood() {
        System.out.println("\nWhat would you like to make?");
        String dish = handleStringUserInput();
        ArrayList<String> menu = Utility.createMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).equals(dish)){
                FileHandler file = new FileHandler();
                file.writeFileForRecipes();
                file.readRecipesFile(dish);
                System.out.println("\nHow many would you like to make?");
                int itemNumber = handleIntUserInput();
                file.checkIfItemCanBeCreated(dish,itemNumber,ingredientsCustomerHas, dishInventory);
            }
        }
    }

    public static int serveNextCustomer(int coins) {
        for (int i = 0 ; i < customers.size(); i++){
            int counter = 0;
            System.out.println("\n"+customers.get(i));
            System.out.println("Serve this customer? (Y/N)");
            String serveOption = handleStringUserInput();
            String foodDesiredByCustomer = customers.get(i).getItem();
            
            if (serveOption.equalsIgnoreCase("Y")) {
                coins += Utility.serveCustomer(counter, dishInventory, foodDesiredByCustomer, customers, i);
            }
            
            if (serveOption.equalsIgnoreCase("N")) {
                coins = coins -5;
                if (coins > 0) {
                    System.out.println("\n5 coins have been deducted.");
                    System.out.println("\nGoing back to the options menu....\n");
                }
                break;
            }
        }
        return coins;
    }

    public static int serveChosenCustomer(int coins) {

        int customerPosition = 1;
        for (int i = 0; i < customers.size(); i++){
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
            coins += Utility.serveCustomer(0, dishInventory, foodDesiredByCustomer, customers, chosenCustomer-1);
        }
            
        if (serveOption.equalsIgnoreCase("N")) {
            coins = coins - 5;
            if (coins > 0){
                System.out.println("\n5 coins have been deducted.");
                System.out.println("\nGoing back to the options menu....\n");
            }
        }
        return coins;
    }

    public static int menuHandler(int chosenOption, int coins, String username) {
        boolean continuePlaying = true;
        while (continuePlaying) {
            if (chosenOption > 0 && chosenOption < 7) {
                switch (chosenOption) {
                    case 1:
                        Utility.createForecast();
                        break;
                    case 2:
                        inventoryDisplay();
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
                        System.out.println("""
                                ╔══════════════════════════════════════╗
                                ║            Customer Queue            ║
                                ╟──────────────────────────────────────╢
                                ║                                      ║
                                ║ 1) Serve next customer               ║
                                ║ 2) View queue and choose customer    ║
                                ║                                      ║
                                ╚══════════════════════════════════════╝
                                """);
                        System.out.println("\n Select from 1 or 2\n");
                        int servingCustomerOption = handleIntUserInput();
                        switch (servingCustomerOption) {
                            case 1:
                                coins = serveNextCustomer(coins);
                                continuePlaying = checkCoins(coins, username);
                                break;
                            case 2:
                                coins = serveChosenCustomer(coins);
                                continuePlaying = checkCoins(coins, username);
                        }
                }
                break;
            }
            else System.out.println("Please select from 1-6");
        }
        return coins;
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
            String userInputWithoutWhiteSpace = userInput.replaceAll(" ", "");
            if (userInputWithoutWhiteSpace.matches("^[a-zA-Z]*$")){
                return userInput;
            }
            else {System.out.println("Enter a valid string");}
            }
    }

    public static void main(String[] args) {
        // variables created:
        int coins = 0;

        System.out.println("\nEnter your name\n");
        String username = scanner.nextLine();
        
        //greets and introduces the user to the game
        FileHandler introfile = new FileHandler();
        introfile.writeIntroductionFile(username);
        introfile.readIntroductionFile();

        System.out.println("\nThe options bellow will allow you to navigate to different sections of the game, good luck " +username);

        System.out.println("Would you like to continue? Y/N");
        String proceed = handleStringUserInput();
    
        while (proceed.equalsIgnoreCase("Y")) {
            if (coins >= 0) {
                showOptionMenu();
                int chosenOption = handleIntUserInput();
                coins = menuHandler(chosenOption, coins,username);
                if (coins >= 0) {
                    System.out.println("\nWould you like to continue? Y/N");
                    proceed = handleStringUserInput();
                }
                else {
                    break;
                }
            }
        }
        if (proceed.equalsIgnoreCase("N")) {
            checkCoinsIfUserLeavesEarly(coins, username);
        }
        scanner.close();       
    }
}


