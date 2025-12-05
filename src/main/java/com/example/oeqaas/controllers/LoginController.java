package com.example.oeqaas.controllers;

import com.example.oeqaas.models.User;
import com.example.oeqaas.utils.DataStore;
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
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML public TextField AdSoyadAlani;
    @FXML public PasswordField SifreAlani;
    @FXML private Label DurumEtiketi;

    @FXML
    protected void GirisYapButonu(ActionEvent event) {
        String girilenAd = AdSoyadAlani.getText().trim();
        String girilenSifre = SifreAlani.getText().trim();

        if (girilenAd.isEmpty() || girilenSifre.isEmpty()) {
            DurumEtiketi.setText("Lütfen alanları doldurun.");
            return;
        }

        String sql = "SELECT * FROM Kullanicilar WHERE AdSoyad = ? AND Sifre = ?";

        try (Connection baglanti = VeritabaniBaglantisi.baglan();
             PreparedStatement sorgu = baglanti.prepareStatement(sql)) {

            sorgu.setString(1, girilenAd);
            sorgu.setString(2, girilenSifre);

            ResultSet sonuc = sorgu.executeQuery();

            if (sonuc.next()) {
                // Kullanıcı Bulundu, Nesne Oluştur
                User girisYapan = new User(
                        sonuc.getString("AdSoyad"),
                        sonuc.getString("Email"),
                        sonuc.getString("Sifre"),
                        sonuc.getString("Telefon")
                );
                girisYapan.setId(sonuc.getInt("KullaniciID")); // ID çok önemli!

                DataStore.aktifKullanici = girisYapan;
                System.out.println("Giriş Başarılı: " + girisYapan.getAdSoyad());

                String rol = sonuc.getString("Rol");
                if ("ADMIN".equalsIgnoreCase(rol) || girilenAd.equalsIgnoreCase("Admin")) {
                    SceneManager.sahneDegistir(event, "admin_test-view.fxml");
                } else {
                    SceneManager.sahneDegistir(event, "user_test_selection-view.fxml");
                }
            } else {
                DurumEtiketi.setText("Hatalı Şifre veya Kullanıcı!");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            DurumEtiketi.setText("Bağlantı Hatası: " + e.getMessage());
        }
    }

    @FXML
    protected void KayitOlButonu(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "register-view.fxml");
    }

    @FXML
    protected void SifremiUnuttumButonu(ActionEvent event) {
        DurumEtiketi.setText("Yakında...");
    }
}