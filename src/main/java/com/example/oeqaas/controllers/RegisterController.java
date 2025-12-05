package com.example.oeqaas.controllers;

import com.example.oeqaas.utils.SceneManager;
import com.example.oeqaas.utils.VeritabaniBaglantisi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

            String sql = "INSERT INTO Kullanicilar (AdSoyad, Telefon, Sifre, Rol) VALUES (?, ?, ?, 'OGRENCI')";

            try (Connection baglanti = VeritabaniBaglantisi.baglan();
                 PreparedStatement sorgu = baglanti.prepareStatement(sql)) {

                sorgu.setString(1, adSoyad);
                sorgu.setString(2, telefon);
                sorgu.setString(3, sifre);

                int etkilenenSatir = sorgu.executeUpdate();

                if (etkilenenSatir > 0) {
                    System.out.println("SQL Kayıt Başarılı: " + adSoyad);
                    SceneManager.sahneDegistir(event, "login-view.fxml");
                }

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                DurumEtiketi.setText("Veritabanı Hatası: " + e.getMessage());
            }

        } else {
            if(DurumEtiketi != null) DurumEtiketi.setText("Bilgiler eksik veya uyuşmuyor!");
        }
    }

    @FXML
    protected void GeriButonu(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "login-view.fxml");
    }
}