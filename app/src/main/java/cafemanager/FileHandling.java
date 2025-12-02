package cafemanager;

import java.io.File;
import java.io.FileWriter;

import java.util.Scanner;
import java.util.HashMap;
import java.util.List;

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
                                "╚══════════════════════════════════════╝\n" + //
                                "");
            writer.write("\nYou begin with 0 points!\n" + //
                                "Earn points by running your café — reach 20 to win!\n" + //
                                "But be careful… if your points fall below 0, your café closes immediately.");   
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

    //when adding new recipe, can't have more than one word for ingredients due to whitespace reduction
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
                                "Ingredients  : Quantity\n" + //
                                " - Dough     | 2.0\n" + //
                                " - Butter    | 0.75\n" + //
                                " - Chocolate | 1.0\n" + //
                                "Steps: milk + cocoa_powder + sugar\n" + //
                                "\nRecipe: Cheese Toastie\n" + //
                                "\n" + //
                                "Ingredients    : Quantity\n" + //
                                " - Butter      | 0.5\n" + //
                                " - Cheese      | 1.0\n" + //
                                " - Bread_Slice | 2.0\n" + //
                                "Steps: butter + cheese + bread slice\n" + //
                                "\nRecipe: Brownie\n" + //
                                "\n" + //
                                "Ingredients: : Quantity\n" + //
                                " - Sugar     | 0.5\n" + //
                                " - Butter    | 0.5\n" + //
                                " - Chocolate | 1.0\n" + //
                                "Steps: sugar + butter + chocolate  \n" + //
                                "\nRecipe: Latter\n" + //
                                "\n" + //
                                "Ingredients: : Quantity\n" + //
                                " - Milk      | 1.5\n" + //
                                " - Coffe     | 1.0\n" + //
                                " - Sugar     | 0.25\n" + //
                                "Steps: milk + coffe + sugar\n" + //
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
    public void checkIfItemCanBeCreated(String dish, int number, HashMap<String, Double> Ingredients, List<FoodInventory> dishInventories) {
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
                                //because of the replace all i can't have a space for an ingredient with more than 1 name e.i cocoa powder != cocoapowder
                                int index = data.indexOf("|");
                                String ingredientName = data.substring(1, index);
                                String ingredientQuantity = data.substring(index+1, data.length());
                                Double inDouble = Double.parseDouble(ingredientQuantity) * number ;
                                for (String i: Ingredients.keySet()) {
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
                        Utility.createAndUpdateInventoryDishes(dishInventories, dish, number);
                        System.out.println("successfully created " +number + " of " + dish);
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

