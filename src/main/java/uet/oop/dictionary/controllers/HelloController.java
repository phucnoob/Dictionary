package uet.oop.dictionary.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import uet.oop.dictionary.api.Speaker;
import uet.oop.dictionary.ui.SearchBar;
import uet.oop.dictionary.ui.WordView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public Button haha;
    public VBox box;
    public Button add;
    public Button update;
    public BorderPane root;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) {
        try {
            if (event.getSource() == add) {
                System.out.println("addd");
                root.setCenter(new Home());
            }
            if (event.getSource() == update) {
                System.out.println("update");
                root.setCenter(new SearchBar());
            }
        } catch (Exception e) {
            System.err.println("Bla");
            System.err.println(e.getMessage());
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(location);
        System.out.println(resources);

        add.setOnAction(this::onHelloButtonClick);
        update.setOnAction(this::onHelloButtonClick);
    }
}