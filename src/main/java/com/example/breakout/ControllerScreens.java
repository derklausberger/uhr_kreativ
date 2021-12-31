package com.example.breakout;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ControllerScreens {

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
    }

    /**
     *_________________________________________________________________________________________________________________
     */




    public void Changetimerboolean(){if(timerBoolean = false){timerBoolean = true;}else{timerBoolean = true;}}



    @FXML
    protected void Start() {
        Changetimerboolean();
    }

    @FXML
    protected void LevelEditor() {
    }

    @FXML
    protected void Einstellungen() {
    }




    @FXML
    private Label time;
    boolean timerBoolean = true;

    @FXML
    private void Timer() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat stf = new SimpleDateFormat("hh:mm:ss a");
            while (!timerBoolean) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timer = stf.format(new Date());
                Platform.runLater(() -> {
                    time.setText(timer);
                });
            }
        });
        thread.start();
    }

}