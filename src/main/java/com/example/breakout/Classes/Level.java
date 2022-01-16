package com.example.breakout.Classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable {
    private final List<Block> blocks;
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

    public boolean addBlock(Block block) {
        for (Block b : blocks) {
            if ((((block.getX() < (b.getX() + b.getWidth())
                    && block.getX() > b.getX())
                    || (block.getX() + block.getWidth() > b.getX()
                    && block.getX() < b.getX()))
                    && ((block.getY() < (b.getY() + b.getHeight())
                    && block.getY() > b.getY())
                    || (block.getY() + block.getHeight() > b.getY()
                    && block.getY() < b.getY())))) {
                return false;
            }
        }
        blocks.add(block);
        count++;
        return true;
    }

    public Block findBlock(double x, double y) {
        for (Block block : blocks) {
            if (block.getX() == x && block.getY() == y) {
                return block;
            }
        }
        return null;
    }

    public void removeBlock(Block block) {
        blocks.remove(block);
    }

    public boolean replaceBlock(Block block) {
        for (Block b : blocks) {
            if (block.getID() != b.getID()
                    && (((block.getX() < (b.getX() + b.getWidth())
                    && block.getX() >= b.getX())
                    || (block.getX() + block.getWidth() > b.getX()
                    && block.getX() <= b.getX()))
                    && ((block.getY() < (b.getY() + b.getHeight())
                    && block.getY() >= b.getY())
                    || (block.getY() + block.getHeight() > b.getY()
                    && block.getY() <= b.getY())))) {
                return false;
            }
        }
        return true;
    }

    public static void deleteLevel(String filepath) {
        String dirPath = new File("").getAbsolutePath();
        dirPath += "\\levels\\";

        File file = new File(dirPath + filepath);
        if (file.exists()) {
            file.delete();
        }
    }

    public void overwriteLevel(String filepath) {
        deleteLevel(filepath);

        saveLevel(filepath);
    }

    public boolean saveLevel(String filepath) {
        try {
            String dirPath = new File("").getAbsolutePath();
            dirPath += "\\levels\\";

            File directory = new File(dirPath);
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(dirPath + filepath);
            if (file.exists()) {
                return false;
            }

            FileOutputStream fos = new FileOutputStream(dirPath + filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static Level loadLevel(String filepath) {
        try {
            String dirPath = new File("").getAbsolutePath();
            dirPath += "\\levels\\";

            FileInputStream fis = new FileInputStream(dirPath + filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            //System.out.println(filepath);
            Level level = (Level) ois.readObject();
            ois.close();
            fis.close();
            return level;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Level[] loadLevelList() {
        try {
            String dirPath = new File("").getAbsolutePath();
            dirPath += "\\levels\\";

            String[] filenames = new File(dirPath).list();
            Level[] levelList = new Level[filenames.length];

            for (int i = 0; i < filenames.length; i++) {
                //new File(dirPath + filenames[i]).delete();
                levelList[i] = loadLevel(filenames[i]);
            }

            return levelList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
