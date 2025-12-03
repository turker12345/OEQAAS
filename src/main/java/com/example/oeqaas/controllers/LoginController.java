package com.example.oeqaas.controllers;
import com.example.oeqaas.utils.ScaneManager;
import com.example.oeqaas.utils.DataStore; // Importing the central data store
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
        System.out.println("LOG: GirisYapButonu tıklandı.");

        String girilenAd = AdSoyadAlani.getText();
        String girilenSifre = SifreAlani.getText();

        if (girilenAd == null || girilenSifre == null) {
            System.out.println("LOG: Alanlar null!");
            DurumEtiketi.setText("Lütfen alanları doldurun.");
            return;
        }

        girilenAd = girilenAd.trim();
        girilenSifre = girilenSifre.trim();

        System.out.println("LOG: Aranan Kullanıcı: [" + girilenAd + "] Şifre: [" + girilenSifre + "]");
        // Using DataStore instead of local list
        System.out.println("LOG: Toplam Kayıtlı Kullanıcı Sayısı: " + DataStore.kullanicilar.size());

        boolean kullaniciBulundu = false;

        for(User u : DataStore.kullanicilar) {
            System.out.println("LOG: Kontrol ediliyor -> DB Adı: [" + u.getAdSoyad() + "] DB Şifre: [" + u.getSifre() + "]");

            if(u.getAdSoyad().equalsIgnoreCase(girilenAd) && u.getSifre().equals(girilenSifre)) {
                kullaniciBulundu = true;
                System.out.println("LOG: EŞLEŞME BULUNDU!");
                break;
            }
        }

        if (kullaniciBulundu) {
            try {
                if (girilenAd.equalsIgnoreCase("admin")) {
                    System.out.println("LOG: Admin paneline yönlendiriliyor...");
                    ScaneManager.sahneDegistir(event, "admin-view.fxml");
                } else {
                    System.out.println("LOG: Test paneline yönlendiriliyor...");
                    ScaneManager.sahneDegistir(event, "user_test_selection-view.fxml");
                }
            } catch (IOException e) {
                System.err.println("LOG: SAHNE DEĞİŞTİRME HATASI!");
                e.printStackTrace();
                DurumEtiketi.setText("Sayfa yüklenemedi: " + e.getCause());
            }
        } else {
            System.out.println("LOG: Kullanıcı bulunamadı.");
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