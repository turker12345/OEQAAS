package com.example.oeqaas.controllers;

import com.example.oeqaas.utils.SahneYoneticisi;
import com.example.oeqaas.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    public static List<User> kayitliKullanicilar = new ArrayList<>();

    static {
        kayitliKullanicilar.add(new User("admin", "admin@email.com", "123", "555-5555"));
    }

    @FXML
    public TextField AdSoyadAlani;
    @FXML
    public PasswordField SifreAlani;

    @FXML
    private Label DurumEtiketi; // MAKE SURE THIS LINE EXISTS! (Replaces 'Quiz')

    @FXML
    protected void GirisYapButonu(ActionEvent event) throws IOException {
        String girilenAd = AdSoyadAlani.getText();
        String girilenSifre = SifreAlani.getText();

        boolean kullaniciBulundu = false;

        for(User u : kayitliKullanicilar) {
            if(u.getAdSoyad().equalsIgnoreCase(girilenAd) && u.getSifre().equals(girilenSifre)) {
                kullaniciBulundu = true;
                break;
            }
        }

        if (kullaniciBulundu) {
            System.out.println("Giriş Başarılı: " + girilenAd);
            SahneYoneticisi.sahneDegistir(event, "user_test-view.fxml");
        } else {
            DurumEtiketi.setText("Hatalı Şifre veya Kullanıcı!");
        }
    }

    @FXML
    protected void KayitOlButonu(ActionEvent event) throws IOException {
        SahneYoneticisi.sahneDegistir(event, "register-view.fxml");
    }

    @FXML
    protected void SifremiUnuttumButonu(ActionEvent event) {
        DurumEtiketi.setText("Özellik yakında eklenecek.");
    }
}