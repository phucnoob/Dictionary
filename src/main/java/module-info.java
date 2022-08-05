module uet.oop.dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens uet.oop.dictionary to javafx.fxml;
    exports uet.oop.dictionary;
    exports uet.oop.dictionary.controllers;
    opens uet.oop.dictionary.controllers to javafx.fxml;
}