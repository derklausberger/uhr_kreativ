package com.example.breakout.Classes;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.shape.Circle;

public class Ball {
    //momentum means how fast it moves in a certain direction and if its - or +
    private double xmomentum;
    private double ymomentum;
    private double x;
    private double y;
    private double radius;
    private Circle circle;


    public Ball(Circle circle, double dx, double dy) {
        this.circle=circle;
        this.x = circle.getCenterX();
        this.y = circle.getCenterY();
        this.radius = circle.getRadius();
        xmomentum = dx;
        ymomentum = dy;
    }

    public Ball() {
    }

    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;
       // System.out.println(x+" this."+this.x);
        circle.setCenterX(x);
       // System.out.println(y+" this."+this.y);
        circle.setCenterY(y);
    }

    public void resize(double radius) {
        this.radius = radius;
        circle.setRadius(radius);
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