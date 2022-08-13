package uet.oop.dictionary.controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import uet.oop.dictionary.DictionaryApplication;
import uet.oop.dictionary.data.Word;
import uet.oop.dictionary.services.DictionaryService;
import uet.oop.dictionary.ui.CustomPopup;
import uet.oop.dictionary.ui.SearchBar;
import uet.oop.dictionary.ui.WordView;
import uet.oop.dictionary.utils.QuickToast;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class SearchWordView extends VBox implements Initializable {

    public Pane root;
    public SearchBar search;
    public Label label;
    public ScrollPane content;
    private DictionaryService dictionary;

    public SearchWordView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search-word-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.applyCss();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void autoComplete(String newValue) {
        if (newValue == null || newValue.isBlank()) return;

        List<Word> suggestions = dictionary.searchWord(newValue);
        List<String> context = suggestions.stream()
                .map(Word::getTarget)
                .collect(Collectors.toList());

        search.addSuggestions(context);
    }

    public void displayWord(Word word) {
        WordView wordView = new WordView();
        wordView.deleteBtn.setOnAction(e -> deleteWord());
        wordView.updateBtn.setOnAction(e -> updateWord(word));
        wordView.setWord(word);

        content.setContent(wordView);
    }

    private void handleAcceptSuggestion(ActionEvent event) {
        CustomPopup popup = (CustomPopup) event.getSource();
        Optional<Word> word = dictionary.lookup(popup.getLabelText());
        search.clearSuggestions();
        search.setText("");
        search.hideSuggestions();
        if (word.isPresent()) {
            displayWord(word.get());
        } else {
            QuickToast.makeText(root, "Word not found.");
        }
    }

    private void handleEnterDirectly(ActionEvent event) {
        TextField textField = (TextField) event.getSource();

        Optional<Word> word = dictionary.lookup(textField.getText());
        search.clearSuggestions();
        search.setText("");
        search.hideSuggestions();
        if (word.isPresent()) {
            displayWord(word.get());
        } else {
            QuickToast.makeText(root, "Word not found.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dictionary = DictionaryService.DEFAULT;

        PauseTransition pause = new PauseTransition(Duration.millis(100));
        search.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    pause.setOnFinished(e -> autoComplete(newValue));
                    pause.playFromStart();
                });

        this.focusedProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal) {
                search.textField.requestFocus();
            }
        });

        search.setOnAcceptSuggestion(this::handleAcceptSuggestion);
        search.textField.setOnAction(this::handleEnterDirectly);
    }

    private void deleteWord() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to delete a word.");
        alert.setTitle("Delete Word");
        alert.setContentText("This process is not recoverable. Are you sure?");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets()
                .add(DictionaryApplication.class
                        .getResource("css/search-word-view.css").toExternalForm());

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.isPresent()) {
            ButtonType type = answer.get();
            if (type == ButtonType.OK) {
                WordView wordView = (WordView) content.getContent();
                dictionary.delete(wordView.getWord().getID());
                content.setContent(null);
            }
        }
    }

    private void updateWord(Word word) {
        Scene scene = getScene();
        HamburgerMenu menu = (HamburgerMenu) scene.getUserData();
        UpdateWordView updateView = (UpdateWordView) menu.allView.get(menu.update);
        updateView.setWord(word);
        menu.update.fire();
    }
}
