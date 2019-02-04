/**
 * @author Robert Lightfoot
 * @date 1/16/19
 * @rev A
 * A GUI demonstration
 *
 * @autor Adolfo Herrera
 * @version 1.1
 * @date 2/3/19
 * Updated to include 3 new games Scrabble, Stopwatch, and Team Builder
 *
 */

//TODO Give name specifics for each game

import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.concurrent.Task;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GameCalc extends Application {

    // field for label control
    private Label valueLabel;
    private Label wordSoupLetters;

    // TextFiled required to enter as many names needed in Team Building Object
    private TextField nameInput = new TextField();

    // field for Main Stage
    Stage window;

    // Total of 5 scenes: main menu, diceRollGame, Scrabble, StopWatch, Team Builder.
    Scene mainMenuScene, diceRollScene, stopWatchScene, scrabbleScene, teamBuilderScene;

    // This will be the main image object changing FOR DICE ONLY
    ImageView image;

    // Dice object
    Dice myDice = new Dice();

    // Stopwatch Object
    Stopwatch myWatch = new Stopwatch();

    // Scrabble Object
    Scrabble wordSoup = new Scrabble();

    //Thread needed for stopwatch to keep running
    Thread t1 = new Thread(myWatch,"watch");

    // Team Builder Object
    teamBuilder myTeam = new teamBuilder();
    //Array needed to remove new and old names onto display
    private static ArrayList<HBox> membersList = new ArrayList<HBox>();

    //TODO make layouts non-public

    VBox diceBox = new VBox(10);
    VBox stopWatchBox = new VBox(10);
    VBox scrabbleBox = new VBox(10);
    VBox teamBuilderBox = new VBox(10);

    // Main
    public static void main(String[] args) {
        //launch the Application
        launch(args);
    }//end main


    @Override
    public void start(Stage primaryStage) {
        //Timer Thread begins!
        t1.start();

        /**
         * Static Variables
         */

        // Set the stage title
        primaryStage.setTitle("Main Menu");

        // Note which stage will be changing
        window = primaryStage;

        // create a main menu label
        Label messageLabel = new Label("Pick a game and let's get started!");
        Label teaBuilderTitle = new Label("Type a name and hit enter, Once done hit make team!");

        /**
         * ALL GAMES MUST HAVE A BACK BUTTON TO MAIN MENU SCENE
         */
        Button goBackAfterDice = new Button("<- Main Menu");
        goBackAfterDice.setOnAction(e -> window.setScene(mainMenuScene));
        Button goBackAfterTimer = new Button("<- Main Menu");
        goBackAfterTimer.setOnAction(e -> window.setScene(mainMenuScene));
        Button goBackAfterScrabble = new Button("<- Main Menu");
        goBackAfterScrabble.setOnAction(e -> window.setScene(mainMenuScene));
        Button goBackAfterTeamBuilder = new Button("<- Main Menu");
        goBackAfterTeamBuilder.setOnAction(e -> window.setScene(mainMenuScene));

        /**
         * ALL GAMES must have a button to switch over to their appropriate scene
         */

        //create button for dice roll option
        Button opt1 = new Button("Dice Roll!");
        opt1.setOnAction(e -> window.setScene(diceRollScene));
        Button opt2 = new Button("Word Soup!");
        opt2.setOnAction(e -> window.setScene(scrabbleScene));
        Button opt3 = new Button("Start a stop watch!");
        opt3.setOnAction(e -> window.setScene(stopWatchScene));
        Button opt4 = new Button("Make Teams!");
        opt4.setOnAction(e -> window.setScene(teamBuilderScene));

        /**
         * Layout 1 - The Main Menu
         * Put the label and button in a VBox with 10 pixels of spacing.
         */

        valueLabel = new Label("Go ahead, Click!");
        VBox  vbox = new VBox(10);
        vbox.getChildren().addAll(messageLabel,opt1,opt2,opt3,opt4);
        // Create the first scene with 1:1 width/height ratio
        mainMenuScene = new Scene(vbox, 400, 400);

        /**
         * Layout 2 - Dice Roll Game
         * Create button for roll and register an event handler
         */

        // Create roll button
        Button roll = new Button("Let's Roll");

        // Register event handler
        roll.setOnAction(new rollTheDice());

        diceBox.getChildren().addAll(roll,valueLabel,goBackAfterDice);
        diceRollScene = new Scene(diceBox, 400, 400);


        /**
         * Layout 3 - StopWatch Game
         * Create button for roll and register an event handler
         *
         * Used this site: http://www.naturalborncoder.com/java/javafx/2013/06/13/samplefx-updating-a-label/
         * To properly update the time label every second
         */

        Label stopWatchLabel = new Label("0:0:0");

        //*****************************************************************************
        //Will handle constantly updating label to correct time using a Thread/Task

        Task dynamicTimeTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                while (true) {
                    updateMessage(myWatch.timeStamp());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
                return null;
            }
        };
        stopWatchLabel.textProperty().bind(dynamicTimeTask.messageProperty());
        Thread t2 = new Thread(dynamicTimeTask);
        t2.setName("Tesk Time Updater");
        t2.setDaemon(true);
        t2.start();
        //*****************************************************************************

        // Start Button and its Event Handler
        Button startTime = new Button("Start");
        startTime.setOnAction(new startTheTimer());

        // Stop Button and its Event Handler
        Button stopTime = new Button("Stop");
        stopTime.setOnAction(new stopTimer());

        // Reset Button and its Event Handler
        Button resetTime = new Button("Reset");
        resetTime.setOnAction(new resetTimer());

        // Add all Nodes to Stopwatch Box
        stopWatchBox.getChildren().addAll(stopWatchLabel,startTime,stopTime,resetTime,goBackAfterTimer);
        stopWatchScene = new Scene(stopWatchBox,400,400);

        /**
         * Layout 4 - Scrabble Game
         * Create button for roll and register an event handler
         */

        Button deal = new Button("Let's Play!");
        deal.setOnAction(new scrabbleDeal());
        wordSoupLetters = new Label("_ _ _ _ _");
        scrabbleBox.getChildren().addAll(deal,wordSoupLetters,goBackAfterScrabble);
        scrabbleScene = new Scene(scrabbleBox, 400, 400);

        /**
         * Layout 5 - Team Builder
         * Create button for roll and register an event handler
         */

        // Create roll button
        Button enter = new Button("Enter");
        Button makeTeam = new Button("Make Team!");
        Button resetTeams = new Button("Reset Teams!");

        // Register event handler
        enter.setOnAction(new enterName());
        makeTeam.setOnAction(new displayTeams());
        resetTeams.setOnAction(new resetTeamsFunction());

        Label teamOneLabel = new Label("Team 1");
        Label teamTwoLabel = new Label ("Team 2");

        HBox teamLabels = new HBox(50);
        teamLabels.getChildren().addAll(teamOneLabel,teamTwoLabel);

        teamBuilderBox.getChildren().addAll(teaBuilderTitle,nameInput,enter,makeTeam,resetTeams,teamLabels);
        teamBuilderScene = new Scene(teamBuilderBox, 400, 400);


        /**
         * Final Design Touches
         * Making sure everything being displayed is centered
         */
        vbox.setAlignment(Pos.CENTER);
        diceBox.setAlignment(Pos.CENTER);
        stopWatchBox.setAlignment(Pos.CENTER);
        scrabbleBox.setAlignment(Pos.CENTER);
        teamBuilderBox.setAlignment(Pos.CENTER);
        teamLabels.setAlignment(Pos.CENTER);

        // Add the scene to the Stage
        window.setScene(mainMenuScene);

        //show the window
        window.show();
    } //end start

    /**
     * Event Handler class for Dice Roll Game
     */
    class rollTheDice implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {

            // Remove any existing images of the dice that are currently displayed
            diceBox.getChildren().remove(image);

            // Dice roll will return appropriate dice image
            String value = myDice.roll();

            // Open image
            image = new ImageView(new Image(value));

            // Add image to current layout
            diceBox.getChildren().add(image);

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }//end handle
    }//end rollTheDice

    /**
     * Event Handler class for Starting Timer
     */
    class startTheTimer implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {

            myWatch.startMyTimer();

        }//end handle
    }//end startTheTimer2

    /**
     * Event Handler class for Stopping Timer
     */
    class stopTimer implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            myWatch.stopMyTimer();

        }//end handle
    }//end stopTimer

    /**
     * Event Handler class for Reset Timer
     */
    class resetTimer implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            myWatch.resetTimer();

        }//end handle
    }//end resetTimer

    /**
     * Event Handler class for Dealing Letters for Scrabble
     */
    class scrabbleDeal implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            ArrayList<String> result = wordSoup.deal(5);
            String Letters = "";
            for (String x :result) {
                Letters += x;
                Letters += " ";
            }
            wordSoupLetters.setText(Letters);

        }//end handle
    }//end scrabbleDeal

    /**
     * Event Handler class for Entering Name
     */
    class enterName implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            myTeam.enterName(nameInput.getText());
            nameInput.setText("");

        }//end handle
    }//end enterName

    /**
     * Event Handler class for displaying Team
     */
    class displayTeams implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            for (HBox oldMembers : membersList ) {

                teamBuilderBox.getChildren().remove(oldMembers);
            }
            membersList.clear();
            membersList = myTeam.getTeams();

            for (HBox newMembers : membersList) {
                HBox demo = new HBox(10);
                demo = newMembers;
                teamBuilderBox.getChildren().add(demo);
            }

        }//end handle
    }//end displayTeams

    /**
     * Event Handler class for reseting team board
     */
    class resetTeamsFunction implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            for (HBox oldMembers : membersList ) {
                teamBuilderBox.getChildren().remove(oldMembers);
                myTeam.reset();
            }
        }//end handle
    }//end resetTeamsFunctin
}//end class