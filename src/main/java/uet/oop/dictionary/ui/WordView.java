package uet.oop.dictionary.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import uet.oop.dictionary.api.LabanSpeaker;
import uet.oop.dictionary.api.Speaker;
import uet.oop.dictionary.data.Definition;
import uet.oop.dictionary.data.Word;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WordView extends VBox implements Initializable {

    private Word currentWord;
    private Speaker speaker;
    private Speaker.SpeakerData speakerData;

    @FXML
    public VBox definitions;
    @FXML
    public Button playBtn;

    @FXML
    public Button updateBtn;

    @FXML
    public Button deleteBtn;

    @FXML
    public Label target;

    @FXML
    public Label phonetics;

    public WordView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("word-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        speaker = new LabanSpeaker();
        speakerData = Speaker.SpeakerData.EMPTY;
    }

    public void speakWord() {
        new Thread(new PlaySound()).start();
    }

    public void setWord(Word word) {
        currentWord = word;
        new Thread(new FetchData(word.getTarget())).start();

        // Update UI
        target.setText(word.getTarget());
        phonetics.setText(word.getPhonetics());

        Map<String, List<Definition>> defsByType = word.getDefinitions().stream()
                .collect(Collectors.groupingBy(Definition::getWordType));

        definitions.getChildren().clear();
        for (Map.Entry<String, List<Definition>> entry: defsByType.entrySet()) {

            DefinitionView view = new DefinitionView();
            view.loadDefinitionGroup(entry.getKey(), entry.getValue());

            definitions.getChildren().add(view);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playBtn.setOnAction(e -> speakWord());
    }

    public Word getWord() {
        return currentWord;
    }


    private class PlaySound implements Runnable {
        @Override
        public void run() {
            if (speakerData == Speaker.SpeakerData.EMPTY) {
                return;
            }

            AudioClip clip = new AudioClip(speakerData.getData());
            clip.play();
        }
    }

    private class FetchData implements Runnable {
        private String target;

        public FetchData(String target) {
            this.target = target;
        }

        @Override
        public void run() {
            speakerData = speaker.speak(target);
        }
    }
}
