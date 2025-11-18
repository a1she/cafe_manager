package cafemanager;

import java.io.File;
import java.io.FileWriter;

import java.util.Scanner;

public class FileHandling {

    public void WriteFileForRecipes(){
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

    public void ReadRecipesFile() {

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
}
