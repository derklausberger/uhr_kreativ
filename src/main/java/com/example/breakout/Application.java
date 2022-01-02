package com.example.breakout;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setTitle("Breakout -> mainScreen");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }


    public static void main(String[] args) { launch(args);}
}