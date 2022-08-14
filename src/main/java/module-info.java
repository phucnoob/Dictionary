module uet.oop.dictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.bootstrapicons;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.apache.httpcomponents.client5.httpclient5.fluent;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    exports uet.oop.dictionary;
    exports uet.oop.dictionary.ui;
    exports uet.oop.dictionary.controllers;
    exports uet.oop.dictionary.data;
    exports uet.oop.dictionary.api;

    opens uet.oop.dictionary.api to com.fasterxml.jackson.databind;
    opens uet.oop.dictionary.controllers to javafx.fxml;
    opens uet.oop.dictionary.ui to javafx.fxml;
    opens uet.oop.dictionary to javafx.fxml;
}