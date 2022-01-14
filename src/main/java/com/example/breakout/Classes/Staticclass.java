package com.example.breakout.Classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Staticclass {
    public static File settings = new File(String.valueOf(Paths.get(System.getProperty("user.dir"), "src", "resources", "settings.txt")));
    public static String theme = "basic";

    private static MediaPlayer mediaPlayer = null;

    private static boolean soundsetting = true;
    private static boolean musicsetting = true;
    private static double mediaplayervolume = 0.3;


    public static void playsong(String songname) {
        //these lines need to be here and not in the if due to settings being able to turn on or off music with a button there
        Media hit = new Media(new File(String.valueOf(Paths.get(System.getProperty("user.dir"), "src", "resources", theme, songname
        ))).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setVolume(mediaplayervolume);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
        if (musicsetting) {
            mediaPlayer.play();
        }
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

    public static void readlocalsettings() throws IOException {
        if (settings.exists()) {
            Scanner myReader = new Scanner(settings);
            String data = myReader.nextLine();
            musicsetting = Boolean.parseBoolean(data.split("=")[1]);
            data = myReader.nextLine();
            soundsetting = Boolean.parseBoolean(data.split("=")[1]);
            myReader.close();
        } else {
            settings.createNewFile();
            FileWriter myWriter = new FileWriter(settings);
            myWriter.write("Music=true\n");
            myWriter.write("Sound=true");
            myWriter.close();
        }
    }

    public static boolean isSoundsetting() {
        return soundsetting;
    }

    public static boolean isMusicsetting() {
        return musicsetting;
    }

    public static void setSoundsetting(boolean soundsetting) throws IOException {
        Staticclass.soundsetting = soundsetting;
        changeonelineinsettings(1, "Sound=" + soundsetting);
    }

    public static void setMusicsetting(boolean musicsetting) throws IOException {
        if (musicsetting) {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        } else {
            mediaPlayer.stop();
        }
        Staticclass.musicsetting = musicsetting;
        changeonelineinsettings(0, "Music=" + musicsetting);
    }

    public static void changeonelineinsettings(int linenumber, String line) throws IOException {
        List<String> fileContent = new ArrayList<>(Files.readAllLines(settings.toPath()));

        for (int i = 0; i < fileContent.size(); i++) {
            if (i == linenumber) {
                fileContent.set(i, line);
                break;
            }
        }

        Files.write(settings.toPath(), fileContent);
    }
}
