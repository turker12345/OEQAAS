package com.example.oeqaas.controllers;

import com.example.oeqaas.models.User;
import com.example.oeqaas.utils.DataStore; // Import the DataStore
import com.example.oeqaas.utils.SceneManager;
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

        // 1. Validate inputs (Boş mu? Şifreler aynı mı?)
        if (sifre.equals(sifreTekrar) && !sifre.isEmpty() && !adSoyad.isEmpty()) {

            // 2. Create the User Model
            User yeniKullanici = new User(adSoyad, "", sifre, telefon);

            // 3. Save to the CENTRAL DATASTORE (This line was the problem)
            // Instead of LoginController.kayitliKullanicilar.add(...)
            DataStore.kullanicilar.add(yeniKullanici);

            System.out.println("Kullanıcı Kaydedildi: " + yeniKullanici.getAdSoyad());

            // 4. AUTO-REDIRECT: Send user back to Login Page immediately
            try {
                // This line opens the Login page using your SceneManager
                SceneManager.sahneDegistir(event, "login-view.fxml");
            } catch (IOException e) {
                e.printStackTrace();
                if(DurumEtiketi != null) DurumEtiketi.setText("Sayfa yönlendirme hatası!");
            }

        } else {
            if(DurumEtiketi != null) DurumEtiketi.setText("Şifreler uyuşmuyor veya alanlar boş!");
        }
    }

    @FXML
    protected void GeriButonu(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "login-view.fxml");
    }
}