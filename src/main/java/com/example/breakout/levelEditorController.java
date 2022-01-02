package com.example.breakout;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
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
    }

    private void placeBlock(MouseEvent event, int strength) {
        Rectangle rect;

        if (strength == 1) {
            rect = new Rectangle(1090, 50, 100, 30);
            rect.setFill(Color.DARKRED);
        } else if (strength == 2) {
            rect = new Rectangle(1090, 130, 100, 30);
            rect.setFill(Color.DARKBLUE);
        } else {
            rect = new Rectangle(1090, 210, 100, 30);
            rect.setFill(Color.DARKGREEN);
        }

        EventHandler handler = (EventHandler<MouseEvent>) e -> {
            mainPane.getChildren().remove(rect);
        };

        rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e -> {
            rect.setX(e.getSceneX() - 50);
            rect.setY(e.getSceneY() - 15);
        }));

        rect.addEventHandler(MouseEvent.MOUSE_RELEASED, (e -> {
            if (e.getSceneX() + 50 > 1000 || e.getSceneX() - 50 < 0) {
                mainPane.getChildren().remove(rect);
            }
        }));

        rect.addEventHandler(MouseEvent.MOUSE_CLICKED, (e ->
                rect.removeEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                        handler)));

        rect.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, handler);

        mainPane.getChildren().add(rect);
    }
}
