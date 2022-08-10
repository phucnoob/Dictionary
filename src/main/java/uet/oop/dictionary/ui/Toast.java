package uet.oop.dictionary.ui;

import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public final class Toast {

    private Window owner;
    private String message;
    private double fadeInDelay;
    private double fadeOutDelay;
    private double delay;

    private double x;
    private double y;

    private Pane content;

    public Toast() {
    }

    public Window getOwner() {
        return owner;
    }

    public void setOwner(Window owner) {
        this.owner = owner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getFadeInDelay() {
        return fadeInDelay;
    }

    public void setFadeInDelay(double fadeInDelay) {
        this.fadeInDelay = fadeInDelay;
    }

    public double getFadeOutDelay() {
        return fadeOutDelay;
    }

    public void setFadeOutDelay(double fadeOutDelay) {
        this.fadeOutDelay = fadeOutDelay;
    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    private boolean defaultPosition() {
        return  x == 0 && y == 0;
    }

    public void show() {
        Stage toastWin = new Stage();
        toastWin.initStyle(StageStyle.TRANSPARENT);
        toastWin.initOwner(owner);
        toastWin.setResizable(false);

        Scene scene = new Scene(content);
        scene.setFill(Color.TRANSPARENT);

        toastWin.setScene(scene);
        if (!defaultPosition()) {
            toastWin.setX(owner.getX() + x);
            toastWin.setY(owner.getY() + y);
        }
        toastWin.show();

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(delay));


        TranslateTransition translate = new TranslateTransition(Duration.seconds(fadeInDelay), content);
        translate.setFromY(content.getLayoutY() + content.getHeight());
        translate.setToY(content.getLayoutY());
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(fadeInDelay), content);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        ParallelTransition fadeInAndSlide = new ParallelTransition(fadeIn, translate);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(fadeOutDelay), content);
        fadeOut.setToValue(0);
        fadeOut.setFromValue(1);

        SequentialTransition transition = new SequentialTransition(fadeInAndSlide, pauseTransition, fadeOut);
        transition.setOnFinished(e -> toastWin.close());

        transition.play();
    }


    public static class ToastBuilder {
        private Window owner;
        private String message;
        private double fadeInDelay;
        private double fadeOutDelay;
        private double delay;

        private double x;
        private double y;

        private ToastUI content;

        public ToastBuilder(Window owner) {
            this.owner = owner;
            this.message = "";
            fadeInDelay = 0.2;
            fadeOutDelay = 0.5;
            delay = 1.5;
            content = new ToastUI();
        }

        public ToastBuilder withFadeIn(double delay) {
            this.fadeInDelay = delay;
            return this;
        }

        public ToastBuilder withMessage(String message) {
            this.message = message;

            return this;
        }

        public ToastBuilder withPos(double x, double y) {
            this.x = x;
            this.y = y;

            return this;
        }

        public ToastBuilder withFadeOut(double delay) {
            this.fadeOutDelay = delay;
            return this;
        }

        public ToastBuilder withDelay(double delay) {
            this.delay = delay;
            return this;
        }

        public ToastBuilder withContent(ToastUI content) {
            this.content = content;
            return this;
        }

        public Toast build() {
            Toast toast = new Toast();
            toast.setDelay(delay);
            toast.setMessage(message);
            toast.setOwner(owner);
            toast.setFadeInDelay(fadeInDelay);
            toast.setFadeOutDelay(fadeOutDelay);
            toast.content = content;
            toast.x = x;
            toast.y = y;
            content.setMessage(message);

            return toast;
        }
    }
}
