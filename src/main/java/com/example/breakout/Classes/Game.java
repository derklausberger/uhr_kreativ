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
    private Scene scene;
    private boolean stillPlaying = true;

    public Game() {
        ball = null;
        bar = null;
        blocks = null;
        scene = null;
    }

    public Game(Game game) {
        ball = game.ball;
        bar = game.bar;
        blocks = game.blocks;
        scene = null;
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

    public void moveBall() {
        List<Double> momentum = ball.getmomentum();
        List<Double> position = ball.getpositionalinfo();
        checkMomentum();
        ball.moveTo(position.get(0) + momentum.get(0),
                position.get(1) + momentum.get(1));
    }

    public void moveBar(double x) {
        bar.moveTo(x);
    }

    public void checkMomentum() {
        List<Double> position = ball.getpositionalinfo();
        List<Double> momentum = ball.getmomentum();

        for (Block block : blocks) {
            int touches = block.checkblock(position);
            // ball touches block on left or right side
            if (touches == 1 || touches == 2) {
                ball.changemomentum((momentum.get(0) * -1), momentum.get(1));
            } else if (touches == 3 || touches == 4) { // ball touches block on top or bottom side
                ball.changemomentum(momentum.get(0), (momentum.get(1) * -1));
            }
        }

        // ball touches left or right side of the window
        if (position.get(0) - position.get(2) == 0) {
            ball.changemomentum((momentum.get(0) * -1), momentum.get(1));
        } else if (position.get(0) + position.get(2) == scene.getWidth()) {

        }

        // ball touches top or bottom side of the window
        if (position.get(1) - position.get(2) == 0) {
            ball.changemomentum(momentum.get(0), (momentum.get(1) * -1));
        } else if (position.get(1) + position.get(2) == scene.getHeight()) {
            youLost();
        }
    }

    public void playGame(String filepath, Scene scene) {
        this.scene = scene;
        Thread barFred = new Thread(new Runnable() {
            @Override
            public void run() {
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

                while (run) {

                }
            }
        });

        Thread ballFred = new Thread(new Runnable() {
            @Override
            public void run() {
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

                scene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.SPACE) {
                        start();
                    }
                });
            }

            public void start() {
                while (stillPlaying) {
                    checkMomentum();
                    moveBall();
                }
            }
        });

        //barFred.start();
        ballFred.start();
    }

    public void youLost() {
        stillPlaying = false;
        System.out.println("you lost boy");
    }
}