package cafemanager;

import java.io.File;
import java.io.FileWriter;

import java.util.Scanner;
import java.util.HashMap;
import java.util.List;

/* This class handles files by reading and writing them */

public class FileHandler {

    // this function creates the introduction file and loads the data into the file
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
            System.out.println("Issue with writing the text file");
        }
    }

    //this function loads the data from the introduction file and displays it to the user
    public void readIntroductionFile() {
        File myObj = new File("introduction.txt");

        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
            //myReader.close();
            }
        } catch (Exception e) {
            System.out.println("Issue with writing the text file");
            e.printStackTrace();
        }
    }

    //searches for the dish within the recipe text file and prints the recipe
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
            System.out.println("Issue with reading the recipe text file");
            e.printStackTrace();
        }
    }


    /* this function checks if the user has enough ingredients to create the dish item by comparing their ingredients stock with the amount needed in the text file */

    public void checkIfItemCanBeCreated(String dish, int number, HashMap<String, Double> Ingredients, List<FoodInventory> dishInventories) {
        File myObj = new File("recipes.txt");
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                String data =myReader.nextLine();
                if (data.contains(dish)){
                    int enoughIngredients = 0;
                    do {
                        while (myReader.hasNextLine()){
                            data= myReader.nextLine();
                            if (data.startsWith("Recipe: ")) {
                                break;
                            }
                            else if (data.contains("-")){
                                data = data.replaceAll(" ","");
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
                    
                    //if the user has enough ingredients then create the dish
                    // the each recipe contains 3 ingredients in the recipe text file
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

