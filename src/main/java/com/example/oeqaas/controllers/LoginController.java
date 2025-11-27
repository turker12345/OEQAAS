package com.example.oeqaas.controllers;
import com.example.oeqaas.OEQAASApplication;
import com.example.oeqaas.models.User;
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

public class LoginController {

    @FXML
    public TextField AdSoyadAlani;
    @FXML
    public PasswordField SifreAlani;
    @FXML
    private Label DurumEtiketi;

    @FXML
    protected void GirisYapButonu(ActionEvent event) throws IOException {
        String girilenAd = AdSoyadAlani.getText();
        String girilenSifre = SifreAlani.getText();

        // Use the Model to simulate a database result
        User girisYapanKullanici = sahteVeritabaniSorgusu(girilenAd, girilenSifre);

        if (girisYapanKullanici != null) {
            System.out.println("Giriş Başarılı: " + girisYapanKullanici.getAdSoyad());
            // In the future, you can pass 'girisYapanKullanici' to the next screen here
            sahneDegistir(event, "user_test-view.fxml");
        } else {
            DurumEtiketi.setText("Hatalı Şifre veya Kullanıcı!");
        }
    }

    // Returns a User Model object instead of just boolean
    private User sahteVeritabaniSorgusu(String ad, String sifre) {
        if (sifre.equals("123")) {
            // Return a real User object
            return new User(ad, "ornek@email.com", sifre, "555-0000");
        }
        return null;
    }

    @FXML
    protected void KayitOlButonu(ActionEvent event) throws IOException {
        sahneDegistir(event, "register-view.fxml");
    }

    @FXML
    protected void SifremiUnuttumButonu(ActionEvent event) {
        DurumEtiketi.setText("Özellik yakında eklenecek.");
    }

    private void sahneDegistir(ActionEvent event, String fxmlDosyasi) throws IOException {
        FXMLLoader fxmlYukleyici = new FXMLLoader(OEQAASApplication.class.getResource(fxmlDosyasi));
        Scene sahne = new Scene(fxmlYukleyici.load());
        Stage asama = (Stage)((Node)event.getSource()).getScene().getWindow();
        asama.setScene(sahne);
        asama.setMaximized(true);
        asama.show();
    }
}