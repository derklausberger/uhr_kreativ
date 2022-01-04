package com.example.breakout.Classes;

import java.util.List;

public class Block {
    private double x;
    private double y;
    private double width;
    private double height;
    private int strength;
    private int ID;
    /*
    One thing I am unsure of is if we should make this into its own class or just have a String which gets
    used in a method in game.
    private Powerup/String powerup;
    */

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getID() {
        return ID;
    }

    public Block(int ID, double x, double y, double width, double height, int strength) {
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.strength = strength;
    }

    //checks if a ball intersects a block, if strength is below 1 it is always false, uses the list created from getpositionalinfo in the ball class
    //these would be x,y and radius
    //got this method from https://www.geeksforgeeks.org/check-if-any-point-overlaps-the-given-circle-and-rectangle/
    //could be moved into Game class if desired with minimal modifications
    //0= ball doesnt hit block
    //1= ball hits from the left
    //2= ball hits from the right
    //3= ball hits from the bottom
    //4= ball hits from the top
    public int checkblock(List<Double> ballinfo) {
        if (strength < 1) {
            return 0;
        }
        double Xn = Math.max(x - (width / 2), Math.min(ballinfo.get(0), x + (width / 2)));
        double Yn = Math.max(y - (height / 2), Math.min(ballinfo.get(1), y + (height / 2)));
        double Dx = Xn - ballinfo.get(0);
        double Dy = Yn - ballinfo.get(1);
        if ((Dx * Dx + Dy * Dy) <= ballinfo.get(2) * ballinfo.get(2)) {
            //this part I though of on my own(Peter), if you find a mistake or a better option feel free to improve it
            double xdif = x - ballinfo.get(0);
            double ydif = y - ballinfo.get(1);
            if (Math.abs(xdif) > Math.abs(ydif)) {
                if (xdif > 0) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (ydif > 0) {
                    return 3;
                } else {
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
            return false;
        }
        return true;

    }
}