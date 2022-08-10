package uet.oop.dictionary.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import uet.oop.dictionary.data.Definition;
import uet.oop.dictionary.data.Word;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WordForm extends VBox implements Initializable {

    private List<Pair<ComboBox<String>, TextField>> explainInputs;

    @FXML
    public TextField targetInput;

    @FXML
    public TextField phoneticsInput;

    @FXML
    public VBox explainForm;

    public WordForm() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("word-form.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }


    public void clear() {
        targetInput.clear();

        explainForm.getChildren().clear();
        explainInputs.clear();
        explainForm.getChildren().add(createExplainFormGroup());
    }

    private HBox createExplainFormGroup() {
        Label label = new Label("Explain");
        ComboBox<String> wordTypes = new ComboBox<>();
        wordTypes.getItems().addAll("Danh từ", "Động từ", "Tính từ");
        wordTypes.setValue("");
        TextField textInput = new TextField();
        textInput.setOnAction(e -> onActionHandler());
        HBox.setHgrow(textInput, Priority.ALWAYS);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(label, wordTypes, textInput);
        hBox.getStyleClass().add("explain-form-group");
        hBox.setId("explain-group");

        explainInputs.add(new Pair<>(wordTypes, textInput));

        return hBox;
    }

    private void onActionHandler() {
        explainForm.getChildren().add(createExplainFormGroup());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        explainInputs = new ArrayList<>();
        explainForm.getChildren().add(createExplainFormGroup());
    }

    public Word getData() {
        Word wordData = new Word();
        wordData.setTarget(targetInput.getText());
        wordData.setPhonetics(phoneticsInput.getText());

        List<Definition> definitions = new ArrayList<>();
        for (var explainInput: explainInputs) {

            String wordType = explainInput.getKey().getValue();
            String wordExplain = explainInput.getValue().getText();

            var definition = new Definition();
            definition.setWordType(wordType);
            definition.setExplain(wordExplain);

            definitions.add(definition);
        }

        wordData.setDefinitions(definitions);

        return wordData;
    }

}
