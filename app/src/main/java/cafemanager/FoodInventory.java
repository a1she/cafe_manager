package cafemanager;

public class FoodInventory {

    private String itemName;
    private int itemQuantity;

    public FoodInventory(String itemName, int itemQuantity){
        this.itemName = itemName;
        this.itemQuantity =itemQuantity;
    }

    public String getFoodName() {
        return itemName;
    }

    public int getItemQuantity () {
        return itemQuantity;
    }

    public void setItemQuantity( int totalAmount) {
        this.itemQuantity =totalAmount;
    }

}
