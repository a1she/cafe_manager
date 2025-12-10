package cafemanager;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class HandleOptionsTest {

    private static class MockCustomerDecisions implements CustomerDecisionMaker {
        private boolean[] answers;
        private int index = 0;
        
        public MockCustomerDecisions( boolean[] answers) {
            this.answers = answers;
        }

        public boolean shouldServeCustomer( Customer customer){
            boolean result = answers[index];
            if (index < answers.length){
                index++;
            }
            return result;
        }
    }

    @Test
    public void returnFalseGivenNegativeCoins() {
        assertFalse(Main.checkCoins(-1, "Ashley"));
        assertFalse(Main.checkCoins(-12000, "Ashley"));
    }

    @Test
    public void returnFalseGiven20OrMoreCoins() {
        assertFalse(Main.checkCoins(20, "Ashley"));
        assertFalse(Main.checkCoins(21, "Ashley"));
        assertFalse(Main.checkCoins(40000, "Ashley"));
    }

    // @Test
    // public void showDishesGivenUserInputOfOneForDisplayingInventory(){
    //     //Main mockMain = new Main();

    //     create fake scanner input for function
    //     String mockInput = "1/n";
    //     ByteArrayInputStream inputStream = new ByteArrayInputStream(mockInput.getBytes()); 
    //     Scanner mockScanner = new Scanner(inputStream); 

    //     Main.showInventory(0, mockScanner);
        
        
        

    // }

    @Test
    public void returnNegativeFiveAndBreakGivenUserRefusesToServeCustomer(){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Benjamin", "Brownie", 2));
        customers.add(new Customer("Tommy", "Brownie", 6));

        boolean [] answers = {false, false};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);

        HandleOptions mockHandleOptions = new HandleOptions();
        int actualResultForFalse = mockHandleOptions.serveNextCustomer(0, customers, mockCustomerDecisions);

        //since you break out of the loop, the 2nd user decision is ignored
        assertEquals(-5, actualResultForFalse);
    }

    @Test
    public void returnUpdatedCoinsGivenUserServingCustomers(){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Benjamin", "Brownie", 1));
        customers.add(new Customer("Tommy", "Hot Chocolate", 2));
        customers.add(new Customer("Lily", "Cheese Toastie", 2));
        customers.add(new Customer("Tim", "Brownie", 1));


        boolean [] answers = {true, true, true, true};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);

        HandleOptions mockHandleOptions = new HandleOptions();
        int actual = mockHandleOptions.serveNextCustomer(0, customers, mockCustomerDecisions);

        
        assertEquals(12, actual);
    }

    @Test
    public void returnUpdatedCoinsGivenDifferentUserServingChoices(){
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Benjamin", "Brownie", 1));
        customers.add(new Customer("Tommy", "Hot Chocolate", 2));
        customers.add(new Customer("Lily", "Cheese Toastie", 2));
        customers.add(new Customer("Tim", "Brownie", 1));


        boolean [] answers = {true, true, false, true};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);

        HandleOptions mockHandleOptions = new HandleOptions();
        int actual = mockHandleOptions.serveNextCustomer(0, customers, mockCustomerDecisions);

        //breaks out of the loop when serveCustomer() = false, therefore last true is ignored
        assertEquals(1, actual);
    }
    

}
