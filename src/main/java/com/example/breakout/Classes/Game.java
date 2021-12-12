package com.example.breakout.Classes;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class Game {
    private Ball ball;
    private Bar bar;
    private List<Block> blocks;

    public Game() {
        ball = null;
        bar = null;
        blocks = null;
    }

    public Game(Game game) {
        ball = game.ball;
        bar = game.bar;
        blocks = game.blocks;
    }

    public Game(String filepath) {
        this(loadGameFromFile(filepath));
    }

    public static Game loadGameFromFile(String filepath) {
        try {
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Game) ois.readObject();
            // as this was below the return i commented it out as it would throw an error ==> ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void moveBall(double x, double y) {
        ball.moveTo(x, y);
    }

    public void moveBar(double x) {
        bar.moveTo(x);
    }

    public void runBar(Scene scene) {

    }

    public void runBall(Scene scene) {
        while (true) {
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.SPACE) {
                    ;
                }
            });
        }
    }

    public void playGame(String filepath, Scene scene) {
        Thread barFred = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    scene.setOnKeyPressed(event -> {
                        if (event.getCode() == KeyCode.RIGHT) {
                            moveBar(bar.getX() + 1); //  1 is dummy value
                            // moveBall() ???
                        } // else if?? if right and left arrow get pressed
                          // simultaneously
                        if (event.getCode() == KeyCode.LEFT) {
                            moveBar(bar.getX() - 1); //  1 is dummy value
                        }
                    });
                }
            }
        });

        Thread ballFred = new Thread(new Runnable() {
            @Override
            public void run() {
                scene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.SPACE) {
                        start();
                    }
                });
            }

            public void start() {
                while (true) {
                    List<Double> momentum = ball.getmomentum();
                    List<Double> position = ball.getpositionalinfo();
                    moveBall(position.get(0) + momentum.get(0),
                            position.get(1) + momentum.get(1));

                }
            }
        });

        barFred.start();
        ballFred.start();
    }
}