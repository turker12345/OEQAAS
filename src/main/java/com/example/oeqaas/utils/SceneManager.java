package com.example.oeqaas.utils;

import com.example.oeqaas.EQAASApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneManager {

    // Helper method to switch scenes
    public static void sahneDegistir(ActionEvent event, String fxmlDosyasi) throws IOException {
        FXMLLoader fxmlYukleyici = new FXMLLoader(EQAASApplication.class.getResource(fxmlDosyasi));
        Parent yeniKok = fxmlYukleyici.load();

        if (event != null && event.getSource() instanceof Node) {
            Scene mevcutSahne = ((Node)event.getSource()).getScene();
            mevcutSahne.setRoot(yeniKok);
        }
    }
}