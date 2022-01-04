package com.example.breakout;

import com.example.breakout.Classes.Block;
import com.example.breakout.Classes.Game;
import com.example.breakout.Classes.Ball;
import com.example.breakout.Classes.Bar;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerScreens implements Initializable {


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
        Stage stage = new Stage();
        stage.setTitle("Leveleditor");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // close previous window
        Node n = (Node) event.getSource();
        Stage previous = (Stage) n.getScene().getWindow();
        previous.close();
    }


    private Game game = new Game();

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


        game = new Game(scene, this.scene);
        game.setBall(new Ball(circle, 5, -5, 500, 600));
        game.setBar(new Bar(rectangle));
        game.checkBall();

        //testin purposes
     List<Block>blocks=createBlocks(10, 5, 2);
     game.setBlocks(blocks);

        // listening to certain KeyEvent's
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            double BarDirectX = 15; //BarDirectX can be moved or changed depending on how it feels

            if (key.getCode() == KeyCode.A) {
                System.out.println("Bar Left in Controller");
                if (this.rectangle.getLayoutX() <= 0) {
                    this.rectangle.setLayoutX(0);
                } else {
                    game.moveBar(-BarDirectX);
                }
            }
            if (key.getCode() == KeyCode.D) {
                System.out.println("Bar Right in Controller");
                if (this.rectangle.getLayoutX() + this.rectangle.getWidth() >= 1000) {
                    this.rectangle.setLayoutX(1000 - rectangle.getWidth());
                } else {
                    game.moveBar(+BarDirectX);
                }
            }
            // this.rectangle.getLayoutX --> most left point

        });

        //have the timeline stop when you exit out
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //use scene.removeEventHandler to remove it after the screen ends, though might not be needed if we change scenes
    }


    // Quelle: https://www.youtube.com/watch?v=x6NFmzQHvMU

    @FXML
    private AnchorPane scene;

    @FXML
    private Circle circle;// circle == ball

    @FXML
    private Rectangle rectangle;


  /*  //1 Frame evey 10 millis, which means 100 FPS
    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {

        double deltaX = 1;

        @Override
        public void handle(ActionEvent actionEvent) {
            rectangle.setLayoutX(rectangle.getLayoutX() + deltaX);

            Bounds bounds = scene.getBoundsInLocal();
            boolean rightBorder = rectangle.getLayoutX() >= (bounds.getMaxX() - rectangle.getWidth());
            boolean leftBorder = rectangle.getLayoutX() <= (bounds.getMinX());


            if (rightBorder || leftBorder) {
                deltaX *= -1;
            }

        }

    }));*/


    //1 Frame evey 10 millis, which means 100 FPS
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {


        @Override
        public void handle(ActionEvent actionEvent) {

            // methods used while timeline is ongoing

            game.moveBall();
            game.checkBall();



/*
            circle.setLayoutX(circle.getLayoutX() + ball.getmomentum().get(0));
            circle.setLayoutY(circle.getLayoutY() + ball.getmomentum().get(1));

            Bounds bounds = scene.getBoundsInLocal();
            boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
            boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
            boolean bottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
            boolean topBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());


            if (rightBorder || leftBorder) {
                 ball.changemomentum(ball.getmomentum().get(0)*(-1),ball.getmomentum().get(1)); // früher das mit deltay *=-1
            }
            if (topBorder) {
                ball.changemomentum(ball.getmomentum().get(0),ball.getmomentum().get(1)*(-1)); // selbe wie oben
            }


            boolean CollisionY = circle.getLayoutY() + circle.getRadius() == 700;
            boolean CollisionX = (rectangle.getLayoutX() + rectangle.getWidth() / 2) >= circle.getCenterX() && circle.getCenterX() >= (rectangle.getLayoutX() - rectangle.getWidth() / 2);

            if (CollisionX && CollisionY) {
                ball.changemomentum(ball.getmomentum().get(0),ball.getmomentum().get(1)*(-1));
            }

            if (bottomBorder) {
                timeline.stop();
            }

 */


        }

    }));


    public List<Block> createBlocks(int blocks, int rows, int coloums) {
        List<Block> blocklist = new LinkedList<Block>();
        int xWert = 50;
        int yWert = 50;
        int count = 0;

        while (count < blocks) {
            for (int k = 0; k < rows; k++) {
                xWert = 50;
                for (int i = 0; i < coloums; i++) {
                    Rectangle block = new Rectangle(xWert, yWert, 100, 30);
                    blocklist.add(new Block(block,1));
                    block.setFill(Color.RED);
                    scene.getChildren().add(block);

                    count++;
                    xWert += 110;
                }
                yWert += 40;
            }
        }
        return blocklist;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    @FXML
    private Label highscore = new Label();

    private final long createdMillis = System.currentTimeMillis();

    public void getAgeInSeconds() {
        long nowMillis = System.currentTimeMillis();
        int zw = (int) ((nowMillis - this.createdMillis) / 1000);
        highscore.textProperty().bind(new SimpleIntegerProperty(zw).asString());
    }

}

