package com.example.breakout.Classes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUp {
    private ImageView image;
    private int id;
    private final double yMomentum = 1;
    private final double width = 32;

    public PowerUp(double x, double y) throws FileNotFoundException {

        this.image = new ImageView();

        String dirPath = new File("").getAbsolutePath();
        dirPath += "\\src\\main\\resources\\Item\\";
        this.id = randomID();
        switch (this.id) {
            case(0): dirPath += "ItemBerry.png";
            break;
            case(1): dirPath += "ItemBomb.png";
            break;
            case(2): dirPath += "ItemMelon.png";
            break;
            case(3): dirPath += "ItemPumpkin.png";
            break;
            case(4): dirPath += "ItemRadish.png";
        }

        Image image = new Image(new FileInputStream(dirPath));

        this.image.setImage(image);
        this.image.setLayoutX(x);
        this.image.setLayoutY(y);

        this.image.minWidth(width);
        this.image.minHeight(width);
        this.image.maxWidth(width);
        this.image.maxHeight(width);
    }

    public ImageView getImageView() {
        return image;
    }

    public int randomID() {
        Random random = new Random();
        return random.nextInt(5);
    }

    public int getId() {return this.id;}

    public void moveTo(double y) {
        this.image.setLayoutY(image.getLayoutY() + y);
    }

    public List<Double> getMomentum() {
        List<Double> returnMe = new ArrayList<>();
        returnMe.add(yMomentum);
        return returnMe;
    }

    public List<Double> getPositionalInfo() {
        List<Double> returnMe = new ArrayList<>();
        returnMe.add(image.getLayoutX());
        returnMe.add(image.getLayoutY());
        returnMe.add(width);
        return returnMe;
    }
}