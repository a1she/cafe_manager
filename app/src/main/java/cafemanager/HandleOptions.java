package cafemanager;
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
