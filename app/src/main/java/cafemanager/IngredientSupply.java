package cafemanager;

public class IngredientSupply extends StockItem {

    private String ingredientName;
    private int price;
    private int amount;

    public IngredientSupply(String name, int price, int amount){
        super(name, amount);
        this.price = price;
    }

    public String toString(){
        return "Item - Name: " + ingredientName + ", Price: " + price + ", Amount: " + amount;
    }

    public int getPrice() {
        return price;
    }

}
