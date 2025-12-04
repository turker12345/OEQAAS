package com.example.oeqaas.controllers;

import com.example.oeqaas.utils.ScaneManager;
import com.example.oeqaas.utils.DataStore;
import com.example.oeqaas.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

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

        System.out.println("Giriş Denemesi: " + girilenAd);

        boolean kullaniciBulundu = false;

        // Check DataStore
        for(User u : DataStore.kullanicilar) {
            if(u.getAdSoyad().equalsIgnoreCase(girilenAd) && u.getSifre().equals(girilenSifre)) {
                kullaniciBulundu = true;
                break;
            }
        }

        if (kullaniciBulundu) {
            System.out.println("Giriş Başarılı!");
            try {
                if (girilenAd.equalsIgnoreCase("admin")) {
                    ScaneManager.sahneDegistir(event, "admin_test-view.fxml");
                } else {
                    // FIXED: Go to Selection Screen first, not the Test directly
                    ScaneManager.sahneDegistir(event, "user_test_selection-view.fxml");
                }
            } catch (IOException e) {
                e.printStackTrace();
                DurumEtiketi.setText("Sayfa açılamadı: Dosya bulunamadı!");
                System.err.println("Hata Detayı: " + e.getMessage());
            }
        } else {
            DurumEtiketi.setText("Hatalı Şifre veya Kullanıcı!");
        }
    }

    @FXML
    protected void KayitOlButonu(ActionEvent event) throws IOException {
        ScaneManager.sahneDegistir(event, "register-view.fxml");
    }

    @FXML
    protected void SifremiUnuttumButonu(ActionEvent event) {
        DurumEtiketi.setText("Özellik yakında eklenecek.");
    }
}