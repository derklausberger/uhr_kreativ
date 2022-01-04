package com.example.breakout.Classes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Block> blocks;
    private static int count = 0;

    public static int getCount() {
        return count;
    }

    public Level() {
        blocks = new ArrayList<Block>();
    }

    public Level(Level level) {
        blocks = level.blocks;
        count = level.count;
    }

    public Level(String filepath) {
        this(loadLevel(filepath));
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public boolean addBlock(Block block){
        Block b2;
        for (int i = 0; i < blocks.size(); i++) {
            b2 = blocks.get(i);
            if ((block.getX() < (b2.getX() + b2.getWidth()) && block.getX() >
                    b2.getX()) && (block.getY() < (b2.getY() + b2.getHeight())
                    && block.getY() > b2.getY())) {
                return false;
            }
        }
        blocks.add(block);
        count++;
        //System.out.println(block.getX() + " " + block.getY());
        return true;
    }

    public boolean replaceBlock(Block block) {
        Block b2;
        for (int i = 0; i < blocks.size(); i++) {
            b2 = blocks.get(i);
            if (block.getID() != b2.getID() && (block.getX() < (b2.getX() + b2.getWidth()) && block.getX() >
                    b2.getX()) && (block.getY() < (b2.getY() + b2.getHeight())
                    && block.getY() > b2.getY())) {
                blocks.remove(block);
                return false;
            }
        }
        //System.out.println(block.getX() + " " + block.getY());
        return true;
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

    public static Level loadLevel(String filepath) {
        try {
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Level) ois.readObject();
            // as this was below the return i commented it out as it would throw an error ==> ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
