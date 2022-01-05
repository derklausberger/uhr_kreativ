package com.example.breakout.Classes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable {
    private List<Block> blocks;
    private int count = 0;
    private String name;

    public Level() {
        blocks = new ArrayList<>();
    }

    public Level(Level level) {
        blocks = level.blocks;
        count = level.count;
        name = level.name;
    }

    public Level(String filepath) {
        this(loadLevel(filepath));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public boolean addBlock(Block block) {
        Block b2;
        for (int i = 0; i < blocks.size(); i++) {
            b2 = blocks.get(i);
            if ((((block.getX() < (b2.getX() + b2.getWidth())
                    && block.getX() > b2.getX())
                    || (block.getX() + block.getWidth() > b2.getX()
                    && block.getX() < b2.getX()))
                    && ((block.getY() < (b2.getY() + b2.getHeight())
                    && block.getY() > b2.getY())
                    || (block.getY() + block.getHeight() > b2.getY()
                    && block.getY() < b2.getY())))) {
                return false;
            }
        }
        blocks.add(block);
        count++;
        return true;
    }

    public void removeBlock(Block block) {
        blocks.remove(block);
    }

    public boolean replaceBlock(Block block) {
        Block b2;
        for (int i = 0; i < blocks.size(); i++) {
            b2 = blocks.get(i);
            if (block.getID() != b2.getID()
                    && (((block.getX() < (b2.getX() + b2.getWidth())
                    && block.getX() >= b2.getX())
                    || (block.getX() + block.getWidth() > b2.getX()
                    && block.getX() <= b2.getX()))
                    && ((block.getY() < (b2.getY() + b2.getHeight())
                    && block.getY() >= b2.getY())
                    || (block.getY() + block.getHeight() > b2.getY()
                    && block.getY() <= b2.getY())))) {
                return false;
            }
        }
        return true;
    }

    public void saveLevel(String filepath) {
        try {
            String dirPath = new File("").getAbsolutePath();
            dirPath += "\\levels\\";

            File directory = new File(dirPath);
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(dirPath + filepath);
            if (file.exists()) {
                file.delete();
            }

            FileOutputStream fos = new FileOutputStream(dirPath + filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Level loadLevel(String filepath) {
        try {
            String dirPath = new File("").getAbsolutePath();
            dirPath += "\\levels\\";

            FileInputStream fis = new FileInputStream(dirPath + filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Level level = (Level) ois.readObject();
            ois.close();
            fis.close();
            return level;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
