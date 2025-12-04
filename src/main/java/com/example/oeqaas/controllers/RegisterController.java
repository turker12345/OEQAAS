package com.example.oeqaas.controllers;

import com.example.oeqaas.models.User;
import com.example.oeqaas.utils.DataStore;
import com.example.oeqaas.utils.SceneManager; // FIXED IMPORT (was ScaneManager)
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {

    @FXML private TextField AdSoyadAlani;
    @FXML private TextField TelefonNoAlani;
    @FXML private PasswordField SifreAlani;
    @FXML private PasswordField SifreTekrarAlani;
    @FXML private Label DurumEtiketi;

    @FXML
    protected void KayitOlButonu(ActionEvent event) {
        String adSoyad = AdSoyadAlani.getText();
        String telefon = TelefonNoAlani.getText();
        String sifre = SifreAlani.getText();
        String sifreTekrar = SifreTekrarAlani.getText();

        if (sifre.equals(sifreTekrar) && !sifre.isEmpty() && !adSoyad.isEmpty()) {

            // Save User
            User yeniKullanici = new User(adSoyad, "", sifre, telefon);
            DataStore.kullanicilar.add(yeniKullanici);
            System.out.println("Kayıt Başarılı: " + adSoyad);

            // Redirect to Login Page
            try {
                // Using SceneManager (correct spelling)
                SceneManager.sahneDegistir(event, "login-view.fxml");
            } catch (IOException e) {
                e.printStackTrace();
                if(DurumEtiketi != null) DurumEtiketi.setText("Sayfa hatası: " + e.getMessage());
            }

        } else {
            if(DurumEtiketi != null) DurumEtiketi.setText("Hatalı bilgi veya şifreler uyuşmuyor!");
        }
    }

    @FXML
    protected void GeriButonu(ActionEvent event) throws IOException {
        // Go back to Login Page
        SceneManager.sahneDegistir(event, "login-view.fxml");
    }
}