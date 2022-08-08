package uet.oop.dictionary.controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;
import uet.oop.dictionary.data.Definition;
import uet.oop.dictionary.data.Word;
import uet.oop.dictionary.services.DictionaryService;
import uet.oop.dictionary.ui.CustomPopup;
import uet.oop.dictionary.ui.SearchBar;
import uet.oop.dictionary.ui.WordView;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Home implements Initializable {
    public SearchBar search;
    public Label label;
    public ScrollPane content;
    private DictionaryService dictionary;

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

    public void handleAcceptSuggestion(ActionEvent event) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dictionary = DictionaryService.DEFAULT;

        PauseTransition pause = new PauseTransition(Duration.millis(300));
        search.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    pause.setOnFinished(e -> autoComplete(newValue));
                    pause.playFromStart();
                });
        search.setOnAcceptSuggestion(this::handleAcceptSuggestion);
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
                dictionary.remove(wordView.getWord().getTarget());
                content.setContent(null);
            }
        }
    }

    private void updateWord() {
        System.out.println("update word");
    }
}
