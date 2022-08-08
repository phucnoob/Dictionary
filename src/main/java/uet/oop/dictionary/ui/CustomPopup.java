package uet.oop.dictionary.ui;

import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import org.kordamp.ikonli.javafx.FontIcon;

public class CustomPopup extends MenuItem {

    private final Label innerLabel;

    public CustomPopup(String text) {
        super();
        innerLabel = new Label(text);
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

    public void setGraphicTextGap(double value) {
        innerLabel.setGraphicTextGap(value);
    }

}
