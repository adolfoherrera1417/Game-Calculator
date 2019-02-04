/***********************************************************
 * Created by:   Adolfo Herrera                                      
 *
 * Date:     Feb 02, 2019             
 *
 * Purpose:  Team Building Class.
 * This class will allow you to enter as many names as you
 * need and will generate two random teams.
 * Most useful feature is that it has a simple integration
 * with JavaFX it will return all the teams in a row feature manner
 * so that they can be added to a VBox object.
 ********************************************************/

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class teamBuilder {

    /**
     * One main array is required to store all the names entered.
     * Two sub lists are created from main array.
     *
     */

    private static ArrayList<String> allNames = new ArrayList<String>();
    private List<String> teamA,teamB;
    private ArrayList<HBox> rows = new ArrayList<HBox>();

    /**
     * Generates the teams into rows and columns using HBox and Labels
     *
     */
    public ArrayList<HBox> getTeams() {
        balanceTeams();
        for (int i = 0; i < teamA.size(); i++) {
            HBox row = new HBox(50);
            Label name1 = new Label(teamA.get(i));
            Label name2 = new Label(teamB.get(i));
            row.getChildren().addAll(name1,name2);
            row.setAlignment(Pos.CENTER);
            rows.add(row);
        }

        return rows;
    }

    /**
     * Enters the names into array
     *
     */
    public void enterName(String name) {
        allNames.add(name);
    }

    /**
     *  Clears all the names entered into form
     */
    public void reset() {
        allNames.clear();
    }

    /**
     *  Properly evens out teams
     */
    private void balanceTeams() {
        randomizeTeam();
        if (allNames.size() % 2 != 0) {
            allNames.add("");
        }
        teamA = allNames.subList(0, allNames.size()/2);
        teamB = allNames.subList(allNames.size()/2, allNames.size());
    }

    /**
     * Shuffle function will randomly shuffle the array containing all the names entered
     * Shuffling algorithms was used from:
     * //https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
     */
    private void randomizeTeam() {
        // Creating a object for Random class
        Random r = new Random();

        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = allNames.size()-1; i > 0; i--) {

            // Pick a random index from 0 to i
            int j = r.nextInt(i+1);

            // Swap arr[i] with the element at random index
            String temp = allNames.get(i);
            allNames.set(i,allNames.get(j));
            allNames.set(j,temp);
        }
    }
}
