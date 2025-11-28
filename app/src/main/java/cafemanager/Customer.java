package cafemanager;

public class Customer {

    private String name;
    private String item; 
    private int amount;

    public Customer(String name, String item, int amount){
        this.name=name;
        this.item =item;
        this.amount =amount;
    }

    public String toString(){
        return name + " wants " + amount+ " " +item + ".";
    }

    public String printAsList() {
        return name + " (" + amount + ") item/items";
    }
    
    public String getName() {
        return name;
    }

    public String getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }


}
