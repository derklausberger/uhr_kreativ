package com.example.breakout.Classes;

import javafx.scene.shape.Rectangle;

import java.util.List;

public class Bar {
    private double x;
    private double y;
    private double width;
    private double height;
    private Rectangle rectangle;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Bar(Rectangle rectangle) {
        this.rectangle = rectangle;
        this.x = rectangle.getLayoutX();
        this.y = rectangle.getLayoutY();
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
    }

    public Bar() {
    }

    public void move(double x) {
        this.x += x;
        rectangle.setLayoutX(this.x);
    }

    public void movelimit(double x, double gamesidemaxwidth, double gamesideminwidth) {
        this.x += x;
        if ((this.x + this.width / 2) > gamesidemaxwidth) {
            this.x = gamesidemaxwidth - this.width / 2;
        } else if (this.x - this.width / 2 < gamesideminwidth) {
            this.x = gamesidemaxwidth + this.width / 2;
        }
        rectangle.setLayoutX(this.x);
    }

    public void resize(double width, double length) {
        this.width = width;
        this.height = length;
    }

    public void showBar() {
        // place bar on scene (later implemented)
    }
      /*
    //checks if a ball intersects the bar, uses the list created from getpositionalinfo in the ball class
    //these would be x,y and radius
    //got this method from https://www.geeksforgeeks.org/check-if-any-point-overlaps-the-given-circle-and-rectangle/
    //could be moved into Game class if desired with minimal modifications
    public boolean checkbar(List<Double> ballinfo) {
        double Xn = Math.max(x - (width / 2), Math.min(ballinfo.get(0), x + (width / 2)));
        double Yn = Math.max(y - (height / 2), Math.min(ballinfo.get(1), y + (height / 2)));
        double Dx = Xn - ballinfo.get(0);
        double Dy = Yn - ballinfo.get(1);
        return (Dx * Dx + Dy * Dy) <= ballinfo.get(2) * ballinfo.get(2);
    }*/

    public boolean checkbar(List<Double> ballinfo) {
        double Xn = Math.max(x, Math.min(ballinfo.get(0), x + width));
        double Yn = Math.max(y, Math.min(ballinfo.get(1), y + height));
        double Dx = Xn - ballinfo.get(0);
        double Dy = Yn - ballinfo.get(1);
        return (Dx * Dx + Dy * Dy) <= ballinfo.get(2) * ballinfo.get(2);
    }


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}