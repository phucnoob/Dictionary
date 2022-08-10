package uet.oop.dictionary.ui;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import uet.oop.dictionary.utils.Config;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearchBar extends HBox implements Initializable {

    @FXML
    public TextField textField;
    private EventHandler<ActionEvent> handler;



    public SearchBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        HBox.setHgrow(textField, Priority.ALWAYS);
        textField.getContextMenu()
                .setOnShown(e ->
                        textField.getContextMenu()
                        .getSkin().getNode().requestFocus());
    }

    public void addSuggestions(List<String> suggestions) {
        clearSuggestions();
        suggestions.stream()
                .limit(Config.MAX_SUGGESTIONS)
                .forEach(this::addContextMenu);
        textField.getContextMenu().show(this, Side.BOTTOM, 0, 4);
    }

    public void clearSuggestions() {
        textField.getContextMenu()
                .getItems().clear();
    }

    public void hideSuggestions() {
        textField.getContextMenu().hide();
    }

    public void addContextMenu(String text) {
        CustomPopup popup = new CustomPopup(text);
        double padding = textField.getPadding().getLeft()
                + textField.getPadding().getRight()
                + 12;
        popup.setWidth(textField.getWidth() - padding);
        popup.setOnAction(handler);
        textField.getContextMenu()
                .getItems()
                .add(popup);
    }

    public void setOnAcceptSuggestion(EventHandler<ActionEvent> handler) {
        this.handler = handler;
    }
    public void setText(String value) {
        textProperty().set(value);
    }

    public String getText() {
        return textProperty().get();
    }

    public StringProperty textProperty() {
        return textField.textProperty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(textField.getContextMenu().getStyleClass());
        System.out.println(getStylesheets());
    }
}
