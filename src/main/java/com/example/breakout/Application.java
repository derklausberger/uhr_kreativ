package com.example.breakout;

import com.example.breakout.Classes.staticclass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Breakout -> mainScreen");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        // das braucht nur klausberger, bitte nicht löschen
        //((Button)scene.lookup("#lvlEditBtn")).fire();
        staticclass.playsong("titlescreen.mp3");
    }


    public static void main(String[] args) { launch();}
}