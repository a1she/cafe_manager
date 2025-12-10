package cafemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

        public int chooseCustomerToServe(){
            return 2;
        }

        public String dishCustomerWantsToMake(){
            return "Hot Chocolate";
        }
        public int dishAmountCustomerWantsToMake() {
            return 2;
        }

        public String ingredientCustomerWantsToBuy() {
            return "Coffe";
        }

        public int ingredientAmountCustomerWantsToBuy(){
            return 3;
        }

        public boolean doesCustomerWantToBuyThis(){
            boolean result = answers[index];
            if (index < answers.length){
                index++;
            }
            return result;
        }
    }

    @BeforeEach
    public void setUp(){

        // create Dish inventory :
        List<FoodInventory> foodInventory = new ArrayList<>();
        foodInventory.add(new FoodInventory("Hot Chocolate", 2));
        foodInventory.add(new FoodInventory("Chocolate Croissant", 2));
        foodInventory.add(new FoodInventory("Brownie", 2));
        foodInventory.add(new FoodInventory("Latte", 2));
        foodInventory.add(new FoodInventory("Cheese Toastie", 2));

    }

    @Test
    public void returnFalseGivenNegativeCoins() {
        HandleOptions mockHandleOptions = new HandleOptions();
        assertFalse(mockHandleOptions.checkCoins(-1, "Ashley"));
        assertFalse(mockHandleOptions.checkCoins(-12000, "Ashley"));
    }

    @Test
    public void returnFalseGiven20OrMoreCoins() {
        assertFalse(Main.checkCoins(20, "Ashley"));
        assertFalse(Main.checkCoins(21, "Ashley"));
        assertFalse(Main.checkCoins(40000, "Ashley"));
    }


    @Test
    public void returnUpdatedCoinsGivenCorrectDishAndCorrectAmount(){

        boolean [] answers = {true};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);

        HandleOptions mockHandleOptions = new HandleOptions();
        int actual = mockHandleOptions.buyIngredients(10, mockCustomerDecisions);

        assertEquals(4, actual);
    }

    @Test
    public void returnCoinsGivenUserDoesNotWantToBuyItem(){

        boolean [] answers = {false};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);

        HandleOptions mockHandleOptions = new HandleOptions();
        int actual = mockHandleOptions.buyIngredients(10, mockCustomerDecisions);

        assertEquals(10, actual);
    }



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

    @Test
    public void returnUpdatedCoinsGivenUserHasServedTheirChosenCustomer(){
        List<Customer> mockedCustomers = new ArrayList<>();
        mockedCustomers.add(new Customer("Benjamin", "Brownie", 1));
        mockedCustomers.add(new Customer("Tommy", "Hot Chocolate", 2));
        mockedCustomers.add(new Customer("Lily", "Cheese Toastie", 2));
        mockedCustomers.add(new Customer("Tim", "Brownie", 1));

        //when creating a mock, predefined the chosen customer as the 2nd one in the list
        boolean [] answers = {true};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);

        HandleOptions mockHandleOptions = new HandleOptions();
        int actual = mockHandleOptions.serveChosenCustomer(0, mockedCustomers, mockCustomerDecisions);

        assertEquals(4, actual);
    }

    @Test
    public void returnNegative5GivenUserHasDecidedNotToServeChosenCustomer(){
        List<Customer> mockedCustomers = new ArrayList<>();
        mockedCustomers.add(new Customer("Benjamin", "Brownie", 1));
        mockedCustomers.add(new Customer("Tommy", "Hot Chocolate", 2));
        mockedCustomers.add(new Customer("Lily", "Cheese Toastie", 2));
        mockedCustomers.add(new Customer("Tim", "Brownie", 1));

        //when creating the mock decision maker, predefined the chosen customer as the 2nd one in the list
        boolean [] answers = {false};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);

        HandleOptions mockHandleOptions = new HandleOptions();
        int actual = mockHandleOptions.serveChosenCustomer(0, mockedCustomers, mockCustomerDecisions);

        assertEquals(-5, actual);
    }

    //TODO:test make food()



}
