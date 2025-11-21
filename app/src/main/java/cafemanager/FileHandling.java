package cafemanager;

import java.io.File;
import java.io.FileWriter;

import java.util.Scanner;

public class FileHandling {

    public void writeFileForRecipes(){
        try {
            FileWriter writer = new FileWriter("recipes.txt");
            writer.write("\nRecipe: Hot Chocolate\n" + //
                                "Ingredients:\n" + //
                                "\n" + //
                                " - milk | 2\n" + //
                                " - cocoa_powder | 1\n" + //
                                " - sugar | 0.5\n" + //
                                "Steps: milk + cocoa_powder + sugar\n" + //
                                "");
            writer.write("\nRecipe: Chocolate Croissant\n" + //
                                "Ingredients:\n" + //
                                "\n" + //
                                " - dough | 2\n" + //
                                " - butter | 0.75\n" + //
                                " - chocolate | 1\n" + //
                                "Steps: milk + cocoa_powder + sugar\n" + //
                                "");
            writer.close();         
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    public void readRecipesFile() {

        File myObj = new File("recipes.txt");

        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void writeIntroductionFile(String username) {
        try {
            FileWriter writer = new FileWriter("introduction.txt");
            writer.write("\n      Welcome to Cafe Manager " +username+ "!\n");
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
            // TODO: handle exception
        }
    }

    public void readIntroductionFile() {

        File myObj = new File("introduction.txt");

        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void checkIfItemCanBeMade(){
        File myFile = new File("recipes.txt");
        try (Scanner myRead = new Scanner(myFile)) {
            while (myRead.hasNext()) {
                String data = myRead.next();
                System.out.println(data);
            }


        } catch (Exception e) {
            // TODO: handle exception
        }
    }


}
