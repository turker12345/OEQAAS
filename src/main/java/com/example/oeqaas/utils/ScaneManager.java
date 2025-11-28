package com.example.oeqaas.utils;

import com.example.oeqaas.OEQAASApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class ScaneManager {

    public static void sahneDegistir(ActionEvent event, String fxmlDosyasi) throws IOException {
        FXMLLoader fxmlYukleyici = new FXMLLoader(OEQAASApplication.class.getResource(fxmlDosyasi));
        Parent yeniKok = fxmlYukleyici.load();
        Scene mevcutSahne = ((Node)event.getSource()).getScene();
        mevcutSahne.setRoot(yeniKok);
    }
}