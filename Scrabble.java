/***********************************************************
 * Created by:   Adolfo Herrera                                      
 *
 * Date:     Jan 28, 2019             
 *
 * Purpose:
 * Simple Scrabble Game! This class allows you to
 * create a game that deals 5 random letters. The objective is
 * to form as many words as you can within 1 minute!
 * ********************************************************/

import java.util.ArrayList;
import java.util.Random;

public class Scrabble {

    /**
     * Initialize and array with all 26 letters
     */
   static String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
   static int n = 26;

    /**
     * Will deal the amount of letters requested
     */

    public ArrayList<String> deal(int x) {
        shuffle();
        return gatherFiveLetters(x);
    }

    /**
     * Will return a sub list of a fully shuffled array that contains shuffled letters
     */

    private ArrayList<String> gatherFiveLetters (int x) {
        ArrayList<String> finalArray = new ArrayList<String>();
        for (int i = 0; i < x; i++) {
            finalArray.add(alphabet[i]);
        }
        return finalArray;
    }

    /**
     * Shuffle function will randomly shuffle the array containing all the letters and return
     * Shuffling algorithms was used from:
     * //https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
     */

    private void shuffle() {
        // Creating a object for Random class
        Random r = new Random();

        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = n-1; i > 0; i--) {

            // Pick a random index from 0 to i
            int j = r.nextInt(i+1);

            // Swap arr[i] with the element at random index
            String temp = alphabet[i];
            alphabet[i] = alphabet[j];
            alphabet[j] = temp;
        }
    }
}
