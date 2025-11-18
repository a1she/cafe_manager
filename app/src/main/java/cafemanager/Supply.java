package cafemanager;

public class Supply {

    private String name;
    private int price;
    private int amount;

    public Supply(String name, int price, int amount){
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String toString(){
        return "Item - Name: " + name + ", Price: " + price + ", Amount: " + amount;
    }

    public String getName(){
        return name;
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
