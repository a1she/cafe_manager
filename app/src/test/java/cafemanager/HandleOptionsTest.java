package cafemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

public class HandleOptionsTest {

    // this creates a mock of user inputs to allow effective unit testing 
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

    @Test
    public void returnFalseGivenNegativeCoins() {
        assertFalse(HandleOptions.checkCoins(-1, "Ashley"));
        assertFalse(HandleOptions.checkCoins(-12000, "Ashley"));
    }

    @Test
    public void returnFalseGiven20OrMoreCoins() {
        assertFalse(HandleOptions.checkCoins(20, "Ashley"));
        assertFalse(HandleOptions.checkCoins(21, "Ashley"));
        assertFalse(HandleOptions.checkCoins(40000, "Ashley"));
    }


    @Test
    public void returnUpdatedCoinsGivenCorrectDishAndCorrectAmount(){
        //boolean[] used to mock Y for doesCustomerWantToBuyThis() 
        boolean [] answers = {true};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);

        List<FoodInventory> foodInventory = new ArrayList<>();
        foodInventory.add(new FoodInventory("Hot Chocolate", 2));

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Alex", "Hot Chocolate", 2));

        HashMap<String, Double> ingredientsCustomerHas  = new HashMap<String, Double>();
        ingredientsCustomerHas.put("Cheese", 4.0);

