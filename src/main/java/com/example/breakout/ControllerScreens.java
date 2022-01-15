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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class ControllerScreens implements Initializable {
    public static final int windowHeight = 720;
    private static final int windowWidth = 1280;

    public void showClockScreen() {
        AnalogClock clock = new AnalogClock();

        clock.start(Application.getStage());
        //Application.getStage().setScene(clock.getScene());
    }

    public static void SwitchToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        //stage = new Stage();
        Application.stage.setTitle("Breakout");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();
        Staticclass.playsong("titlescreen.mp3");
    }

    public void SwitchToMainns() throws IOException {// Called by a button to go back to the main, as Static methods cant be used by on-action in fxml
        SwitchToMain();
    }


    @FXML
    AnchorPane mainPaneLevelScreen;


    public void SwitchToLevels() throws IOException { // called by button "Start"
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("levelsScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        Application.stage.setTitle("Levels");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();

        mainPaneLevelScreen = (AnchorPane) scene.lookup("#mainPaneLevelScreen");

        /* ScrollPane scroll = new ScrollPane();
        scroll.setContent(mainPaneLevelScreen);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        */





        Button backToMain = new Button("Back to Menu");

        backToMain.setLayoutX(65);
        backToMain.setLayoutY(38);

        backToMain.addEventHandler(MouseEvent.MOUSE_CLICKED, (e -> {
            try {
                ControllerScreens.SwitchToMain();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));

        TextField searchField = new TextField();

        searchField.setLayoutX(1005);
        searchField.setLayoutY(38);

        searchField.setMinHeight(30);
        searchField.setMaxHeight(30);

        searchField.setMinWidth(100);
        searchField.setMaxWidth(100);


        Button searchBtn = new Button("Search");

        searchBtn.setLayoutX(1115);
        searchBtn.setLayoutY(38);

        searchBtn.setMinHeight(30);
        searchBtn.setMaxHeight(30);

        searchBtn.setMinWidth(100);
        searchBtn.setMaxWidth(100);

        searchBtn.setOnMouseClicked(e -> {
            for (int i = mainPaneLevelScreen.getChildren().size() - 1; i >= 0; i--) {
                Node n = mainPaneLevelScreen.getChildren().get(i);
                if (n.getClass().getSimpleName().equals("AnchorPane")) {
                    mainPaneLevelScreen.getChildren().remove(n);
                }
            }

            showLevels(searchField.getText());
        });

        mainPaneLevelScreen.getChildren().addAll(backToMain, searchField, searchBtn);

        showLevels("");
        final double[] scroll = {0};



        mainPaneLevelScreen.setOnScroll(e -> {
            for (int i = mainPaneLevelScreen.getChildren().size() - 1; i >= 0; i--) {
                Node n = mainPaneLevelScreen.getChildren().get(i);
                if (scroll[0] + e.getDeltaY() >= 0) {
                    n.setLayoutY(n.getLayoutY() + scroll[0]);
                    scroll[0] = 0;
                } else if (scroll[0] + e.getDeltaY() >= windowHeight + 500){
                    n.setLayoutY(n.getLayoutY() + (windowHeight - scroll[0]));
                    scroll[0] = 1000 - windowHeight;
                } else {
                    n.setLayoutY(n.getLayoutY() + e.getDeltaY());
                    scroll[0] += e.getDeltaY();
                }

                //System.out.println(scroll[0]);
            }
        });
    }

    public void showLevels(String filterBy) {
        Level[] levels = Level.loadLevelList();
        if (levels != null) {

            int x = 65;
            int y = 90;

            //double winHeight = y + levels.length * 250;

            for (int i = 0; i < levels.length; i++) {
                Level level = levels[i];

                if (level.getName().toLowerCase().contains(filterBy.toLowerCase())) {
                    AnchorPane pane = new AnchorPane();

                    pane.setMinWidth(250);
                    pane.setMaxWidth(250);

                    pane.setMinHeight(200);
                    pane.setMaxHeight(200);

                    pane.setLayoutX(x);
                    pane.setLayoutY(y);

                    if ((i + 1) % 4 == 0) {
                        x = 65;
                        y += 720 / 4 + 50;
                    } else {
                        x += 250 + 50;
                    }

                    game = new Game(level);
                    loadBlocks(0.25, pane);

                    Label label = new Label(level.getName());
                    AnchorPane.setLeftAnchor(label, 0.0);
                    AnchorPane.setRightAnchor(label, 0.0);
                    AnchorPane.setBottomAnchor(label, 0.0);
                    AnchorPane.setTopAnchor(label, 0.0);

                    label.setAlignment(Pos.BOTTOM_CENTER);

                    pane.getChildren().add(label);
                    pane.setOnMouseClicked(e -> {
                        try {
                            game = new Game(level);
                            SwitchToGame();//(ActionEvent) e);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });

                    pane.setOnMouseEntered(e -> pane.setStyle("-fx-border-color: black; -fx-border-width: 5px;"));

                    pane.setOnMouseExited(e -> pane.setStyle("-fx-border-color: black; -fx-border-width: 2px;"));

                    pane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

                    mainPaneLevelScreen.getChildren().add(pane);
                }
            }
        }
    }

    @FXML
    Button Musicbutton;
    @FXML
    Button Soundbutton;

    public void SwitchToSettings() throws IOException { // called by button "Settings"
        Staticclass.playsong("settings.mp3");
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("settingsScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        Application.stage.setTitle("Settings");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();
        this.Musicbutton = (Button) scene.lookup("#Musicbutton");   // -> the Music button
        if(!Staticclass.isMusicsetting()){
            Musicbutton.setText("Enable Music");
        }
        this.Soundbutton = (Button) scene.lookup("#Soundbutton");   // -> the Sound button
        if(!Staticclass.isSoundsetting()){
            Soundbutton.setText("Enable Sound");
        }
    }

    public void ChangeMusicSetting() throws IOException { // Called by button "Music button" to turn music on or off
        Staticclass.setMusicsetting(!Staticclass.isMusicsetting());
        if(Staticclass.isMusicsetting()){
            Musicbutton.setText("Disable Music");
        }else{
            Musicbutton.setText("Enable Music");
        }
    }

    public void ChangeSoundSetting() throws IOException { // Called by button "Sound button" to turn Sound on or off
        Staticclass.setSoundsetting(!Staticclass.isSoundsetting());
        if(Staticclass.isSoundsetting()){
            Soundbutton.setText("Disable Sound");
        }else{
            Soundbutton.setText("Enable Sound");
        }
    }

    public void SwitchToLeveleditor() throws IOException { // called by button "Level Editor"
        Staticclass.playsong("Leveleditor.mp3");
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("leveleditorScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        Application.stage.setTitle("Leveleditor");
        Application.stage.setScene(scene);
        Application.stage.setResizable(false);
        Application.stage.show();
    }


    private Game game;// = new Game();

    public void SwitchToGame(/*ActionEvent event*/) throws IOException { // called by button "level [id]"

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), windowWidth, windowHeight);
        //Stage stage = new Stage();
        Application.stage.setTitle("Game");
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

        //game = new Game();//(scene, this.scene);
        game.setBall(new Ball(circle, 0,
                0,
                rectangle.getLayoutX() + rectangle.getWidth() / 2,
                rectangle.getLayoutY() - rectangle.getHeight()));
        // dx: dy: --> momentum
        // centerX: centerY: --> initial positional info
        // no initial momentum for the ball -> after pressing "B" method changeMomentum is called

        game.setBar(new Bar(rectangle));
        checkBall();

        //testin purposes
        //game.setLevel(Level.loadLevel("name"));
        loadBlocks(1, this.scene);


        // listening to KeyEvent's
        //BarDirectX can be moved or changed depending on how it feels
        // locking the ability to press B multiple times
        // momentum is given
        // why 1, -1? cause top left corner is 0, 0
        // used in getAgeInSeconds() for the score (time needed)
        // -> starts only if B is pressed, so players have the "freedom" to
        // position the bar wherever they want before the timer starts
        // keycodes == keyboard input
        //System.out.println("Bar Left in Controller"); // console output for testing
        // looks if the bar "rectangle" is out of bounds and
        // adjusts it's x-value
        // boolean value -> looks if game has started
        // it is changed if the key B is pressed
        // if the key B hasn't been pressed yet
        // changes the ball's x-value corresponding to the bar's x-value
        // --> "ball stays on top of bar"
        // keycodes == keyboard input
        //System.out.println("Bar Right in Controller"); // console output for testing
        // looks if the bar "rectangle" is out of bounds and
        // adjusts it's x-value
        // boolean value -> looks if game has started
        // it is changed if the key B is pressed
        // if the key B hasn't been pressed yet
        // changes the ball's x-value corresponding to the bar's x-value
        // --> "ball stays on top of bar"
        // this.rectangle.getLayoutX --> most left point
        EventHandler<KeyEvent> handler = (key) -> {
            // listening to KeyEvent's

            double BarDirectX = 15;
            //BarDirectX can be moved or changed depending on how it feels

            if (key.getCode() == KeyCode.B && !gameStartLock) {
                timeline.play();
                gameStart = true;
                gameStartLock = true;
                // locking the ability to press B multiple times
                game.getBall().changemomentum(1, -1);
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
                        game.getBall().moveTo((game.getBall().getpositionalinfo().get(0) - BarDirectX),
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
                        game.getBall().moveTo((game.getBall().getpositionalinfo().get(0) + BarDirectX),
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

        for (int i = scene.getChildren().size() - 1; i >= 0; i--) {
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

        loadBlocks(1, this.scene);
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
                Staticclass.playsound("lose.wav");
                // losing sound
            }
        }

    }));

    private void loadBlocks(double factor, AnchorPane pane) {
        Block block;
        for (int i = 0; i < game.getLevel().getBlocks().size(); i++) {
            block = game.getLevel().getBlocks().get(i);
            placeBlock(block.getX() * factor,
                    block.getY() * factor,
                    block.getStrength(), factor, pane);
        }
    }

    private void placeBlock(double x, double y, int strength, double factor, AnchorPane pane) {
        Rectangle rect = new Rectangle(x, y, 100 * factor, 30 * factor);
        //Block block = new Block(game.getLevel().getCount(), rect, strength);

        if (strength == 1) {
            rect.setFill(Color.RED);
        } else if (strength == 2) {
            rect.setFill(Color.BLUE);
        } else {
            rect.setFill(Color.GREEN);
        }

        pane.getChildren().add(rect);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


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
