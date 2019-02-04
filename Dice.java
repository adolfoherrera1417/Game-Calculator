/**
 * @author Robert Lightfoot
 * @version 1.0
 * CSCE315 Example code
 * 1/16/2018
 *
 * @autor Adolfo Herrera
 * @version 1.1
 * Updated to include return the appropriate dice image once rolled.
 * 1/20/2018
 *
 */
//package Games;

import java.util.Random;
import java.util.Hashtable;

public class Dice  {

    private int sides;
    // Each number on dice associated with image
    private Hashtable<Integer, String> hashtable = new Hashtable<Integer, String>();

    /**
     * Default constructor,
     * Dice has 6 sides.
     */
    public Dice() {
        sides = 6;
        roll();
        hashtable.put(1,"img/dice/dice1.png");
        hashtable.put(2,"img/dice/dice2.png");
        hashtable.put(3,"img/dice/dice3.png");
        hashtable.put(4,"img/dice/dice4.png");
        hashtable.put(5,"img/dice/dice5.png");
        hashtable.put(6,"img/dice/dice6.png");
    }

    /**
     * Non default constructor,
     * @param side is the number of sides desired
     */
    public Dice(int side){
        sides = side;
        roll();
    }

    /**
     * Calling roll returns the rolled value.
     *@return a dice roll image address
     */
    public String roll(){
        Random val = new Random();
        return hashtable.get(val.nextInt(sides) + 1);
    }

}//end Dice