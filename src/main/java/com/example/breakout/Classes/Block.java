package com.example.breakout.Classes;

import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.List;

public class Block implements Serializable {
    //private Rectangle rectangle;
    private int ID;
    private double x;
    private double y;
    private double width;
    private double height;
    private int strength;
    /*
    One thing I am unsure of is if we should make this into its own class or just have a String which gets
    used in a method in game.
    private Powerup/String powerup;
    */

    public Block(int ID, Rectangle rectangle, int strength) {
        this.ID = ID;
        //this.rectangle = rectangle;
        this.x = rectangle.getLayoutX();
        this.y = rectangle.getLayoutY();
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
        this.strength = strength;
    }

    public Block(int ID, double x, double y, double width, double height, int strength) {
        this.ID = ID;
        //this.rectangle = null;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.strength = strength;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        //rectangle.setLayoutX(x);
    }

    /*public void setX_(double x) {
        this.x = x;
        rectangle.setLayoutX(x);
    }*/

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        //rectangle.setLayoutY(y);
    }


    /*public void setY_(double y) {
        this.y = y;
        rectangle.setLayoutY(y);
    }*/

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public int getID() {
        return ID;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    //checks if a ball intersects a block, if strength is below 1 it is always false, uses the list created from getpositionalinfo in the ball class
    //these would be x,y and radius
    //got this method from https://www.geeksforgeeks.org/check-if-any-point-overlaps-the-given-circle-and-rectangle/
    //could be moved into Game class if desired with minimal modifications
    //0= ball doesnt hit block
    //1= ball hits from the left
    //2= ball hits from the right
    //3= ball hits from the top
    //4= ball hits from the bottom
    public int checkblock(List<Double> ballinfo) {
        if (strength < 1) {
            return 0;
        }
        double Xn = Math.max(x, Math.min(ballinfo.get(0), x + width));
        double Yn = Math.max(y, Math.min(ballinfo.get(1), y + height));
        double Dx = Xn - ballinfo.get(0);
        double Dy = Yn - ballinfo.get(1);
        if ((Dx * Dx + Dy * Dy) <= ballinfo.get(2) * ballinfo.get(2)) {
            //this part I though of on my own(Peter), if you find a mistake or a better option feel free to improve it
            double xdif = x - ballinfo.get(0);
            double ydif = y - ballinfo.get(1);
            if (Math.abs(xdif) > Math.abs(ydif)) {
                if (xdif > 0) {
                    System.out.println("left");
                    return 1;
                } else {
                    System.out.println("right");
                    return 2;
                }
            } else {
                if (ydif > 0) {
                    System.out.println("top");
                    return 3;
                } else {
                    System.out.println("bottom");
                    return 4;
                }
            }
        }
        return 0;
    }

    //true means the block still exists, false means its gone, only do this after
    //using checkBlock to see if the block gets touched
    public boolean lowerHP(int damage) {
        strength -= damage;
        if (strength < 1) {
            //rectangle.setVisible(false);
            return false;
        }
        return true;

    }
}