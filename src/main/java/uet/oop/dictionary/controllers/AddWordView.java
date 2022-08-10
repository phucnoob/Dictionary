package uet.oop.dictionary.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AddWordView extends VBox {
    public AddWordView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-word-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
