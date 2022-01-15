package com.example.breakout.Classes;

import java.util.List;

public class Game {
    private Ball ball;
    private Bar bar;
    private Level level;
    //private Scene scene;
    //private AnchorPane leftside = new AnchorPane();

    public Game() {
        ball = null;
        bar = null;
        level = null;
    }

    /*
    public Game(Scene scene, AnchorPane pane) {
        ball = null;
        bar = null;
        //blocks = null;///////////////////////////////////////////////
        this.scene = scene;
        leftside = pane;
    }
     */

    public Game(Level level) {
        this();
        this.level = level;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public Level getLevel() {
        return level;
    }

    public Ball getBall() {
        return ball;
    }

    public void moveBall() {
        List<Double> momentum = ball.getmomentum();
        List<Double> position = ball.getpositionalinfo();

        ball.moveTo(position.get(0) + momentum.get(0),
                position.get(1) + momentum.get(1));
    }

    public void moveBar(double xchange) {

        bar.move(xchange);
        //bar.movelimit(xchange, leftside.getMaxWidth(), leftside.getMinWidth());
    }

    // return true: not lost
    // return false: player lost game (ball touched bottom side)
    public boolean checkBall() {
        List<Double> position = ball.getpositionalinfo();
        List<Double> momentum = ball.getmomentum();

        for (Block block : level.getBlocks()) {
            int touches = block.checkblock(position);
            // ball touches block on left or right side
            if (touches == 1 || touches == 2) {
                ball.changemomentum((momentum.get(0) * -1), momentum.get(1));
                block.lowerHP(1);
                if (block.getStrength() <= 0) {
                    level.removeBlock(block);
                }
                return true;
            } else if (touches == 3 || touches == 4) { // ball touches block on top or bottom side
                ball.changemomentum(momentum.get(0), (momentum.get(1) * -1));
                block.lowerHP(1);
                if (block.getStrength() <= 0) {
                    level.removeBlock(block);
                }
                return true;
            }
        }

        // ball touches left or right side of the window
        if (position.get(0) - position.get(2) <= 0) {
            ball.changemomentum((momentum.get(0) * -1), momentum.get(1));
            return true;
        } else if (position.get(0) + position.get(2) >= 1000) { // leftside.getWidth()
            ball.changemomentum((momentum.get(0) * -1), momentum.get(1));
            return true;
        }

        // ball touches top or bottom side of the window
        if (position.get(1) - position.get(2) == 0) {
            ball.changemomentum(momentum.get(0), (momentum.get(1) * -1));
            return true; // seite
            //moveBall();
        } else if (position.get(1) + position.get(2) >= 720) { // leftside.getHeigth()
            bar.stop();
            return false; // verloren
        }
        // ball touches bar
        else if (bar.checkbar(position)) {
            ball.changemomentum(momentum.get(0), (momentum.get(1) * -1));
            return true; // touch bar
        }
        return true;
    }

    /*
    public void playGame(Scene scene) throws InterruptedException {
        this.scene = scene;
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

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Circle circle = (Circle) scene.lookup("#circle");
                while (checkBall()) {
                    moveBall();
                    circle.setCenterX(ball.getpositionalinfo().get(0));
                    circle.setCenterY(ball.getpositionalinfo().get(1));
                    //((Circle) scene.lookup("#circle")).setCenterY(game.getBall().getpositionalinfo().get(1));
                    try {
                        TimeUnit.MILLISECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                youLost();
            }
        });

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                t.start();
            }
        });
        t.join();

        /*Thread barFred = new Thread(new Runnable() {
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

                while (stillPlaying) {

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
                while (checkBall()) {
                    moveBall();
                }
                youLost();
            }
        });

        //barFred.start();
        ballFred.start();*/
}
