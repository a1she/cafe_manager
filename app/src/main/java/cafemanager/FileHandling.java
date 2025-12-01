package cafemanager;

import java.io.File;
import java.io.FileWriter;

import java.util.Scanner;
import java.util.HashMap;

public class FileHandling {
    
    public void writeIntroductionFile(String username) {
        try {
            FileWriter writer = new FileWriter("introduction.txt");
            writer.write("\n  ☆ Welcome to Café Manager " +username+ " (っ☕)っ \n");
            writer.write("\n╔══════════════════════════════════════╗\n" + //
                                "║           ☕ Café Manager ☕         ║\n" + //
                                "╟──────────────────────────────────────╢\n" + //
                                "║                                      ║\n" + //
                                "║ 📅 Today’s customers are on the way. ║\n" + //
                                "║ 🔎 Check the forecast to preview     ║\n" + //
                                "║    who’s arriving.                   ║\n" + //
                                "║ 🧺 Low on stock? Buy from suppliers. ║\n" + //
                                "║ 🍽️ Ready? Start serving now.          ║\n" + //
                                "║ 📜 Unsure about recipes? Open menu.  ║\n" + //
                                "║ ⭐ Earn points with every serve.     ║\n" + //
                                "║                                      ║\n" + //
                                "╚══════════════════════════════════════╝" + //
                                "");   
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readIntroductionFile() {

        File myObj = new File("introduction.txt");

        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
            //myReader.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeFileForRecipes(){
        try {
            FileWriter writer = new FileWriter("recipes.txt");
            writer.write("\nRecipe: Hot Chocolate\n" + //
                                "\n" + //
                                "Ingredients     : Quantity\n" + //
                                " - Milk         | 2.0\n" + //
                                " - Cocoa_Powder | 1.0\n" + //
                                " - Sugar        | 0.5\n" + //
                                "Steps: milk + cocoa powder + sugar" + //
                                "\nRecipe: Chocolate Croissant\n" + //
                                "\n" + //
                                "Ingredients: : Quantity\n" + //
                                " - Dough     | 2.0\n" + //
                                " - Butter    | 0.75\n" + //
                                " - Chocolate | 1.0\n" + //
                                "Steps: milk + cocoa_powder + sugar\n" + //
                                "");
            writer.close();         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //searches for the recipe within the recipe file
    public void readRecipesFile(String dish) {

        File myObj = new File("recipes.txt");

        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
            String data =myReader.nextLine();
            if (data.contains(dish)){
                do {
                    if (data.startsWith("Recipe: ")|| data.equals(null) ) {
                        break;
                    }
                    else {
                        data= myReader.nextLine();
                        System.out.println(data);
                    }
                    } while (!data.startsWith("Recipe: ")); 
                }
            }
        myReader.close();
    } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // need to search for the quantiy and check if they have that much in their ingredinents(hashmap contains name + int quanityt).
    public void checkIfItemCanBeCreated(String dish, int number, HashMap<String, Double> Ingredients) {

        File myObj = new File("recipes.txt");

        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                String data =myReader.nextLine();
                if (data.contains(dish)){
                    int enoughIngredients = 0;
                    do {
                        while (myReader.hasNextLine())
                        {
                            data= myReader.nextLine();
                            if (data.startsWith("Recipe: ")) {
                                break;
                            }
                            else if (data.contains("-")){
                                data = data.replaceAll(" ","");
                                //because of the replace all i can't have a space for an ingredient with more than 1 name e.i cocoa powder !+ cocoapowder
                                int index = data.indexOf("|");
                                String ingredientName = data.substring(1, index);
                                String ingredientQuantity = data.substring(index+1, data.length());
                                Double inDouble = Double.parseDouble(ingredientQuantity) * number ;
                                for (String i: Ingredients.keySet()) {
                                    //might change it to contains?
                                    if (i.equals(ingredientName)){
                                        if( Ingredients.get(i) >= inDouble){
                                            enoughIngredients++;
                                        }
                                    }
                                }            
                            }
                        }
                        break;
                    } while (!data.startsWith("Recipe: ")); 
                    if (enoughIngredients == 3) {
                        //maybe update what's already in inventory, if it doesn't exist then add it ;p
                        FoodInventory item = new FoodInventory(dish, number);
                        System.out.println("successfully created " +number + " of " + dish);
                        //TODO: ADD item to dish array list
                    }
                    else System.out.println("You don't have enough ingredients to make this.");
                }
            }
            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

