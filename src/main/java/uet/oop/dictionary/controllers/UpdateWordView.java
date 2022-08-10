package uet.oop.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import uet.oop.dictionary.data.Word;
import uet.oop.dictionary.services.DictionaryService;
import uet.oop.dictionary.ui.WordForm;
import uet.oop.dictionary.utils.QuickToast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateWordView extends VBox implements Initializable {

    private final DictionaryService dictionary;

    @FXML
    public VBox root;

    @FXML
    public WordForm wordForm;

    @FXML
    public Button clearBtn;

    @FXML
    public Button updateBtn;

    public UpdateWordView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("update-word-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        dictionary = DictionaryService.DEFAULT;
    }

    public void setWord(Word word) {
        wordForm.setData(word);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearBtn.setOnAction(e -> wordForm.clear());
        updateBtn.setOnAction(e -> updateWordHandler());
    }

    private void updateWordHandler() {
        Word word = wordForm.getData();
        if (word.getID() == Word.ID_UNSET) {
            return;
        }

        boolean updated = dictionary.update(word.getID(), word);;
        if (updated) {
            QuickToast.makeText(root, String.format("Update %s successfully.", word.getTarget()));
        } else {
            QuickToast.makeText(root, "Update word failed.\nSee the log for details.");
        }

    }
}
