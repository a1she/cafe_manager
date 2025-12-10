package cafemanager;

public interface CustomerDecisionMaker {

    boolean shouldServeCustomer(Customer customer);
    int chooseCustomerToServe();
    String dishCustomerWantsToMake();
    int dishAmountCustomerWantsToMake();
    String ingredientCustomerWantsToBuy();
    int ingredientAmountCustomerWantsToBuy();
    boolean doesCustomerWantToBuyThis();

}
