package cafemanager;

public class IngredientSupply {

    private String ingredientName;
    private int price;
    private int amount;

    public IngredientSupply(String name, int price, int amount){
        this.ingredientName = name;
        this.price = price;
        this.amount = amount;
    }

    public String toString(){
        return "Item - Name: " + ingredientName + ", Price: " + price + ", Amount: " + amount;
    }

    public String getName(){
        return ingredientName;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public void setAmount(int purchase) {
        int total = amount - purchase;
        this.amount = total;
    }
}
