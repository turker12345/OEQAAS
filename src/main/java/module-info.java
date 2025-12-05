module com.example.oeqaas {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    // --- SQL BAĞLANTISI İÇİN GEREKLİ İZİNLER ---
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    // -------------------------------------------

    opens com.example.oeqaas to javafx.fxml;
    exports com.example.oeqaas;
    exports com.example.oeqaas.controllers;
    opens com.example.oeqaas.controllers to javafx.fxml;

    exports com.example.oeqaas.models;
    // Model klasörünü hem FXML'e hem GSON'a açıyoruz
    opens com.example.oeqaas.models to javafx.fxml, com.google.gson;

    exports com.example.oeqaas.utils;
    opens com.example.oeqaas.utils to javafx.fxml;
}