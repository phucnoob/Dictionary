package uet.oop.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.dictionary.controllers.GoogleTranslate;
import uet.oop.dictionary.controllers.HamburgerMenu;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        URL view = HamburgerMenu.class.getResource("menu-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(view);

        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Hello!");
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