package uet.oop.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.dictionary.controllers.HamburgerMenu;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        URL view = HamburgerMenu.class.getResource("menu-view.fxml");
        URL css = DictionaryApplication.class.getResource("css/main.css");
        FXMLLoader fxmlLoader = new FXMLLoader(view);

        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        scene.setUserData(fxmlLoader.getController());
        scene.getStylesheets().add(Objects.requireNonNull(css).toExternalForm());

        stage.setTitle("Dictionary App!");
        stage.setScene(scene);
        stage.setWidth(600);
        stage.setMaxHeight(800);
        stage.setMinWidth(700);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}