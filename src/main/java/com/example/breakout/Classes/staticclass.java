package com.example.breakout.Classes;

import java.io.File;
import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class staticclass {
    public static String theme = "basic";

    private static MediaPlayer mediaPlayer = null;
    

    public static void playsong(String songname) {
        Media hit = new Media(new File(String.valueOf(Paths.get(System.getProperty("user.dir"), "src", "resources", theme, songname
        ))).toURI().toString());
         mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
    }
}
