package com.example.breakout;

import com.example.breakout.Classes.Block;
import com.example.breakout.Classes.Level;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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
    private AnchorPane placePane;

    @FXML
    private Button saveLevelBtn;

    @FXML
    private Button exitLevelEditorBtn;

    @FXML
    private TextField name;

    private Level level;

    private int blockSelected = 0;

    private final Region selectRect = new Region();

    private final Point p = new Point();

    private double x = 0, y = 0;

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

        saveLevelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e -> {
            Alert alert;
            if (level.saveLevel(name.getText())) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Speichern");
                alert.setHeaderText("Level gespeichert.");
                alert.setContentText("Level wurde unter dem Namen \"" + name.getText() + "\" abgespeichert.");
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Speichern");
                alert.setHeaderText("Level mit dem Namen \"" + name.getText() + "\" existiert bereits.");
                alert.setContentText("Möchten Sie trotzdem fortfahren?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == ButtonType.OK) {
                        level.overwriteLevel(name.getText());
                    }
                }
            }
        }));

        exitLevelEditorBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e -> {
            try {
                ControllerScreens.SwitchToMain();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));

        name.addEventHandler(KeyEvent.KEY_RELEASED, (e -> saveLevelBtn.setDisable(name.getText().equals(""))));

        saveLevelBtn.setDisable(true);

        selectRect.setStyle("-fx-border-width: 2px; -fx-border-color: black; -fx-border-style: dashed;");

        placePane.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (blockSelected == 0) {
                p.setLocation(e.getSceneX(), e.getSceneY());

                placePane.getChildren().add(selectRect);
            } else {
                blockSelected = 0;
                reloadScreen();

                selectRect.setMinHeight(0);
                selectRect.setMinHeight(0);
                selectRect.setMinWidth(0);
                selectRect.setMaxWidth(0);
                selectRect.setLayoutX(0);
                selectRect.setLayoutY(0);
            }
        });

        placePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (p.getX() > e.getSceneX()) {
                selectRect.setMinWidth(p.getX() - e.getSceneX());
                selectRect.setMaxWidth(p.getX() - e.getSceneX());
                selectRect.setLayoutX(e.getSceneX());
            } else {
                selectRect.setMinWidth(e.getSceneX() - p.getX());
                selectRect.setMaxWidth(e.getSceneX() - p.getX());
                selectRect.setLayoutX(p.getX());
            }

            if (p.getY() > e.getSceneY()) {
                selectRect.setMinHeight(p.getY() - e.getSceneY());
                selectRect.setMaxHeight(p.getY() - e.getSceneY());
                selectRect.setLayoutY(e.getSceneY());
            } else {
                selectRect.setMinHeight(e.getSceneY() - p.getY());
                selectRect.setMaxHeight(p.getY() - e.getSceneY());
                selectRect.setLayoutY(p.getY());
            }
        });


        placePane.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            Block b;
            for (Rectangle r : getRectangles()) {
                if (((r.getX() + 100 > selectRect.getLayoutX()) && (r.getX()
                        < selectRect.getLayoutX() + selectRect.getWidth()))
                        && ((r.getY() + 30 > selectRect.getLayoutY()) &&
                        (r.getY() < selectRect.getLayoutY() +
                                selectRect.getHeight()))) {
                    b = level.findBlock(r.getX(), r.getY());
                    if (b.getStrength() == 1) {
                        r.setFill(Color.DARKRED);
                    } else if (b.getStrength() == 2) {
                        r.setFill(Color.DARKBLUE);
                    } else {
                        r.setFill(Color.DARKGREEN);
                    }
                    blockSelected++;
                }
            }

            selectRect.setMinHeight(0);
            selectRect.setMinHeight(0);
            selectRect.setMinWidth(0);
            selectRect.setMaxWidth(0);

            placePane.getChildren().remove(selectRect);
        });
    }


    private void loadBlocks() {
        try {
            //level = Level.loadLevel("UHR");
            Block block;
            for (int i = 0; i < level.getBlocks().size(); i++) {
                block = level.getBlocks().get(i);
                placeBlock(block);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reloadScreen() {
        for (Rectangle r : getRectangles()) {
            if (r.getX() < 1000) {
                mainPane.getChildren().remove(r);
            }
        }

        loadBlocks();
    }

    private ArrayList<Rectangle> getRectangles() {
        ArrayList<Rectangle> rects = new ArrayList<>();
        for (int i = 0; i < mainPane.getChildren().size(); i++) {
            Node n = mainPane.getChildren().get(i);
            if (n.getClass().getSimpleName().equals("Rectangle")) {
                rects.add((Rectangle) n);
            }
        }
        return rects;
    }

    private void placeBlock(Block block) {
        Rectangle rect;

        if (block.getStrength() == 1) {
            rect = new Rectangle(block.getX(), block.getY(), 100, 30);
            if (block.getX() == 1090) {
                rect.setFill(Color.DARKRED);
            } else {
                rect.setFill(Color.RED);
            }
        } else if (block.getStrength() == 2) {
            rect = new Rectangle(block.getX(), block.getY(), 100, 30);
            if (block.getX() == 1090) {
                rect.setFill(Color.DARKBLUE);
            } else {
                rect.setFill(Color.BLUE);
            }
        } else {
            rect = new Rectangle(block.getX(), block.getY(), 100, 30);
            if (block.getX() == 1090) {
                rect.setFill(Color.DARKGREEN);
            } else {
                rect.setFill(Color.GREEN);
            }
        }

        for (Rectangle r : getRectangles()) {
            if (r.getX() == 1090 && r.getY() != rect.getY()) {
                mainPane.getChildren().remove(r);
            }
        }

        EventHandler<MouseEvent> handler = e -> {
            if (rect.getX() + 50 > 1000) {
                mainPane.getChildren().remove(rect);

                level.removeBlock(block);
            }
            mainPane.setCursor(Cursor.DEFAULT);
        };

        rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e -> {
            if (blockSelected == 1) {
                rect.setX(e.getSceneX() - 50);
                rect.setY(e.getSceneY() - 15);

                block.setX(e.getSceneX() - 50);
                block.setY(e.getSceneY() - 15);

               /* if ((e.getSceneX() + 50 > 1000 || e.getSceneX() - 50 < 0) ||
                        e.getSceneY() - 15 < 0 || e.getSceneY() + 15 > 700 ||
                        !level.replaceBlock(block)) {
                    */if (block.getStrength() == 1) {
                        rect.setFill(Color.DARKRED);
                    } else if (block.getStrength() == 2) {
                        rect.setFill(Color.DARKBLUE);
                    } else {
                        rect.setFill(Color.DARKGREEN);
                    }
               /* } else {
                    if (block.getStrength() == 1) {
                        rect.setFill(Color.RED);
                    } else if (block.getStrength() == 2) {
                        rect.setFill(Color.BLUE);
                    } else {
                        rect.setFill(Color.GREEN);
                    }
                }*/
            } else if (blockSelected > 1) {
                Block b;
                for (Rectangle r : getRectangles()) {
                    if (r.getFill() == Color.DARKGREEN || r.getFill() == Color.DARKRED
                            || r.getFill() == Color.DARKBLUE) {
                        b = level.findBlock(r.getX(), r.getY());

                        b.setX(r.getX() - (x - e.getSceneX()));
                        b.setY(r.getY() - (y - e.getSceneY()));

                        r.setX(r.getX() - (x - e.getSceneX()));
                        r.setY(r.getY() - (y - e.getSceneY()));
                    }
                }
                x = e.getSceneX();
                y = e.getSceneY();
            }
        }));

        mainPane.getChildren().add(rect);

        rect.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, handler);

        rect.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            if (blockSelected == 1) {
                if ((e.getSceneX() + 50 > 1000 || e.getSceneX() - 50 < 0) ||
                        e.getSceneY() - 15 < 0 || e.getSceneY() + 15 > 700 ||
                        !level.replaceBlock(block)) {
                    mainPane.getChildren().remove(rect);
                    level.removeBlock(block);
                }
                blockSelected--;
                reloadScreen();
            } else if (blockSelected > 1) {
                Block b;
                for (Rectangle r : getRectangles()) {
                    if ((r.getFill() == Color.DARKGREEN || r.getFill() ==
                            Color.DARKRED || r.getFill() == Color.DARKBLUE)
                            && ((r.getX() > 1000 - 100 || r.getX() < 0) || (r.getY()
                            < 0 || r.getY() > 700 - 30) || !level.replaceBlock(block))) {
                        b = level.findBlock(r.getX(), r.getY());
                        mainPane.getChildren().remove(rect);
                        level.removeBlock(b);
                        blockSelected--;
                    }
                }
            }

            mainPane.setCursor(Cursor.MOVE);
        });


        rect.addEventHandler(MouseEvent.MOUSE_PRESSED, (e -> {
            if (blockSelected == 0) {
                rect.removeEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                        handler);
                rect.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, ev ->
                        mainPane.setCursor(Cursor.DEFAULT));
                blockSelected++;
            } else if (blockSelected > 1 && (rect.getFill() == Color.DARKGREEN || rect.getFill() ==
                    Color.DARKRED || rect.getFill() == Color.DARKBLUE)) {
                x = e.getSceneX();
                y = e.getSceneY();
            } else {
                reloadScreen();
                rect.removeEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                        handler);
                rect.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, ev ->
                        mainPane.setCursor(Cursor.DEFAULT));

                blockSelected = 1;
            }

            mainPane.setCursor(Cursor.MOVE);
        }));

        rect.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, (e ->
                mainPane.setCursor(Cursor.MOVE)));

        if (!level.getBlocks().contains(block)) {
            level.addBlock(block);
        }
    }
}
