package com.example.breakout;

import com.example.breakout.Classes.Staticclass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


public class Application extends javafx.application.Application {

    public static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Application.stage = stage;
        stage.setTitle("Breakout -> mainScreen");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        // das braucht nur klausberger, bitte nicht löschen
        //((Button)scene.lookup("#lvlEditBtn")).fire();
        Staticclass.readlocalsettings();
        Staticclass.playsong("titlescreen.mp3");
    }


    public static void main(String[] args) {
        launch();
    }
}