module com.example.oeqaas {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires flyway.core;

    opens com.example.oeqaas to javafx.fxml;
    exports com.example.oeqaas;
    exports com.example.oeqaas.controllers;
    opens com.example.oeqaas.controllers to javafx.fxml;
    exports com.example.oeqaas.models;
    opens com.example.oeqaas.models to javafx.fxml;
    exports com.example.oeqaas.utils;
    opens com.example.oeqaas.utils to javafx.fxml;
    opens db;
}