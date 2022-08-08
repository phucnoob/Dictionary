package uet.oop.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.dictionary.api.Speaker;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        Media media = new Media("file:///home/ppvan/Downloads/music.mp3");
        MediaPlayer player = new MediaPlayer(media);

        player.play();
    }
}