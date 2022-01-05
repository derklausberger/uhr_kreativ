package com.example.breakout;

import com.example.breakout.Classes.Block;
import com.example.breakout.Classes.Level;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class levelEditorController {
    @FXML
    private Rectangle redRect;// = new Rectangle(1000 + 90, 50, 100, 30);

    @FXML
    private Rectangle blueRect;

    @FXML
    private Rectangle greenRect;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button saveLevelBtn;

    @FXML
    private Button exitLevelEditorBtn;

    @FXML
    private TextField name;

    private Level level;

    public levelEditorController() {
    }

    @FXML
    private void initialize() {
        redRect.setFill(Color.RED);
        blueRect.setFill(Color.BLUE);
        greenRect.setFill(Color.GREEN);

        redRect.setOnMouseEntered(e -> placeBlock(new Block(level.getCount(), 1090, 50, 100, 30, 1)));
        blueRect.setOnMouseEntered(e -> placeBlock(new Block(level.getCount(), 1090, 130, 100, 30, 2)));
        greenRect.setOnMouseEntered(e -> placeBlock(new Block(level.getCount(), 1090, 210, 100, 30, 3)));

        level = new Level();

        loadBlocks();

        saveLevelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e -> level.saveLevel(name.getText())));

        exitLevelEditorBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e -> {
            try {
                ControllerScreens.SwitchToMain();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));

        name.addEventHandler(KeyEvent.KEY_RELEASED, (e -> saveLevelBtn.setDisable(name.getText().equals(""))));

        saveLevelBtn.setDisable(true);
    }

    private void loadBlocks() {
        try {
            level = Level.loadLevel("UHR");
            Block block;
            for (int i = 0; i < level.getBlocks().size(); i++) {
                block = level.getBlocks().get(i);
                placeBlock(block);
            }
        } catch (Exception e) {

        }
    }

    private void placeBlock(Block b) {
        Rectangle rect;

        if (b.getStrength() == 1) {
            rect = new Rectangle(b.getX(), b.getY(), 100, 30);
            // block = new Block(level.getCount(), rect, b.getStrength());
            // block = new Block(level.getCount(), 1090, 50, 100, 30, strength);
            if (b.getX() == 1090) {
                rect.setFill(Color.DARKRED);
            } else {
                rect.setFill(Color.RED);
            }
        } else if (b.getStrength() == 2) {
            rect = new Rectangle(b.getX(), b.getY(), 100, 30);
            if (b.getX() == 1090) {
                rect.setFill(Color.DARKBLUE);
            } else {
                rect.setFill(Color.BLUE);
            }
        } else {
            rect = new Rectangle(b.getX(), b.getY(), 100, 30);
            if (b.getX() == 1090) {
                rect.setFill(Color.DARKGREEN);
            } else {
                rect.setFill(Color.GREEN);
            }
        }

        for (int i = 0; i < mainPane.getChildren().size(); i++) {
            try {
                Node n = mainPane.getChildren().get(i);
                if (n.getClass().getSimpleName().equals("Rectangle")) {
                    Rectangle r = (Rectangle) n;
                    if (r.getX() == 1090 && r.getY() != rect.getY()) {
                        mainPane.getChildren().remove(r);
                    }
                }
            } catch (ClassCastException e) {
                System.out.println("not a rectangle");
            }
        }

        EventHandler handler = (EventHandler<MouseEvent>) e -> {
            if (rect.getX() + 50 > 1000) {
                mainPane.getChildren().remove(rect);

                level.removeBlock(b);
            }
            mainPane.setCursor(Cursor.DEFAULT);
        };

        rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e -> {
            rect.setX(e.getSceneX() - 50);
            rect.setY(e.getSceneY() - 15);

            b.setX(e.getSceneX() - 50);
            b.setY(e.getSceneY() - 15);

            if ((e.getSceneX() + 50 > 1000 || e.getSceneX() - 50 < 0) ||
                    e.getSceneY() - 15 < 0 || e.getSceneY() + 15 > 700 ||
                    !level.replaceBlock(b)) {
                if (b.getStrength() == 1) {
                    rect.setFill(Color.DARKRED);
                } else if (b.getStrength() == 2) {
                    rect.setFill(Color.DARKBLUE);
                } else {
                    rect.setFill(Color.DARKGREEN);
                }
            } else {
                if (b.getStrength() == 1) {
                    rect.setFill(Color.RED);
                } else if (b.getStrength() == 2) {
                    rect.setFill(Color.BLUE);
                } else {
                    rect.setFill(Color.GREEN);
                }
            }
        }));

        mainPane.getChildren().add(rect);

        rect.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, handler);

        rect.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            if ((e.getSceneX() + 50 > 1000 || e.getSceneX() - 50 < 0) ||
                    e.getSceneY() - 15 < 0 || e.getSceneY() + 15 > 700 ||
                    !level.replaceBlock(b)) {
                mainPane.getChildren().remove(rect);
                level.removeBlock(b);
            }
            mainPane.setCursor(Cursor.DEFAULT);
        });


        rect.addEventHandler(MouseEvent.MOUSE_PRESSED, (e -> {
            rect.removeEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                    handler);
            rect.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, ev ->
                    mainPane.setCursor(Cursor.DEFAULT));
            mainPane.setCursor(Cursor.MOVE);
        }));

        rect.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, (e -> {
            mainPane.setCursor(Cursor.MOVE);
        }));

        if (!level.getBlocks().contains(b)) {
            level.addBlock(b);
        }

    }
}
