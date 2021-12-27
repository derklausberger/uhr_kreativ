package com.example.breakout;

import com.example.breakout.Classes.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));


        /*Circle circle = new Circle(100, 100, 10);
        circle.setId("circle");
        Group group = new Group(circle);*/
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //Scene scene = new Scene(group, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        /*Game game = new Game(scene);
        game.setBall(new Ball(100, 100, 10));
        game.setBar(new Bar(100, 100, 100, 10));
        List<Block> blocks = new ArrayList<Block>();
        //blocks.add(new Block(10, 10, 10, 10, 1));
        game.setBlocks(blocks);

        game.playGame(scene);*/
    }

    public static void main(String[] args) {
        launch();
    }
}