package com.example.breakout;

import com.example.breakout.Classes.Block;
import com.example.breakout.Classes.Level;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class levelEditorController {
    @FXML
    private Rectangle redRect;// = new Rectangle(1000 + 90, 50, 100, 30);

    @FXML
    private Rectangle blueRect;

    @FXML
    private Rectangle greenRect;

    @FXML
    private AnchorPane mainPane;

    private Level level;

    public levelEditorController() {
    }

    @FXML
    private void initialize() {
        redRect.setFill(Color.RED);
        blueRect.setFill(Color.BLUE);
        greenRect.setFill(Color.GREEN);

        redRect.setOnMouseEntered(e -> placeBlock(e, 1));
        blueRect.setOnMouseEntered(e -> placeBlock(e, 2));
        greenRect.setOnMouseEntered(e -> placeBlock(e, 3));

        level = new Level();
    }

    private void placeBlock(MouseEvent event, int strength) {
        Rectangle rect;
        Block block;

        if (strength == 1) {
            rect = new Rectangle(1090, 50, 100, 30);
            block = new Block(level.getCount(), 1090, 50, 100, 30, strength);
            rect.setFill(Color.DARKRED);
        } else if (strength == 2) {
            rect = new Rectangle(1090, 130, 100, 30);
            block = new Block(level.getCount(), 1090, 130, 100, 30, strength);
            rect.setFill(Color.DARKBLUE);
        } else {
            rect = new Rectangle(1090, 210, 100, 30);
            block = new Block(level.getCount(), 1090, 210, 100, 30, strength);
            rect.setFill(Color.DARKGREEN);
        }

        EventHandler handler = (EventHandler<MouseEvent>) e -> {
            if ((e.getSceneX() + 50 > 1000 || e.getSceneX() - 50 < 0) ||
                    !level.replaceBlock(block)) {
                mainPane.getChildren().remove(rect);
            }
        };

        rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e -> {
            rect.setX(e.getSceneX() - 50);
            rect.setY(e.getSceneY() - 15);

            block.setX(e.getSceneX() - 50);
            block.setY(e.getSceneY() - 15);
        }));

        rect.addEventHandler(MouseEvent.MOUSE_RELEASED, handler);

        rect.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, handler);

        /*rect.addEventHandler(MouseEvent.MOUSE_CLICKED, (e -> {
            rect.removeEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                    handler);
            System.out.println("rem");
        }));*/

        mainPane.getChildren().add(rect);
        level.addBlock(block);
        mainPane.setCursor(Cursor.MOVE);
    }
}