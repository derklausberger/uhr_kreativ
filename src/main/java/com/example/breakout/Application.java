package com.example.breakout;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        this.stage.setTitle("Breakout -> mainScreen");
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

        //((Button)scene.lookup("#lvlEditBtn")).fire();
    }


    public static void main(String[] args) { launch();}
}