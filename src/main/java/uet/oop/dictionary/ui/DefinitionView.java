package uet.oop.dictionary.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import uet.oop.dictionary.data.Definition;

import java.io.IOException;
import java.util.List;

public class DefinitionView extends VBox {

    public Label wordType;
    public VBox explains;

    public DefinitionView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("definition-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void loadDefinitionGroup(String type, List<Definition> explains) {
        wordType.setText(type);
        this.explains.getChildren().clear();
        explains.forEach(explain -> this.explains
                    .getChildren()
                    .add(new Label("- " + explain.getExplain()))
        );
    }
}
