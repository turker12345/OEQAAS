module com.example.oeqaas {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.oeqaas to javafx.fxml;
    exports com.example.oeqaas;
    exports com.example.oeqaas.Controllers;
    opens com.example.oeqaas.Controllers to javafx.fxml;
}