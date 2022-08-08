package uet.oop.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uet.oop.dictionary.controllers.HelloController;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        URL view = HelloController.class.getResource("hello-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(view);

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaxHeight(600);
        stage.setMinWidth(300);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}