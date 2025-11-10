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

    public void outputSupply(){
        System.out.println("Item - Name: " + name + ", Price: " + price + ", Amount: " + amount);
    }
}
