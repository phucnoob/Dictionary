package uet.oop.dictionary.controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import uet.oop.dictionary.utils.Config;

import java.net.URL;
import java.util.*;

public class HamburgerMenu implements Initializable {
    public VBox box;
    public Button toggle;
    public Button add;
    public Button update;
    public Button search;
    public Button translate;
    public BorderPane root;

    private static boolean expanded = false;
    Map<Button ,Pane> allView;

    @FXML
    protected void changeView(ActionEvent event) {

        Button clicked = (Button) event.getSource();
        Pane current = (Pane) root.getCenter();
        Pane next = allView.get(clicked);

        if (next == null || current == next) {
            return;
        }

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), current);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), next);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1.0);

        fadeOut.setOnFinished(e -> {
            next.setOpacity(0);
            root.setCenter(next);
            fadeIn.play();
            next.requestFocus();
        });

        fadeOut.play();
    }

    public void changeSize(final VBox pane, double width) {

        Duration cycleDuration = Duration.millis(400);
        Timeline timeline = new Timeline(
                new KeyFrame(cycleDuration,
                        new KeyValue(pane.maxWidthProperty(),width, Interpolator.EASE_BOTH))
        );
        timeline.setCycleCount(1);
        timeline.setAutoReverse(true);
        timeline.play();
        timeline.setOnFinished(event->{
            /* From SO with love <3 */
            /* insert code here if you need */
        });
    }

    public void onToggleSideBar(ActionEvent event) {
        if (event.getSource() == toggle) {
            if (expanded) {
                changeSize(box, Config.SIDEBAR_MIN);
            } else {
                changeSize(box, Config.SIDEBAR_MAX);
            }
            expanded = !expanded;
        }
    }

    private void initButtonsOnAction() {
        add.setOnAction(this::changeView);
        translate.setOnAction(this::changeView);
        search.setOnAction(this::changeView);
        toggle.setOnAction(this::onToggleSideBar);
        update.setOnAction(this::changeView);
    }

    public void initViewPane() {
        allView = new HashMap<>();
        allView.put(search, new SearchWordView());
        allView.put(translate, new GoogleTranslateView());
        allView.put(add, new AddWordView());
        allView.put(update, new UpdateWordView());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update = new Button();
        initViewPane();
        initButtonsOnAction();

//        root.setCenter(allView.get(update));
    }
}