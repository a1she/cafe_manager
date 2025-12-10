package cafemanager;

import java.util.Scanner;

public class CustomerDecision implements CustomerDecisionMaker {


    private Scanner scanner;

    public boolean shouldServeCustomer(Customer customer) {
        System.out.println(customer.toString());
        System.out.println("Serve this customer? (Y/N)");
        String serveOption = Utility.handleStringUserInputForYesorNo(scanner);
        if (serveOption.equalsIgnoreCase("Y")){
            return true;
        }
        else {return false;}
    }
    
    public int chooseCustomerToServe(){
        System.out.println("Choose customer from the list");
        int chosenCustomer = Utility.handleIntUserInput(scanner);
        return chosenCustomer;
    }

    public String dishCustomerWantsToMake() {
        System.out.println("\nWhat would you like to make?");
        String dish = Utility.handleStringUserInput(scanner);
        return dish;
    }

    public int dishAmountCustomerWantsToMake(){
        System.out.println("\nHow many would you like to make?");
        int itemNumber = Utility.handleIntUserInput(scanner);
        return itemNumber;
    }

    public String ingredientCustomerWantsToBuy() {
        System.out.println("\nWhat would you like to buy?\n");
        String purchaseOption = Utility.handleStringUserInput(scanner);
        return purchaseOption;
    }

    public int ingredientAmountCustomerWantsToBuy(){
        System.out.println("How many would you like to buy?");
        int purchaseAmount = Utility.handleIntUserInput(scanner);
        return purchaseAmount;
    }

    public boolean doesCustomerWantToBuyThis(){
        System.out.println("Are you sure you want to buy this? Y/N\n");
        String choice = Utility.handleStringUserInputForYesorNo(scanner);
        if (choice.equalsIgnoreCase("Y")){
            return true;
        }
        else {
            return false;
        }
    }

}
