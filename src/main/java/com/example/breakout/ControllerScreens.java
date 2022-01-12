package com.example.breakout;

import com.example.breakout.Classes.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerScreens implements Initializable {
    public static final int windowHeight = 720;
    private static final int windowWidth = 1280;

    public void showClockScreen(ActionEvent event) throws IOException {


        AnalogClock clock = new AnalogClock();

        clock.start(Application.getStage());
        //Application.getStage().setScene(clock.getScene());

    }

    public static void SwitchToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        //stage = new Stage();
        Application.stage.setTitle("Breakout -> mainScreen");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();
        //staticclass.playsong("titlescreen.mp3");
    }



    public void SwitchToLevels(ActionEvent event) throws IOException { // called by button "Start"
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("levelsScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        //Stage stage = new Stage();
        Application.stage.setTitle("Breakout -> mainScreen -> levelsScreen");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();

        // close previous window
        /*
        Node n = (Node) event.getSource();
        Stage previous = (Stage) n.getScene().getWindow();
        previous.close();

         */

    }

    public void SwitchToSettings(ActionEvent event) throws IOException { // called by button "Einstellugen"

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("settingsScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        //Stage stage = new Stage();
        Application.stage.setTitle("Breakout -> mainScreen -> settingsScreen");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();

        // close previous window
        /*
        Node n = (Node) event.getSource();
        Stage previous = (Stage) n.getScene().getWindow();
        previous.close();

         */
    }

    public void SwitchToLeveleditor(ActionEvent event) throws IOException { // called by button "Level Editor"
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("leveleditorScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        Application.stage.setTitle("Leveleditor");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();
    }


    private Game game = new Game();
    private EventHandler handler;

    public void SwitchToGame(ActionEvent event) throws IOException { // called by button "level [id]"

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        //Stage stage = new Stage();
        Application.stage.setTitle("Breakout -> mainScreen -> levelsScreen -> gameScreen");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();

        // close previous window
        /*
        Node n = (Node) event.getSource();
        Stage previous = (Stage) n.getScene().getWindow();
        previous.close();

         */


        this.scene = (AnchorPane) scene.lookup("#scene");          // -> scene in which the gameplay is done
                                                                      // -> #[fx:id]
        this.circle = (Circle) scene.lookup("#circle");            // --> the ball
        this.rectangle = (Rectangle) scene.lookup("#rectangle");   // -> the bar
        this.highscore = (Label) scene.lookup("#highscore");       // -> score

        game = new Game(scene, this.scene);
        game.setBall(new Ball(circle,   0,
                                        0,
                                        rectangle.getLayoutX() + rectangle.getWidth() / 2,
                                        rectangle.getLayoutY() - rectangle.getHeight()));
                                        // dx: dy: --> momentum
                                        // centerX: centerY: --> initial positional info
                                        // no initial momentum for the ball -> after pressing "B" method changeMomentum is called

        game.setBar(new Bar(rectangle));
        checkBall();

        //testin purposes
        game.setLevel(Level.loadLevel("name"));
        loadBlocks();


        handler = (EventHandler<KeyEvent>) (key) -> {
            // listening to KeyEvent's

            double BarDirectX = 15;
            //BarDirectX can be moved or changed depending on how it feels

            if (key.getCode() == KeyCode.B && !gameStartLock) {
                timeline.play();
                gameStart = true;
                gameStartLock = true;
                // locking the ability to press B multiple times
                game.getBall().changemomentum(3, -3);
                // momentum is given
                // why 1, -1? cause top left corner is 0, 0
                createdMillis = System.currentTimeMillis();
                // used in getAgeInSeconds() for the score (time needed)
                // -> starts only if B is pressed, so players have the "freedom" to
                // position the bar wherever they want before the timer starts
            }

            if (key.getCode() == KeyCode.A) {
                // keycodes == keyboard input
                //System.out.println("Bar Left in Controller"); // console output for testing
                if (this.rectangle.getLayoutX() <= 0) {
                    // looks if the bar "rectangle" is out of bounds and
                    // adjusts it's x-value
                    this.rectangle.setLayoutX(0);
                } else {
                    game.moveBar(-BarDirectX);
                    if (!gameStart) {
                        // boolean value -> looks if game has started
                        // it is changed if the key B is pressed
                        game.getBall().moveTo(( game.getBall().getpositionalinfo().get(0) - BarDirectX),
                                                game.getBall().getpositionalinfo().get(1));
                        // if the key B hasn't been pressed yet
                        // changes the ball's x-value corresponding to the bar's x-value
                        // --> "ball stays on top of bar"
                    }
                }
            }

            if (key.getCode() == KeyCode.D) {
                // keycodes == keyboard input
                //System.out.println("Bar Right in Controller"); // console output for testing
                if (this.rectangle.getLayoutX() + this.rectangle.getWidth() >= 1000) {
                    // looks if the bar "rectangle" is out of bounds and
                    // adjusts it's x-value
                    this.rectangle.setLayoutX(1000 - rectangle.getWidth());
                } else {
                    game.moveBar(+BarDirectX);
                    if (!gameStart) {
                        // boolean value -> looks if game has started
                        // it is changed if the key B is pressed
                        game.getBall().moveTo(( game.getBall().getpositionalinfo().get(0) + BarDirectX),
                                                game.getBall().getpositionalinfo().get(1));
                        // if the key B hasn't been pressed yet
                        // changes the ball's x-value corresponding to the bar's x-value
                        // --> "ball stays on top of bar"
                    }
                }
            }
            // this.rectangle.getLayoutX --> most left point
        };
        scene.addEventHandler(KeyEvent.KEY_PRESSED, handler);///////////////////// I don't think we need them -> ??? why

        if (checkBall()) {
            timeline.stop();
        }/////////////////////////////////////// I don't think we need them -> solved in timeline


        //have the timeline stop when you exit out
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public boolean checkBall() {
        boolean b = game.checkBall();

        for (int i = 0; i < scene.getChildren().size(); i++) {
            // iterates to the number of children the scene has
            Node n = scene.getChildren().get(i);
            if (n.getClass().getSimpleName().equals("Rectangle")) {
                // filtering -> we are only interested in rectangles
                Rectangle r = (Rectangle) n;
                if (//game.getLevel().findBlock(r.getX(), r.getY()) == null &&
                    r != rectangle) {
                    // && r != rectangle -> the bar is also a rectangle, so we need to take it out
                    scene.getChildren().remove(r);
                }
            }
        }

        loadBlocks();
        return b;
    }

    @FXML
    private AnchorPane scene; // scene in which the gameplay is done

    @FXML
    private Circle circle;// circle == ball

    @FXML
    private Rectangle rectangle; // rectangle == bar

    @FXML
    private Label highscore = new Label();


    private Boolean gameStart = false;
    private Boolean gameStartLock = false;
    private long createdMillis;


    // 1 Frame evey 10 millis, which means 100 FPS
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {

        @Override
        public void handle(ActionEvent actionEvent) {

            // methods used while timeline is ongoing
            // is started by start button "B" after
            // moving the Bar to the spot the user would like to begin
            game.moveBall();
            if (checkBall()) {
                // checkBall returns boolean -> looks if ball hit the bottom border of the scene
                getAgeInSeconds();
                // score/ timer counts up
            } else {
                timeline.stop();
                // if lost, timeline is stopped
                staticclass.playsound("lose.wav");
                // losing sound
            }
        }

    }));

    private void loadBlocks() {
        Block block;
        for (int i = 0; i < game.getLevel().getBlocks().size(); i++) {
            block = game.getLevel().getBlocks().get(i);
            placeBlock(block.getX(), block.getY(), block.getStrength());
        }
    }

    private void placeBlock(double x, double y, int strength) {
        Rectangle rect;
        Block block;

        if (strength == 1) {
            rect = new Rectangle(x, y, 100, 30);
            block = new Block(game.getLevel().getCount(), rect, strength);
            // block = new Block(level.getCount(), 1090, 50, 100, 30, strength);
            rect.setFill(Color.RED);
        } else if (strength == 2) {
            rect = new Rectangle(x, y, 100, 30);
            block = new Block(game.getLevel().getCount(), rect, strength);
            //block = new Block(level.getCount(), 1090, 130, 100, 30, strength);
            rect.setFill(Color.BLUE);
        } else {
            rect = new Rectangle(x, y, 100, 30);
            block = new Block(game.getLevel().getCount(), rect, strength);
            //block = new Block(level.getCount(), 1090, 210, 100, 30, strength);
            rect.setFill(Color.GREEN);
        }
        scene.getChildren().add(rect);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }


    public void getAgeInSeconds() {
        // is needed for score, looks if start button is pressed "B" and then
        // it takes the current time in milliseconds
        // and every 10 milliseconds (timeline is responsible for that)
        // it looks at the current time and calculates how much time is between
        long nowMillis = System.currentTimeMillis();
        int zw = (int) ((nowMillis - this.createdMillis) / 1000);
        highscore.textProperty().bind(new SimpleIntegerProperty(zw).asString());
    }
}
