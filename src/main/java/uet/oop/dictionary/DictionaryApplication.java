package uet.oop.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.dictionary.controllers.HamburgerMenu;

import java.awt.im.InputContext;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        URL view = HamburgerMenu.class.getResource("menu-view.fxml");
        URL css = DictionaryApplication.class.getResource("css/main.css");
        FXMLLoader fxmlLoader = new FXMLLoader(view);

        // For some reason, i have to add this to make textfield work with Linux telex input method.
        // E.g: able to type Vietnamese.
        // Detailed at: https://bugs.openjdk.org/browse/JDK-8284530
        InputContext.getInstance();

        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        scene.setUserData(fxmlLoader.getController());
        scene.getStylesheets().add(Objects.requireNonNull(css).toExternalForm());

        stage.setTitle("Dictionary App!");
        stage.setScene(scene);
        stage.setWidth(700);
        stage.setMaxHeight(800);
        stage.setMinWidth(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}