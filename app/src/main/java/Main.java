
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Enter your name");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.println("Welcome to Cafe Manager " +username+ "!");
        //explain how to play the game here?
        System.out.println("First, you will need to check the forecast to know which customers you will be serving today." 
                            +"Depending on your inventory, you can purchase from suppliers and stock up the ingredients"
                            + "or you can start serving them straight away. You can look at the menu to see if you're unsure"
                            +"what each recipe requires. For each customer you serve, you gain a point");
        System.out.println("The options bellow will allow you to navigate to different sections of the game, good luck " +username);
        System.out.println("Option 1: see forecast");
        System.out.println("Option 2: see inventory");
        int choosenOption = scanner.nextInt();
        if (choosenOption == 1) {
            //generates random number from 0, 1, 2
            int randomNumber = (int)(Math.random()*3) ;
            switch (randomNumber) {
                case 0:
                    System.out.println("Today is a rainy day with slow foot traffic. People linger around the cafe creating a really cozy atmosphere.");
                    System.out.println("In demand we have:");
                    System.out.println("Hot drinks - lattes, cappuccinos, hot chocolate, chai");
                    System.out.println("Comfort bakes - warm pastries, banana bread, soup if available.");
                    //what does break do here?
                    break;
                case 1:
                    System.out.println("Rush hour sucks! Everything is always so fast-paced since everyone wants food to takeout and is in a hurry.");
                    System.out.println("In demand we have:");
                    System.out.println("Quick coffees - americanos, drip/filter, flat whites; pre-batched cold brew.");
                    System.out.println("Grab-and-go food - croissants, breakfast sandwiches, yogurt pots.");
                    break;
                case 2:
                    System.out.println("There's so many people in today on a weekend. The room is full of couples and friend chattering their morning away.");
                    System.out.println("In demand we have:");
                    System.out.println("Specialty drinks - flavored lattes, matcha, iced options even in cooler weather.");
                    System.out.println("Brunch items - avocado toast, pastries, cakes, shareables.");
                    break;
                default:
                    break;
            }
        }
        if (choosenOption == 2){
            
        }

        scanner.close();
        
    }
}
