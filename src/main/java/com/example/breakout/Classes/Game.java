package com.example.breakout.Classes;

import com.example.breakout.ControllerScreens;
import com.example.breakout.LevelEditorController;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Ball ball;
    private Bar bar;
    private Level level;
    private List<PowerUp> powerUp;

    public Game() {
        ball = null;
        bar = null;
        level = null;
        powerUp = new ArrayList<>();
    }

    public Game(Level level) {
        this();
        this.level = level;
    }

    public void setPowerUp(List<PowerUp> powerUp) {this.powerUp = powerUp;}

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

    public List<PowerUp> getPowerUp() { return powerUp; }

    public PowerUp moveDown() {
        for (PowerUp powerUp : getPowerUp()) {
            powerUp.moveTo(3);
            if(bar.checkItem(powerUp.getPositionalInfo())){
                this.powerUp.remove(powerUp);
                switch (powerUp.randomID()) {
                    case (0):
                        ball.resize(2);
                        break;
                    case (1):
                        bar.resize(200, 40);
                        break;
                    case (2):
                        bar.resize(200, 40);
                        break;
                    case (3):
                        ball.resize(2);
                        break;
                }
                return powerUp;
            } else if(powerUp.getPositionalInfo().get(1) >= ControllerScreens.windowHeight){
                this.powerUp.remove(powerUp);
                return powerUp;
            }
        }
        return null;
    }


    public void movePowerUps() {
        for (PowerUp powerUp : powerUp) {
            List<Double> momentum = powerUp.getMomentum();
            List<Double> position = powerUp.getPositionalInfo();
            powerUp.moveTo(position.get(0) + momentum.get(0));
        }
    }

    public void moveBall() {
        List<Double> momentum = ball.getMomentum();
        List<Double> position = ball.getPositionalInfo();

        ball.moveTo(position.get(0) + momentum.get(0),
                    position.get(1) + momentum.get(1));
    }

    public void moveBar(double xchange) {
        bar.move(xchange);
    }

    // return true: not lost
    // return false: player lost game (ball touched bottom side)
    public boolean checkBall() throws FileNotFoundException {
        List<Double> position = ball.getPositionalInfo();
        List<Double> momentum = ball.getMomentum();

        for (Block block : level.getBlocks()) {
            int touches = block.checkBlock(position);
            // ball touches block on left or right side
            if (touches == 1 || touches == 2) {
                ball.changeMomentum((momentum.get(0) * -1), momentum.get(1));
                block.lowerHP(1);
                if (block.getStrength() <= 0) {
                    level.removeBlock(block);
                    powerUp.add(new PowerUp(block.getX(), block.getY())); // imageView abiehen bei x
                }
                return true;
            } else if (touches == 3 || touches == 4) { // ball touches block on top or bottom side
                ball.changeMomentum(momentum.get(0), (momentum.get(1) * -1));
                block.lowerHP(1);
                if (block.getStrength() <= 0) {
                    level.removeBlock(block);
                    powerUp.add(new PowerUp(block.getX(), block.getY())); // imageView abiehen bei x
                }
                return true;
            }
        }

        // ball touches left or right side of the window
        if (position.get(0) - position.get(2) <= 0) {
            ball.changeMomentum((momentum.get(0) * -1), momentum.get(1));
            return true;
        } else if (position.get(0) + position.get(2) >= 1000) {
            ball.changeMomentum((momentum.get(0) * -1), momentum.get(1));
            return true;
        }

        // ball touches top or bottom side of the window
        if (position.get(1) - position.get(2) == 0) {
            ball.changeMomentum(momentum.get(0), (momentum.get(1) * -1));
            return true; // left or right
            //moveBall();
        } else if (position.get(1) + position.get(2) >= 720) {
            bar.stop();
            return false; // lost
        }
        // ball touches bar
        else if (bar.checkBar(position)) {
            ball.changeMomentum(momentum.get(0), (momentum.get(1) * -1));
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
