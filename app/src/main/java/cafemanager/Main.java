package cafemanager;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in); 
    public static CustomerDecision customerDecision = new CustomerDecision();
    public static HandleOptions handleOptions = new HandleOptions(new CustomerDecision(), 0, Utility.createDishInventory(),Utility.createCustomers(), Utility.createIngredientsCustomerHas());


    public static void showOptionMenu() {
        System.out.println("""
                ╔══════════════════════════════════════╗
                ║             Café Manager             ║
                ╟──────────────────────────────────────╢
                ║                                      ║
                ║     1) See forecast ˚ ☁️⋅♡𓂃 ࣪ ִֶָ☾.☂      ║
                ║     2) See inventory 🛒              ║
                ║     3) Buy ingredients 💰🛍           ║
                ║     4) See customers 🧑‍🤝‍🧑          ║
                ║     5) Make food ‧₊˚ ⋅ 𓐐𓎩 ‧₊˚⋅       ║
                ║     6) Serve customer (˘▽˘)っ 𓌉◯𓇋    ║
                ║                                      ║
                ╚══════════════════════════════════════╝
                """);
        System.out.println(" \nSelect from 1-6");
    }

        public static void checkCoinsIfUserLeavesEarly(int coins, String username) {
            String message = """

            ╔═══════════════════════════════════════╗
            ║               GAME OVER               ║
            ╟───────────────────────────────────────╢
            ║                                       ║
            ║ ˚₊‧꒰❀ The café lights go dark… ❀꒱‧₊˚║
            ║                                       ║
            ║     You earned only %d coins…...       ║
            ║  You needed 20 coins to stay open     ║
            ║            .·°՞(っ-ᯅ-ς)՞°·.           ║
            ║                                       ║
            ║      Maybe next time, Manager %s…  ║
            ║                                       ║
            ╚═══════════════════════════════════════╝
            """;
        System.out.println(message.formatted(coins, username));
        
    }

    public static void main(String[] args) {
        // variables created:
        int coins = 0;

        System.out.println("\nEnter your name\n");
        String username = scanner.nextLine();
        
        //greets and introduces the user to the game
        FileHandler introfile = new FileHandler();
        introfile.writeIntroductionFile(username);
        introfile.readIntroductionFile();

        System.out.println("\nThe options bellow will allow you to navigate to different sections of the game, good luck " +username);

        System.out.println("Would you like to continue? Y/N");
        String proceed = Utility.handleStringUserInput(scanner);
    
        while (proceed.equalsIgnoreCase("Y")) {
            if (coins >= 0) {
                showOptionMenu();
                int chosenOption = Utility.handleIntUserInput(scanner);
                coins = handleOptions.menuHandler(chosenOption, coins,username);
                if (coins >= 0) {
                    System.out.println("\nWould you like to continue? Y/N");
                    proceed = Utility.handleStringUserInputForYesorNo(scanner);
                }
                else {
                    break;
                }
            }
        }
        if (proceed.equalsIgnoreCase("N")) {
            checkCoinsIfUserLeavesEarly(coins, username);
        }
        scanner.close();       
    }
}


