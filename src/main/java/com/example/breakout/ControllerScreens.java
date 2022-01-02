package com.example.breakout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerScreens implements Initializable {

    /**________________________________________________________________________________________________________________
     * Scene switching
     * -> needs closing from window before !!! (isn't done yet)
     *    if anyone wants to do it np, go right ahead :)
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

    // Quelle: https://www.youtube.com/watch?v=x6NFmzQHvMU
    @FXML
    private AnchorPane scene = new AnchorPane();


    @FXML
    private Circle circle = new Circle(); // circle == ball

    @FXML
    private Rectangle rectangle = new Rectangle();


    @FXML
    public void Start(){

    }


    public void BarMovement(KeyEvent event){
        double BarDirectX = 10;
        if(event.getCode() == KeyCode.LEFT){ rectangle.setLayoutX(rectangle.getLayoutX() - BarDirectX);}
        if(event.getCode() == KeyCode.RIGHT){ rectangle.setLayoutX(rectangle.getLayoutX() + BarDirectX);}

    }

    //1 Frame evey 10 millis, which means 100 FPS
    Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {

        double deltaX = 1;

        @Override
        public void handle(ActionEvent actionEvent) {
            rectangle.setLayoutX(rectangle.getLayoutX() + deltaX);

            Bounds bounds = scene.getBoundsInLocal();
            boolean rightBorder = rectangle.getLayoutX() >= (bounds.getMaxX() - rectangle.getWidth());
            boolean leftBorder = rectangle.getLayoutX() <= (bounds.getMinX() + rectangle.getWidth());


            if (rightBorder || leftBorder) { deltaX *= -1; }

        }

    }));


    //1 Frame evey 10 millis, which means 100 FPS
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<>() {

        double deltaX = 1;
        double deltaY = -1;

        @Override
        public void handle(ActionEvent actionEvent) {
            circle.setLayoutX(circle.getLayoutX() + deltaX);
            circle.setLayoutY(circle.getLayoutY() + deltaY);

            Bounds bounds = scene.getBoundsInLocal();
            boolean rightBorder = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
            boolean leftBorder = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
            boolean bottomBorder = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());
            boolean topBorder = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());

            if (rightBorder || leftBorder) { deltaX *= -1; }
            if (topBorder) { deltaY *= -1; }
            if (bottomBorder) {timeline.stop();}
        }

    }));



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();
    }

}
