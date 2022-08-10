package uet.oop.dictionary.ui;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import org.kordamp.ikonli.javafx.FontIcon;

public class CustomPopup extends MenuItem {

    private final Label innerLabel;

    public CustomPopup(String text) {
        super();
        innerLabel = new Label(text);
        innerLabel.setId("my-menu");
        innerLabel.setStyle("-fx-font-size: 18px;");
        setCss();
        setGraphic(innerLabel);
    }

    public CustomPopup(String text, FontIcon icon) {
        super();
        innerLabel = new Label(text);
        setIcon(icon);
        setGraphic(innerLabel);
    }

    public void setLabelText(String value) {
        innerLabel.setText(value);
    }

    public String getLabelText() {
        return innerLabel.getText();
    }

    public void setIcon(FontIcon icon) {
        innerLabel.setGraphic(icon);
    }

    public FontIcon getIcon() {
        return (FontIcon) innerLabel.getGraphic();
    }

    public void setWidth(double value) {
        innerLabel.setPrefWidth(value);
    }

    public void setGraphicTextGap(double value) {
        innerLabel.setGraphicTextGap(value);
    }

    // set some desire css by code.
    // have no idea why css not work on menu item.
    public void setCss() {
        this.setStyle("-fx-padding: 10 0 0 10;");
        this.setStyle("-fx-font-size: 18px;");
        this.getStyleClass().add("menu-item");

        innerLabel.focusedProperty().addListener((observable -> {
            innerLabel.setStyle("-fx-text-fill: #fff;");
            innerLabel.setStyle("-fx-background-color: #33658A");
            System.out.println("focus");
        }));
    }
}
