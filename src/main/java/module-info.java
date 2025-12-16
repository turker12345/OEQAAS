module com.example.oeqaas {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    // --- ADDED THESE LINES FOR FLYWAY & SQL ---
    requires java.sql;             // Required for JDBC/SQL;    // Required for Flyway
    requires com.microsoft.sqlserver.jdbc;
    requires flyway.core; // Required if accessing SQL Server classes directly

    opens com.example.oeqaas to javafx.fxml;
    exports com.example.oeqaas;
    exports com.example.oeqaas.controllers;
    opens com.example.oeqaas.controllers to javafx.fxml;
    exports com.example.oeqaas.models;
    opens com.example.oeqaas.models to javafx.fxml;
    exports com.example.oeqaas.utils;
    opens com.example.oeqaas.utils to javafx.fxml;
}