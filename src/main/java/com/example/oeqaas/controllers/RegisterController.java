package com.example.oeqaas.controllers;

import com.example.oeqaas.models.User;
import com.example.oeqaas.utils.SahneYoneticisi; // Import the Utility
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField AdSoyadAlani;

    @FXML
    private TextField TelefonNoAlani;

    @FXML
    private PasswordField SifreAlani;

    @FXML
    private PasswordField SifreTekrarAlani;

    @FXML
    private Label DurumEtiketi;

    @FXML
    protected void KayitOlButonu(ActionEvent event) {
        String adSoyad = AdSoyadAlani.getText();
        String telefon = TelefonNoAlani.getText();
        String sifre = SifreAlani.getText();
        String sifreTekrar = SifreTekrarAlani.getText();

        if (sifre.equals(sifreTekrar) && !sifre.isEmpty() && !adSoyad.isEmpty()) {

            // 1. Create the User Model
            User yeniKullanici = new User(adSoyad, "", sifre, telefon);

            // 2. SAVE TO SHARED LIST IN LOGIN CONTROLLER
            LoginController.kayitliKullanicilar.add(yeniKullanici);

            if(DurumEtiketi != null) DurumEtiketi.setText("Kayıt Başarılı! Giriş yapınız.");
            System.out.println("Kullanıcı Kaydedildi: " + yeniKullanici.getAdSoyad());

        } else {
            if(DurumEtiketi != null) DurumEtiketi.setText("Şifreler uyuşmuyor veya alanlar boş!");
        }
    }

    @FXML
    protected void GeriButonu(ActionEvent event) throws IOException {
        SahneYoneticisi.sahneDegistir(event, "login-view.fxml");
    }
}