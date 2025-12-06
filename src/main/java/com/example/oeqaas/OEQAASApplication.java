package com.example.oeqaas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class OEQAASApplication extends Application {
    @Override
    public void start(Stage anaSahne) throws IOException { // "stage" changed to "anaSahne" (Main Stage)
        FXMLLoader fxmlYukleyici = new FXMLLoader(OEQAASApplication.class.getResource("user_test_selection-view.fxml"));
        Scene sahne = new Scene(fxmlYukleyici.load(), 800, 600); // "scene" changed to "sahne"
        anaSahne.setTitle("Quiz ve Analiz Sistemi");
        // Icon setup
        Image ikon = new Image(getClass().getResourceAsStream("images/icon1.png")); // "icon" -> "ikon"
        anaSahne.getIcons().add(ikon);
        anaSahne.setScene(sahne);
        anaSahne.setMaximized(true);
        anaSahne.show();
    }
    public static void main(String[] args) {
        launch();
    }
}