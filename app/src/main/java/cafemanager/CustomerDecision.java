package cafemanager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerDecision {

    private Scanner scanner;

    public boolean shouldServeCustomer(Customer customer) {
        System.out.println(customer.toString());
        System.out.println("Serve this customer? (Y/N)");
        String serveOption = handleStringUserInputForYesorNo(scanner);
        if (serveOption.equalsIgnoreCase("Y")){
            return true;
        }
        else {return false;}
    }
    
    public int chooseCustomerToServe(){
        System.out.println("Choose customer from the list");
        int chosenCustomer = handleIntUserInput(scanner);
        return chosenCustomer;
    }

    public String dishCustomerWantsToMake() {
        System.out.println("\nWhat would you like to make?");
        String dish = handleStringUserInput(scanner);
        return dish;
    }

    public int dishAmountCustomerWantsToMake(){
        System.out.println("\nHow many would you like to make?");
        int itemNumber = handleIntUserInput(scanner);
        return itemNumber;
    }

    public String ingredientCustomerWantsToBuy() {
        System.out.println("\nWhat would you like to buy?\n");
        String purchaseOption = handleStringUserInput(scanner);
        return purchaseOption;
    }

    public int ingredientAmountCustomerWantsToBuy(){
        System.out.println("How many would you like to buy?");
        int purchaseAmount = handleIntUserInput(scanner);
        return purchaseAmount;
    }

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

    public int handleIntUserInput(Scanner scanner) {
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

    public String handleStringUserInput(Scanner scanner) {
        while (true) {
            String userInput = scanner.nextLine();
            String userInputWithoutWhiteSpace = userInput.replaceAll(" ", "");
            if (userInputWithoutWhiteSpace.matches("^[a-zA-Z]*$")){
                return userInput;
            }
            else {System.out.println("Enter a valid string");}
            }
    }

    public String handleStringUserInputForYesorNo(Scanner scanner) {
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
