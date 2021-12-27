package com.example.breakout.Classes;

import java.util.ArrayList;
import java.util.List;

public class Ball {
    //momentum means how fast it moves in a certain direction and if its - or +
    private double xmomentum;
    private double ymomentum;
    private double x;
    private double y;
    private double radius;

    public Ball(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        xmomentum = 5;
        ymomentum = -5;
    }

    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void resize(double radius) {
        this.radius = radius;
    }

    public void changemomentum(double xmomentum, double ymomentum) {
        this.xmomentum = xmomentum;
        this.ymomentum = ymomentum;
    }

    public List<Double> getmomentum() {
        List<Double> returnme = new ArrayList<Double>();
        returnme.add(xmomentum);
        returnme.add(ymomentum);
        return returnme;
    }

    //gets you x,y and radius in that order in a list
    public List<Double> getpositionalinfo() {
        List<Double> returnme = new ArrayList<Double>();
        returnme.add(x);
        returnme.add(y);
        returnme.add(radius);
        return returnme;
    }
}