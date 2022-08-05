module uet.oop.dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.apache.httpcomponents.client5.httpclient5.fluent;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    opens uet.oop.dictionary to javafx.fxml;
    exports uet.oop.dictionary;
    exports uet.oop.dictionary.controllers;
    opens uet.oop.dictionary.controllers to javafx.fxml;
}