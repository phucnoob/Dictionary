package uet.oop.dictionary.controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import uet.oop.dictionary.data.Word;
import uet.oop.dictionary.services.DictionaryService;
import uet.oop.dictionary.ui.CustomPopup;
import uet.oop.dictionary.ui.SearchBar;
import uet.oop.dictionary.ui.WordView;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class WordSearchView extends VBox implements Initializable {
    public SearchBar search;
    public Label label;
    public ScrollPane content;
    private DictionaryService dictionary;

    public WordSearchView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
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
            System.out.println("Word not found..");
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
            System.out.println("Word not found..");
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

    private void updateWord() {
        System.out.println("translate word");
    }
}
