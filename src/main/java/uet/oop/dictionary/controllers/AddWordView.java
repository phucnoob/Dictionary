package uet.oop.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import uet.oop.dictionary.data.Word;
import uet.oop.dictionary.services.DictionaryService;
import uet.oop.dictionary.ui.Toast;
import uet.oop.dictionary.ui.ToastUI;
import uet.oop.dictionary.ui.WordForm;
import uet.oop.dictionary.utils.QuickToast;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddWordView extends VBox implements Initializable {

    private final DictionaryService dictionary;

    @FXML
    public VBox root;

    @FXML
    public WordForm wordForm;

    @FXML
    public Button clearBtn;

    @FXML
    public Button addBtn;

    public AddWordView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-word-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        dictionary = DictionaryService.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearBtn.setOnAction(e -> wordForm.clear());
        addBtn.setOnAction(e -> addWordHandler());
    }

    public void addWordHandler() {
        Word word = wordForm.getData();
        boolean added = dictionary.add(word);
        if (added) {
            QuickToast.makeText(root, String.format("Add %s successfully.", word.getTarget()));
        } else {
            QuickToast.makeText(root, "Add word failed.\nSee the log for details.");
        }
    }
}
