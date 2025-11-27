package com.example.oeqaas.controllers;

import com.example.oeqaas.OEQAASApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField AdSoyadAlani; // Name Field

    @FXML
    private TextField TelefonNoAlani; // Phone Field

    @FXML
    private PasswordField SifreAlani; // Password Field

    @FXML
    private PasswordField SifreTekrarAlani; // Password Confirm Field

    @FXML
    private Label DurumEtiketi; // Status Label (Old: Quiz)

    @FXML
    protected void KayitOlButonu(ActionEvent event) { // Old: KayitOlButton
        // Mock Registration Logic
        String adSoyad = AdSoyadAlani.getText();
        String sifre = SifreAlani.getText();
        String sifreTekrar = SifreTekrarAlani.getText();

        if (sifre.equals(sifreTekrar) && !sifre.isEmpty()) {
            if(DurumEtiketi != null) DurumEtiketi.setText("Kayıt Başarılı! Giriş yapınız.");
            System.out.println("Kullanıcı Kaydedildi: " + adSoyad);
        } else {
            if(DurumEtiketi != null) DurumEtiketi.setText("Şifreler uyuşmuyor!");
        }
    }

    @FXML
    protected void GeriButonu(ActionEvent event) throws IOException { // Old: GeriButton
        // Go back to Login Screen
        FXMLLoader fxmlYukleyici = new FXMLLoader(OEQAASApplication.class.getResource("login-view.fxml"));
        Scene sahne = new Scene(fxmlYukleyici.load());
        Stage asama = (Stage)((Node)event.getSource()).getScene().getWindow();
        asama.setScene(sahne);
        asama.show();
    }
}