        HandleOptions mockHandleOptions = new HandleOptions(mockCustomerDecisions, 0, foodInventory, customers,ingredientsCustomerHas);
        int actual = mockHandleOptions.buyIngredients(10, mockCustomerDecisions);
        assertEquals(4, actual);
    }

    @Test
    public void returnCoinsGivenUserDoesNotWantToBuyItem(){
        //boolean[] used to mock N for doesCustomerWantToBuyThis() 
        boolean [] answers = {false};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);

        List<FoodInventory> foodInventory = new ArrayList<>();
        foodInventory.add(new FoodInventory("Hot Chocolate", 2));

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Alex", "Hot Chocolate", 2));

        HashMap<String, Double> ingredientsCustomerHas  = new HashMap<String, Double>();
        ingredientsCustomerHas.put("Cheese", 4.0);

        HandleOptions mockHandleOptions = new HandleOptions(mockCustomerDecisions, 0, foodInventory, customers,ingredientsCustomerHas);
        int actual = mockHandleOptions.buyIngredients(10, mockCustomerDecisions);

        assertEquals(10, actual);
    }

    @Test
    public void returnNegativeFiveAndBreakGivenUserRefusesToServeCustomer(){
        //boolean[] used to mock N for shouldServeCustomer() 
        boolean [] answers = {false, false};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);
        
        List<FoodInventory> foodInventory = new ArrayList<>();
        foodInventory.add(new FoodInventory("Hot Chocolate", 2));
        
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Benjamin", "Brownie", 2));
        customers.add(new Customer("Tommy", "Brownie", 6));

        HashMap<String, Double> ingredientsCustomerHas  = new HashMap<String, Double>();
        ingredientsCustomerHas.put("Cheese", 4.0);

        HandleOptions mockHandleOptions = new HandleOptions(mockCustomerDecisions, 0, foodInventory, customers,ingredientsCustomerHas);
        int actualResultForFalse = mockHandleOptions.serveNextCustomer(0, customers, mockCustomerDecisions);

        //since you break out of the loop, the 2nd user decision is ignored
        assertEquals(-5, actualResultForFalse);
    }

    @Test
    public void returnUpdatedCoinsGivenUserServingCustomers(){
        //boolean[] used to mock Y for shouldServeCustomer() 
        boolean [] answers = {true, true, true, true};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);
        
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Benjamin", "Brownie", 1));
        customers.add(new Customer("Tommy", "Hot Chocolate", 2));
        customers.add(new Customer("Lily", "Cheese Toastie", 2));
        customers.add(new Customer("Tim", "Brownie", 1));

        List<FoodInventory> foodInventory = new ArrayList<>();
        foodInventory.add(new FoodInventory("Brownie", 2));
        foodInventory.add(new FoodInventory("Hot Chocolate", 2));
        foodInventory.add(new FoodInventory("Cheese Toastie", 2));

        HashMap<String, Double> ingredientsCustomerHas  = new HashMap<String, Double>();
        ingredientsCustomerHas.put("Cheese", 4.0);

        HandleOptions mockHandleOptions = new HandleOptions(mockCustomerDecisions, 0, foodInventory, customers,ingredientsCustomerHas);
        int actual = mockHandleOptions.serveNextCustomer(0, customers, mockCustomerDecisions);
        
        assertEquals(12, actual);
    }

    @Test
    public void returnUpdatedCoinsGivenDifferentUserServingChoices(){
        //boolean[] used to mock Y,Y,N,N for shouldServeCustomer() 
        boolean [] answers = {true, true, false, true};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Benjamin", "Brownie", 1));
        customers.add(new Customer("Tommy", "Hot Chocolate", 2));
        customers.add(new Customer("Lily", "Cheese Toastie", 2));
        customers.add(new Customer("Tim", "Brownie", 1));
        
        List<FoodInventory> foodInventory = new ArrayList<>();
        foodInventory.add(new FoodInventory("Brownie", 2));
        foodInventory.add(new FoodInventory("Hot Chocolate", 2));
        foodInventory.add(new FoodInventory("Cheese Toastie", 2));

        HashMap<String, Double> ingredientsCustomerHas  = new HashMap<String, Double>();
        ingredientsCustomerHas.put("Cheese", 4.0);

        HandleOptions mockHandleOptions = new HandleOptions(mockCustomerDecisions, 0, foodInventory, customers,ingredientsCustomerHas);
        int actual = mockHandleOptions.serveNextCustomer(0, customers, mockCustomerDecisions);

        //breaks out of the loop when serveCustomer() = false, therefore last true is ignored
        assertEquals(1, actual);
    }

    @Test
    public void returnUpdatedCoinsGivenUserHasServedTheirChosenCustomer(){
        //when creating a mock, predefined the chosen customer as the 2nd one in the list
        //boolean[] used to mock Y for shouldServeCustomer() 
        boolean [] answers = {true};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions(answers);
        
        List<FoodInventory> foodInventory = new ArrayList<>();
        foodInventory.add(new FoodInventory("Hot Chocolate", 2));

        List<Customer> mockedCustomers = new ArrayList<>();
        mockedCustomers.add(new Customer("Benjamin", "Brownie", 1));
        mockedCustomers.add(new Customer("Tommy", "Hot Chocolate", 2));
        mockedCustomers.add(new Customer("Lily", "Cheese Toastie", 2));
        mockedCustomers.add(new Customer("Tim", "Brownie", 1));

        HashMap<String, Double> ingredientsCustomerHas  = new HashMap<String, Double>();
        ingredientsCustomerHas.put("Cheese", 4.0);

        HandleOptions mockHandleOptions = new HandleOptions(mockCustomerDecisions, 0, foodInventory, mockedCustomers,ingredientsCustomerHas);
        int actual = mockHandleOptions.serveChosenCustomer(0, mockedCustomers, mockCustomerDecisions);

        assertEquals(4, actual);
    }

    @Test
    public void returnNegative5GivenUserHasDecidedNotToServeChosenCustomer(){
        //when creating the mock decision maker, predefined the chosen customer as the 2nd one in the list
        boolean [] answers = {false};
        MockCustomerDecisions mockCustomerDecisions = new MockCustomerDecisions( answers);
        
        List<FoodInventory> foodInventory = new ArrayList<>();
        foodInventory.add(new FoodInventory("Hot Chocolate", 2));

        List<Customer> mockedCustomers = new ArrayList<>();
        mockedCustomers.add(new Customer("Benjamin", "Brownie", 1));
        mockedCustomers.add(new Customer("Tommy", "Hot Chocolate", 2));
        mockedCustomers.add(new Customer("Lily", "Cheese Toastie", 2));
        mockedCustomers.add(new Customer("Tim", "Brownie", 1));

        HashMap<String, Double> ingredientsCustomerHas  = new HashMap<String, Double>();
        ingredientsCustomerHas.put("Cheese", 4.0);

        HandleOptions mockHandleOptions = new HandleOptions(mockCustomerDecisions, 0, foodInventory, mockedCustomers,ingredientsCustomerHas);
        int actual = mockHandleOptions.serveChosenCustomer(0, mockedCustomers, mockCustomerDecisions);

        assertEquals(-5, actual);
    }

}
