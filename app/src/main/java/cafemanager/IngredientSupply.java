package cafemanager;

public class IngredientSupply extends StockItem {
    private int price;

    public IngredientSupply(String name, int price, int amount){
        super(name, amount);
        this.price = price;
    }

    public String toString(){
        return "Item - Name: " + super.getName() + ", Price: " + price + ", Amount: " + super.getQuantity();
    }

    public int getPrice() {
        return price;
    }

}
