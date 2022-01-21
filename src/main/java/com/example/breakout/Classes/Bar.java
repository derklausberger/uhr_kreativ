package com.example.breakout.Classes;

import javafx.scene.shape.Rectangle;

import java.util.List;

public class Bar {
    private double x;
    private final double y;
    private double width;
    private double height;
    private Rectangle rectangle;
    private boolean moveAble = true;


    public Bar(Rectangle rectangle) {
        this.rectangle = rectangle;
        this.x = rectangle.getLayoutX();
        this.y = rectangle.getLayoutY();
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
    }

    public void move(double x) {
        if (moveAble) {
            this.x += x;
            rectangle.setLayoutX(this.x);
        }
    }

    public void stop() {
        moveAble = false;
    }


    public void resize(double width, double length) {
        this.width = width;
        this.height = length;
    }

    public boolean checkBar(List<Double> ballInfo) {
        double Xn = Math.max(x, Math.min(ballInfo.get(0), x + width));
        double Yn = Math.max(y, Math.min(ballInfo.get(1), y + height));
        double Dx = Xn - ballInfo.get(0);
        double Dy = Yn - ballInfo.get(1);
        return (Dx * Dx + Dy * Dy) <= ballInfo.get(2) * ballInfo.get(2);
    }


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}