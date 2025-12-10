package cafemanager;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Scanner;

public class HandleOptions implements CustomerDecisionMaker {

    public static List<FoodInventory> dishInventory = Utility.createDishInventory();

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
