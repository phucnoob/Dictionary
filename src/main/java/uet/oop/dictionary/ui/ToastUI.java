package uet.oop.dictionary.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ToastUI extends VBox {

    @FXML
    public Label message;

    public ToastUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("toast-ui.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

}
