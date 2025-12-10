package cafemanager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Scanner;

public class HandleOptions implements CustomerDecisionMaker {

    private static List<FoodInventory> dishInventory = Utility.createDishInventory();
    private static HashMap<String, Double> ingredientsCustomerHas = Utility.createIngredientsCustomerHas();

    public static Scanner scanner;

    @Override
    public boolean shouldServeCustomer(Customer customer) {
        System.out.println(customer.toString());
        System.out.println("Serve this customer? (Y/N)");
        String serveOption = handleStringUserInputForYesorNo(scanner);
        if (serveOption.equalsIgnoreCase("Y")){
            return true;
        }
        else {return false;}
    }
    
    @Override
    public int chooseCustomerToServe(){
        System.out.println("Choose customer from the list");
        int chosenCustomer = handleIntUserInput(scanner);
        return chosenCustomer;
    }

    @Override
    public String dishCustomerWantsToMake() {
        System.out.println("\nWhat would you like to make?");
        String dish = handleStringUserInput(scanner);
        return dish;
    }

    @Override
    public int dishAmountCustomerWantsToMake(){
        System.out.println("\nHow many would you like to make?");
        int itemNumber = handleIntUserInput(scanner);
        return itemNumber;
    }

    @Override
    public String ingredientCustomerWantsToBuy() {
        System.out.println("\nWhat would you like to buy?\n");
        String purchaseOption = handleStringUserInput(scanner);
        return purchaseOption;
    }

    @Override
    public int ingredientAmountCustomerWantsToBuy(){
        System.out.println("How many would you like to buy?");
        int purchaseAmount = handleIntUserInput(scanner);
        return purchaseAmount;
    }

    @Override
    public boolean doesCustomerWantToBuyThis(){
        System.out.println("Are you sure you want to buy this? Y/N\n");
        String choice = handleStringUserInputForYesorNo(scanner);
        if (choice.equalsIgnoreCase("Y")){
            return true;
        }
        else {
            return false;
        }
    }

    public int buyIngredients(int coins, CustomerDecisionMaker customerDecision) {
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

    public static void makeFood(CustomerDecisionMaker customerDecision) {
        ArrayList<String> menu = Utility.createMenu();
        String dish = customerDecision.dishCustomerWantsToMake();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).equals(dish)){
                FileHandler file = new FileHandler();
                file.writeFileForRecipes();
                file.readRecipesFile(dish);
                int itemNumber = customerDecision.dishAmountCustomerWantsToMake();
                file.checkIfItemCanBeCreated(dish,itemNumber,ingredientsCustomerHas, dishInventory);
            }
        }
    }

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
