package com.example.breakout.Classes;

import java.util.List;

public class Bar {
    private double x;
    private double y;
    private double width;
    private double height;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Bar(double x, double y, double width, double length) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = length;
    }

    public void moveTo(double x) {
        this.x = x;
    }

    public void resize(double width, double length) {
        this.width = width;
        this.height = length;
    }

    public void showBar() {
        // place bar on scene (later implemented)
    }

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

    }
}