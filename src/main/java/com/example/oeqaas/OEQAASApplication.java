package com.example.oeqaas;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OEQAASApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OEQAASApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        stage.setTitle("Quiz ve Analiz Sistemi");
        //Hata Düzeltme 3
        // Pencere simgesini ekleme
        Image icon = new Image(getClass().getResourceAsStream("icon1.png")); // icon.png resources klasöründe olmalı
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
