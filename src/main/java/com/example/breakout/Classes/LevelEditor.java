package com.example.breakout.Classes;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class LevelEditor {
    private Game game;

    public LevelEditor() {
        game = null;
    }

    public LevelEditor(String filepath) {
        game = Game.loadGameFromFile(filepath);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void saveLevel(String filepath) {
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void importLevel(String filepath) {
        game = Game.loadGameFromFile(filepath);
    }
}
