package com.example.oeqaas.utils;

import com.example.oeqaas.OEQAASApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SahneYoneticisi {

    // Static method: Can be called from anywhere without 'new SahneYoneticisi()'
    public static void sahneDegistir(ActionEvent event, String fxmlDosyasi) throws IOException {
        FXMLLoader fxmlYukleyici = new FXMLLoader(OEQAASApplication.class.getResource(fxmlDosyasi));
        Parent yeniKok = fxmlYukleyici.load();

        // setRoot keeps the window Maximized/Fullscreen settings
        Scene mevcutSahne = ((Node)event.getSource()).getScene();
        mevcutSahne.setRoot(yeniKok);
    }
}