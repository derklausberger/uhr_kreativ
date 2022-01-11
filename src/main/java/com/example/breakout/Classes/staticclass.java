package com.example.breakout.Classes;

import java.io.File;
import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class staticclass {
    public static String theme = "basic";

    private static MediaPlayer mediaPlayer = null;

    private static boolean soundsetting = true;
    private static double mediaplayervolume = 0.1;


    public static void playsong(String songname) {
        Media hit = new Media(new File(String.valueOf(Paths.get(System.getProperty("user.dir"), "src", "resources", theme, songname
        ))).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(mediaplayervolume);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
    }

    public static void playsound(String soundname) {
        if (soundsetting) {
            File path = new File(String.valueOf(Paths.get(System.getProperty("user.dir"), "src", "resources", theme, soundname)));
            System.out.println(path);
            try {
                AudioInputStream audioinput2 = AudioSystem.getAudioInputStream(path);
                Clip clip = AudioSystem.getClip();
                clip.open(audioinput2);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
