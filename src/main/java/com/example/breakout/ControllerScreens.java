package com.example.breakout;

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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class ControllerScreens implements Initializable {

    /**
     * ________________________________________________________________________________________________________________
     * Scene switching
     * -> needs closing from window before !!! (isn't done yet)
     * if anyone wants to do it np, go right ahead :)
     */

    public void SwitchToLevels(ActionEvent event) throws IOException {

        // called by button "Start"

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

    public void SwitchToSettings(ActionEvent event) throws IOException {

        // called by button "Einstellugen"

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

    public void SwitchToLeveleditor(ActionEvent event) throws IOException {

        // called by button "Level Editor"

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("leveleditorScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Stage stage = new Stage();
        stage.setTitle("Breakout -> mainScreen -> leveleditorScreen");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // close previous window
        Node n = (Node) event.getSource();
        Stage previous = (Stage) n.getScene().getWindow();
        previous.close();
    }

    /**
     *_________________________________________________________________________________________________________________
     */

    /**
     * Das darunter ist daweil nur ein Bouncing Ball und ne Bar die hin und her geht
     * halt nicht auf input reagiert
     */
    private Game game = new Game();

    public void SwitchToGame(ActionEvent event) throws IOException {

        // called by button "Level Editor"

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


        Bounds bounds = scene.lookup("#scene").getBoundsInLocal();////////////////////////////////////


        this.circle = (Circle) scene.lookup("#circle");
        this.rectangle = (Rectangle) scene.lookup("#rectangle");
        Ball ball = new Ball();///////////////////////////////////////////////////////////////////////////
        game = new Game(scene, this.scene);
        game.setBall(new Ball(circle, 1, -1));
        game.setBar(new Bar(rectangle));
        game.checkBall();
      scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            //BarDirectX can be moved or changed depending on how if feels
            double BarDirectX = 5;
          /** ///////////////////////////////////////
           * moving left
           */
          if (key.getCode() == KeyCode.A) { System.out.println("left"); if(this.rectangle.getLayoutX()<= 0) { this.rectangle.setLayoutX(0);} else { game.moveBar(-BarDirectX);}}

          /** ///////////////////////////////////////
           * moving right
           */
          if (key.getCode() == KeyCode.D) { System.out.println("right"); if(this.rectangle.getLayoutX() + this.rectangle.getWidth()  >= 1000) { this.rectangle.setLayoutX(1000-rectangle.getWidth());} else { game.moveBar(+BarDirectX);}}

          /*
          boolean    rightBorder     = ball.getpositionalinfo().get(0) >= (bounds.getMaxX() - ball.getpositionalinfo().get(2));
          boolean    leftBorder      = ball.getpositionalinfo().get(0) <= (bounds.getMinX() + ball.getpositionalinfo().get(2));
          boolean    topBorder       = ball.getpositionalinfo().get(1) <= (bounds.getMinY() + ball.getpositionalinfo().get(2));
          boolean    bottomBorder    = ball.getpositionalinfo().get(1) >= (bounds.getMaxY() - ball.getpositionalinfo().get(2));

          if(rightBorder || leftBorder) { System.out.println("changeleftright"); ball.changemomentum(-1,1);}

          if(topBorder){ System.out.println("changetop"); ball.changemomentum(1,-1);}

          if(bottomBorder){timeline.stop();}

 */

        });
        //have the timeline stop when you exit out
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        //use scene.removeEventHandler to remove it after the screen ends, though might not be needed if we we change scenes
    }

    // Quelle: https://www.youtube.com/watch?v=x6NFmzQHvMU

    @FXML private AnchorPane scene;

    @FXML private Circle circle;// circle == ball

    @FXML private Rectangle rectangle;


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

            game.moveBall();
            game.checkBall();
            getAgeInSeconds(); // timer for highscore

           /* circle.setLayoutX(circle.getLayoutX() + deltaX);
            circle.setLayoutY(circle.getLayoutY() + deltaY);

            Bounds bounds = scene.getBoundsInLocal();
            boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
            boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
            boolean bottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
            boolean topBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());


            if (rightBorder || leftBorder) {
                deltaX *= -1;
            }
            if (topBorder) {
                deltaY *= -1;
            }


            boolean CollisionY = circle.getLayoutY() + circle.getRadius() == 700;
            boolean CollisionX = (rectangle.getLayoutX() + rectangle.getWidth() / 2) >= circle.getCenterX() && circle.getCenterX() >= (rectangle.getLayoutX() - rectangle.getWidth() / 2);

            if (CollisionX && CollisionY) {
                deltaY *= -1;
            }

            if (bottomBorder) {
                timeline.stop();
            }*/
        }

    }));


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

