package cafemanager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/* this class handles the functionality of the game */

public class HandleOptions {

    public List<Customer> customers =Utility.createCustomers();
    public static List<Customer> customersUserHasServed = new ArrayList<>();
    private List<FoodInventory> dishInventory = Utility.createDishInventory();
    private HashMap<String, Double> ingredientsCustomerHas = Utility.createIngredientsCustomerHas();
    public static Scanner scanner;
    public CustomerDecisionMaker customerDecision;
    public int coins;

    public HandleOptions( CustomerDecisionMaker customerDecision, int coins, List<FoodInventory> dishInventory, List<Customer> customers, HashMap<String, Double> ingredientsCustomerHas ) {
        this.customerDecision = customerDecision;
        this.coins = coins;
        this.dishInventory =dishInventory;
        this.customers = customers;
        this.ingredientsCustomerHas = ingredientsCustomerHas;
    }

    //calls the correct method function depending on the user option
    public int menuHandler(int chosenOption, int coins, String username) {
        boolean continuePlaying = true;
        while (continuePlaying) {
            if (chosenOption > 0 && chosenOption < 7) {
                switch (chosenOption) {
                    case 1:
                        Utility.createForecast();
                        break;
                    case 2:
                        showInventory(coins);
                        break;
                    case 3:
                        buyIngredients(coins, customerDecision);
                        break;
                    case 4:
                        for (int i =0 ; i< customers.size(); i++){
                            System.out.println("\n"+customers.get(i));
                        }
                        break;
                    case 5:
                        makeFood(customerDecision);
                        break;
                    case 6:
                        
                        System.out.println("\n Select from 1 or 2\n");
                        int servingCustomerOption = Utility.handleIntUserInput(scanner);
                        switch (servingCustomerOption) {
                            case 1:
                                coins = serveNextCustomer(coins, customers, customerDecision);
                                continuePlaying = checkCoins(coins, username);
                                break;
                            case 2:
                                coins = serveChosenCustomer(coins, customers, customerDecision);
                                continuePlaying = checkCoins(coins, username);
                        }
                }
                break;
            }
            else System.out.println("Please select from 1-6");
        }
        return coins;
    }
    
    //displays inventory such as dishes, ingredients and points
    public void showInventory(int coins) {
        boolean valid = true;
        while (valid){
            displayInventoryOptions();
            System.out.println("Select 1-4");
            int inventoryOption = Utility.handleIntUserInput(scanner);         

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

    // allows the user to buy ingredients from supply if the user has enough coins to buy the ingredients and then updates coins if needed
    public int buyIngredients(int coins, CustomerDecisionMaker customerDecision ) {
        List<IngredientSupply> ingredientSupply = Utility.createIngredientsCustomerCanBuyFrom();
        System.out.println("\nThese are the options you can buy from:");
        for (int i = 0 ; i < ingredientSupply.size(); i++){
            System.out.println(ingredientSupply.get(i));
        }

        String purchaseOption = customerDecision.ingredientCustomerWantsToBuy();
        int itemsNotPresentInSupply =0; 
        int purchaseAmount = customerDecision.ingredientAmountCustomerWantsToBuy();

        for (int i = 0; i < ingredientSupply.size(); i++){
            if (ingredientSupply.get(i).getName().equals(purchaseOption)) {

                System.out.println("\nYou can buy " + ingredientSupply.get(i).getQuantity() + " for " + ingredientSupply.get(i).getPrice() + " coins.");
                
                if (ingredientSupply.get(i).getQuantity() >= purchaseAmount) {

                    System.out.println("\nCalculating Cost...\n"); 
                    int price = Utility.calculatePrice(ingredientSupply.get(i).getQuantity(), ingredientSupply.get(i).getPrice(), purchaseAmount);
                    System.out.println("\nYour total cost is " + price + " coins.");
                    boolean choice = customerDecision.doesCustomerWantToBuyThis();
                    
                    if (choice){
                        if (coins>=price) {
                            coins = coins - price;
                            System.out.println("\nYou have successfully purchased " + ingredientSupply.get(i).getName() + " for " + price + " coins.\n");
                            System.out.println("\nYou have " +coins+ " coins left.\n");
                            ingredientSupply.get(i).reduceQuantity(purchaseAmount);
                            Double buyAmountDouble = (double) purchaseAmount;
                            Utility.createAndUpdateInventoryIngredients(purchaseOption, ingredientsCustomerHas, buyAmountDouble);
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

    //checks if the dish the user wants to make exists in the menu, if it does then it checks if the user has enough ingredients to make it
    public void makeFood(CustomerDecisionMaker customerDecision) {
        ArrayList<String> menu = Utility.createMenu();
        String dish = customerDecision.dishCustomerWantsToMake();
        int counter = 0;
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).equals(dish)){
                FileHandler file = new FileHandler();
                file.readRecipesFile(dish);
                int itemNumber = customerDecision.dishAmountCustomerWantsToMake();
                file.checkIfItemCanBeCreated(dish,itemNumber,ingredientsCustomerHas, dishInventory);
            }
            else counter++;
        }

        if (counter == menu.size()) {
            System.out.println("This dish doesn't exist");
        }
    }

    //loops through the list of customers, and checks if user wants to serve then. if they skip a customer then coins are reduced, if they serve then coins are increased
    public int serveNextCustomer(int coins, List<Customer> customers, CustomerDecisionMaker customerDecision) {
        for (int i = 0 ; i < customers.size(); i++){
            int counter = 0;
            if (customerDecision.shouldServeCustomer(customers.get(i))) {
                String foodDesiredByCustomer = customers.get(i).getItem();
                coins += Utility.serveCustomer(counter, dishInventory, foodDesiredByCustomer, customers, i);
            }
            else {
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

    //allows user to serve the chosen customer from a list
    public int serveChosenCustomer(int coins, List<Customer> customers, CustomerDecisionMaker customerDecision) {
        int customerPosition = 1;
        for (int i = 0; i < customers.size(); i++){
            System.out.println(customerPosition + ")" + customers.get(i).printAsList());
            customerPosition++;
        }
        int customerToServe = customerDecision.chooseCustomerToServe();
        String foodDesiredByCustomer = customers.get(customerToServe-1).getItem();
            
        if (customerDecision.shouldServeCustomer(customers.get(customerToServe-1))) {
            coins += Utility.serveCustomer(0, dishInventory, foodDesiredByCustomer, customers, customerToServe-1);
        }
        else {
            coins = coins - 5;
            if (coins > 0){
                System.out.println("\n5 coins have been deducted.");
                System.out.println("\nGoing back to the options menu....\n");
            }
        }
        return coins;
    }

    //checks the users coins
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



    public static void displayInventoryOptions() {
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
    }

    public static void displayServingOptions(){
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
    }
}
