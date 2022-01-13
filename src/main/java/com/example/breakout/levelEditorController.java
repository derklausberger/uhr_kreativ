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
    public static final int rectWidth = 100;
    public static final int rectHeight = 30;
    public static final int mainPaneWidth = 1000;
    public static final int mainPaneHeight = 720;
    public static final int blockX = 1090;

    @FXML
    private Rectangle redRect;
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
    private final Point p = new Point();
    private final Region selectRect = new Region();
    private double x = 0, y = 0;

    public levelEditorController() {
    }

    @FXML
    private void initialize() {
        redRect.setFill(Color.RED);
        blueRect.setFill(Color.BLUE);
        greenRect.setFill(Color.GREEN);

        redRect.setOnMouseEntered(e -> placeBlock(new Block(level.getCount(), blockX, 50, rectWidth, rectHeight, 1)));
        blueRect.setOnMouseEntered(e -> placeBlock(new Block(level.getCount(), blockX, 130, rectWidth, rectHeight, 2)));
        greenRect.setOnMouseEntered(e -> placeBlock(new Block(level.getCount(), blockX, 210, rectWidth, rectHeight, 3)));

        level = new Level();

        loadBlocks();

        saveLevelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e -> {
            Alert alert;
            level.setName(name.getText());
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
            blockSelected = 0;
            reloadScreen();

            selectRect.setMinHeight(0);
            selectRect.setMinHeight(0);
            selectRect.setMinWidth(0);
            selectRect.setMaxWidth(0);
            selectRect.setLayoutX(0);
            selectRect.setLayoutY(0);

            p.setLocation(e.getSceneX(), e.getSceneY());

            placePane.getChildren().add(selectRect);
        });

        placePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (p.getX() > e.getSceneX() && e.getSceneX() <= 0) {
                selectRect.setMinWidth(p.getX());
                selectRect.setMaxWidth(p.getX());
                selectRect.setLayoutX(2);
            } else if (p.getX() > e.getSceneX() && e.getSceneX() > 0 && e.getSceneX() < mainPaneWidth) {
                selectRect.setMinWidth(p.getX() - e.getSceneX());
                selectRect.setMaxWidth(p.getX() - e.getSceneX());
                selectRect.setLayoutX(e.getSceneX());
            } else if (e.getSceneX() >= mainPaneWidth) {
                selectRect.setMinWidth(mainPaneWidth - 6 - p.getX());
                selectRect.setMaxWidth(mainPaneWidth - 6 - p.getX());
                selectRect.setLayoutX(p.getX());
            } else {
                selectRect.setMinWidth(e.getSceneX() - p.getX());
                selectRect.setMaxWidth(e.getSceneX() - p.getX());
                selectRect.setLayoutX(p.getX());
            }

            if (p.getY() > e.getSceneY() && e.getSceneY() <= 0) {
                selectRect.setMinHeight(p.getY());
                selectRect.setMaxHeight(p.getY());
                selectRect.setLayoutY(0);
            } else if (p.getY() > e.getSceneY() && e.getSceneX() > 0) {
                selectRect.setMinHeight(p.getY() - e.getSceneY());
                selectRect.setMaxHeight(p.getY() - e.getSceneY());
                selectRect.setLayoutY(e.getSceneY());
            } else if (e.getSceneY() >= mainPaneHeight - 20) {
                selectRect.setMinHeight(mainPaneHeight - 20 - p.getY());
                selectRect.setMaxHeight(mainPaneHeight - 20 - p.getY());
                selectRect.setLayoutY(p.getY());
            } else {
                selectRect.setMinHeight(e.getSceneY() - p.getY());
                selectRect.setMaxHeight(e.getSceneY() - p.getY());
                selectRect.setLayoutY(p.getY());
            }
        });


        placePane.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            Block b;
            for (Rectangle r : getRectangles()) {
                if (((r.getX() + rectWidth > selectRect.getLayoutX()) && (r.getX()
                        < selectRect.getLayoutX() + selectRect.getWidth()))
                        && ((r.getY() + rectHeight > selectRect.getLayoutY()) &&
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
            Block b;
            for (int i = 0; i < level.getBlocks().size(); i++) {
                b = level.getBlocks().get(i);
                placeBlock(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reloadScreen() {
        Block b;
        for (Rectangle r : getRectangles()) {
            b = level.findBlock(r.getX(), r.getY());
            if (r.getX() < mainPaneWidth) {
                if (b.getStrength() == 1) {
                    r.setFill(Color.RED);
                } else if (b.getStrength() == 2) {
                    r.setFill(Color.BLUE);
                } else {
                    r.setFill(Color.GREEN);
                }

            }
        }
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
            rect = new Rectangle(block.getX(), block.getY(), rectWidth, rectHeight);
            if (block.getX() == blockX) {
                rect.setFill(Color.DARKRED);
            } else {
                rect.setFill(Color.RED);
            }
        } else if (block.getStrength() == 2) {
            rect = new Rectangle(block.getX(), block.getY(), rectWidth, rectHeight);
            if (block.getX() == blockX) {
                rect.setFill(Color.DARKBLUE);
            } else {
                rect.setFill(Color.BLUE);
            }
        } else {
            rect = new Rectangle(block.getX(), block.getY(), rectWidth, rectHeight);

            if (block.getX() == blockX) {
                rect.setFill(Color.DARKGREEN);
            } else {
                rect.setFill(Color.GREEN);
            }
        }

        for (Rectangle r : getRectangles()) {
            if (r.getX() == blockX && r.getY() != rect.getY()) {
                mainPane.getChildren().remove(r);
            }
        }

        EventHandler<MouseEvent> handler = e -> {
            if (rect.getX() + (double) rectWidth / 2 > mainPaneWidth) {
                mainPane.getChildren().remove(rect);
                level.removeBlock(block);
            }
            mainPane.setCursor(Cursor.DEFAULT);
        };

        rect.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e -> {
            if (blockSelected == 1) {
                rect.setX(e.getSceneX() - (double) rectWidth / 2);
                rect.setY(e.getSceneY() - (double) rectHeight / 2);

                block.setX(e.getSceneX() - (double) rectWidth / 2);
                block.setY(e.getSceneY() - (double) rectHeight / 2);

                if (block.getStrength() == 1) {
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
                if ((e.getSceneX() + (double) rectWidth / 2 > mainPaneWidth || e.getSceneX() - (double) rectWidth / 2 < 0) ||
                        (e.getSceneY() - (double) rectHeight / 2 < 0 || e.getSceneY() + (double) rectHeight / 2 > mainPaneHeight) ||
                        !level.replaceBlock(block)) {
                    mainPane.getChildren().remove(rect);
                    level.removeBlock(block);
                }
                reloadScreen();
                blockSelected = 0;
            } else if (blockSelected > 1) {
                Block b;
                for (Rectangle r : getRectangles()) {
                    if (r.getFill() == Color.DARKGREEN || r.getFill() ==
                            Color.DARKRED || r.getFill() == Color.DARKBLUE) {
                        b = level.findBlock(r.getX(), r.getY());
                        if (((r.getX() >= mainPaneWidth - rectWidth || r.getX() <= 0) || (r.getY()
                                <= 0 || r.getY() >= mainPaneHeight - 20 - rectHeight)) || !level.replaceBlock(b)) {
                            mainPane.getChildren().remove(r);
                            level.removeBlock(b);
                            blockSelected--;
                        }
                    }
                }
            }

            mainPane.setCursor(Cursor.MOVE);
        });


        rect.addEventHandler(MouseEvent.MOUSE_PRESSED, (e -> {
            if (blockSelected > 1) {
                for (Rectangle r : getRectangles()) {
                    if (!(r.getX() < mainPaneWidth - (double) rectWidth / 2)) {
                        blockSelected--;
                        if (r.getFill() == Color.DARKGREEN) {
                            r.setFill(Color.GREEN);
                        } else if (r.getFill() == Color.DARKRED) {
                            r.setFill(Color.RED);
                        } else if (r.getFill() == Color.DARKBLUE) {
                            r.setFill(Color.BLUE);
                        }
                    }
                }
                x = e.getSceneX();
                y = e.getSceneY();
            } else if (blockSelected == 1) {
                reloadScreen();
                blockSelected = 1;
                rect.removeEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                        handler);
                rect.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, ev ->
                        mainPane.setCursor(Cursor.DEFAULT));
            } else {
                if (blockSelected != 0) {
                    reloadScreen();
                }
                blockSelected = 1;
                rect.removeEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                        handler);
                rect.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, ev ->
                        mainPane.setCursor(Cursor.DEFAULT));
            }

            mainPane.setCursor(Cursor.MOVE);
        }));

        rect.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, (e ->
                mainPane.setCursor(Cursor.MOVE)));

        if (!level.getBlocks().

                contains(block)) {
            level.addBlock(block);
        }
    }
}
