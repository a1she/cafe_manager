package cafemanager;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void returnFalseGivenNegativeCoins() {
        assertFalse(Main.checkCoins(-1, "Ashley"));
        assertFalse(Main.checkCoins(-12000, "Ashley"));
    }

    @Test
    public void returnFalseGiven20OrMoreCoins() {
        assertFalse(Main.checkCoins(20, "Ashley"));
        assertFalse(Main.checkCoins(21, "Ashley"));
        assertFalse(Main.checkCoins(40000, "Ashley"));
    }

    @Test
    public void showDishesGivenUserInputOfOneForDisplayingInventory(){
        
        
        

    }

}
