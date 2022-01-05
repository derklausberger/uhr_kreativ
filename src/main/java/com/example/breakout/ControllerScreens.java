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
    //public static Stage stage;
    public static void SwitchToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        //stage = new Stage();
        Application.stage.setTitle("Breakout -> mainScreen");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();
    }


    public void SwitchToLevels(ActionEvent event) throws IOException { // called by button "Start"

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("levelsScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Stage stage = new Stage();
        stage.setTitle("Breakout -> mainScreen -> levelsScreen");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // close previous window
        Node n = (Node) event.getSource();
        Stage previous = (Stage) n.getScene().getWindow();
        previous.close();

    }

    public void SwitchToSettings(ActionEvent event) throws IOException { // called by button "Einstellugen"

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("settingsScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Stage stage = new Stage();
        stage.setTitle("Breakout -> mainScreen -> settingsScreen");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // close previous window
        Node n = (Node) event.getSource();
        Stage previous = (Stage) n.getScene().getWindow();
        previous.close();
    }

    public void SwitchToLeveleditor(ActionEvent event) throws IOException { // called by button "Level Editor"

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("leveleditorScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        //Application.stage = new Stage();
        Application.stage.setTitle("Leveleditor");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();

        // close previous window
        /*Node n = (Node) event.getSource();
        Stage previous = (Stage) n.getScene().getWindow();
        previous.close();*/
    }


    private Game game = new Game();
    private EventHandler handler;

    public void SwitchToGame(ActionEvent event) throws IOException { // called by button "level [id]"

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Stage stage = new Stage();
        stage.setTitle("Breakout -> mainScreen -> levelsScreen -> gameScreen");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // close previous window
        Node n = (Node) event.getSource();
        Stage previous = (Stage) n.getScene().getWindow();
        previous.close();


        this.scene = (AnchorPane) scene.lookup("#scene");
        this.circle = (Circle) scene.lookup("#circle");
        this.rectangle = (Rectangle) scene.lookup("#rectangle");
        this.highscore = (Label) scene.lookup("#highscore");

        game = new Game(scene, this.scene);
        game.setBall(new Ball(circle, 0, 0, rectangle.getLayoutX() + rectangle.getWidth() / 2, rectangle.getLayoutY() - rectangle.getHeight()));
        game.setBar(new Bar(rectangle));
        checkBall();

        //testin purposes
        Level lvl = Level.loadLevel("name");
        loadBlocks(lvl);
        game.setLevel(lvl);


        // listening to certain KeyEvent's
        handler = (EventHandler<KeyEvent>) (key) -> {
            double BarDirectX = 5;//BarDirectX can be moved or changed depending on how it feels

            if (key.getCode() == KeyCode.B) {
                timeline.play();
                gameStart = true;
                game.getBall().changemomentum(1, -1);
                createdMillis = System.currentTimeMillis();
            }

            if (key.getCode() == KeyCode.A) {
                System.out.println("Bar Left in Controller");
                if (this.rectangle.getLayoutX() <= 0) {
                    this.rectangle.setLayoutX(0);
                } else {
                    game.moveBar(-BarDirectX);
                    if (!gameStart) {
                        game.getBall().moveTo((game.getBall().getpositionalinfo().get(0) - BarDirectX), game.getBall().getpositionalinfo().get(1));
                    }
                }
            }

            if (key.getCode() == KeyCode.D) {
                System.out.println("Bar Right in Controller");
                if (this.rectangle.getLayoutX() + this.rectangle.getWidth() >= 1000) {
                    this.rectangle.setLayoutX(1000 - rectangle.getWidth());
                } else {
                    game.moveBar(+BarDirectX);
                    if (!gameStart) {
                        game.getBall().moveTo((game.getBall().getpositionalinfo().get(0) + BarDirectX), game.getBall().getpositionalinfo().get(1));
                    }
                }
            }

            // this.rectangle.getLayoutX --> most left point
        };

        scene.addEventHandler(KeyEvent.KEY_PRESSED, handler);
        if (checkBall()) {
            timeline.stop();
        }


        //have the timeline stop when you exit out
        timeline.setCycleCount(Animation.INDEFINITE);


        //use scene.removeEventHandler to remove it after the screen ends, though might not be needed if we change scenes
    }

    public boolean checkBall() {
        boolean b = game.checkBall();

        for (int i = 0; i < scene.getChildren().size(); i++) {
            Node n = scene.getChildren().get(i);
            if (n.getClass().getSimpleName().equals("Rectangle")) {
                Rectangle r = (Rectangle) n;
                if (!game.getLevel().findBlock(r.getX(), r.getY()) && r != rectangle) {
                    scene.getChildren().remove(r);
                }
            }
        }
        return b;
    }


    // Quelle: https://www.youtube.com/watch?v=x6NFmzQHvMU

    @FXML
    private AnchorPane scene;

    @FXML
    private Circle circle;// circle == ball

    @FXML
    private Rectangle rectangle; // rectangle == bar

    private Boolean gameStart = false;


    //1 Frame evey 10 millis, which means 100 FPS
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {


        @Override
        public void handle(ActionEvent actionEvent) {

            // methods used while timeline is ongoing

            game.moveBall();

            if (checkBall()) {
                getAgeInSeconds();
            } else {
                timeline.stop();
                File path = new File(String.valueOf(Paths.get(System.getProperty("user.dir"), "src", "resources", staticclass.theme, "lose.wav")));
                System.out.println(path);
                try {
                    AudioInputStream audioinput2 = AudioSystem.getAudioInputStream(path);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioinput2);
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }));

    private void loadBlocks(Level level) {
        Block block;
        for (int i = 0; i < level.getBlocks().size(); i++) {
            block = level.getBlocks().get(i);
            placeBlock(level, block.getX(), block.getY(), block.getStrength());
        }
    }

    private void placeBlock(Level level, double x, double y, int strength) {
        Rectangle rect;
        Block block;

        if (strength == 1) {
            rect = new Rectangle(x, y, 100, 30);
            block = new Block(level.getCount(), rect, strength);
            // block = new Block(level.getCount(), 1090, 50, 100, 30, strength);
            rect.setFill(Color.DARKRED);
        } else if (strength == 2) {
            rect = new Rectangle(x, y, 100, 30);
            block = new Block(level.getCount(), rect, strength);
            //block = new Block(level.getCount(), 1090, 130, 100, 30, strength);
            rect.setFill(Color.DARKBLUE);
        } else {
            rect = new Rectangle(x, y, 100, 30);
            block = new Block(level.getCount(), rect, strength);
            //block = new Block(level.getCount(), 1090, 210, 100, 30, strength);
            rect.setFill(Color.DARKGREEN);
        }

        scene.getChildren().add(rect);
        //level.addBlock(block);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    @FXML
    private Label highscore = new Label();

    private long createdMillis;

    public void getAgeInSeconds() {
        long nowMillis = System.currentTimeMillis();
        int zw = (int) ((nowMillis - this.createdMillis) / 1000);
        highscore.textProperty().bind(new SimpleIntegerProperty(zw).asString());
    }

}
