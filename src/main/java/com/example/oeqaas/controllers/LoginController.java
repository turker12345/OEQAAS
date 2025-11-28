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

    // KAYITLI KULLANICILAR (Sanal Veritabanı)
    public static List<User> kayitliKullanicilar = new ArrayList<>();

    static {
        // Varsayılan Admin kullanıcısı
        kayitliKullanicilar.add(new User("admin", "admin@email.com", "123", "555-5555"));
    }

    @FXML
    public TextField AdSoyadAlani;
    @FXML
    public PasswordField SifreAlani;

    @FXML
    private Label DurumEtiketi;

    @FXML
    protected void GirisYapButonu(ActionEvent event) throws IOException {
        // .trim() ile kullanıcının yanlışlıkla koyduğu boşlukları siliyoruz
        String girilenAd = AdSoyadAlani.getText().trim();
        String girilenSifre = SifreAlani.getText().trim();

        boolean kullaniciBulundu = false;

        // HATA AYIKLAMA: Konsola şu an hafızada kaç kişi olduğunu yazdırıyoruz
        System.out.println("--- GİRİŞ DENEMESİ ---");
        System.out.println("Girilen: '" + girilenAd + "' | Şifre: '" + girilenSifre + "'");
        System.out.println("Hafızadaki Kayıt Sayısı: " + kayitliKullanicilar.size());

        for(User u : kayitliKullanicilar) {
            // Konsola hafızadaki her kullanıcıyı yazdıralım ki eşleşme hatasını görelim
            System.out.println("Kontrol Ediliyor -> Kayıtlı: '" + u.getAdSoyad() + "' | Şifre: '" + u.getSifre() + "'");

            // İsimleri büyük/küçük harf duyarsız (equalsIgnoreCase), şifreyi birebir kontrol ediyoruz
            if(u.getAdSoyad().equalsIgnoreCase(girilenAd) && u.getSifre().equals(girilenSifre)) {
                kullaniciBulundu = true;
                break;
            }
        }

        if (kullaniciBulundu) {
            System.out.println("SONUÇ: Başarılı!");

            // Eğer admin ise admin sayfasına, değilse test sayfasına
            if (girilenAd.equalsIgnoreCase("admin")) {
                try {
                    SahneYoneticisi.sahneDegistir(event, "admin-view.fxml");
                } catch (IOException e) {
                    System.err.println("HATA: admin-view.fxml bulunamadı! Lütfen dosyayı oluşturduğunuzdan emin olun.");
                    DurumEtiketi.setText("Admin sayfası bulunamadı!");
                    e.printStackTrace();
                }
            } else {
                SahneYoneticisi.sahneDegistir(event, "user_test-view.fxml");
            }

        } else {
            System.out.println("SONUÇ: Başarısız. Eşleşme yok.");
            DurumEtiketi.setText("Kullanıcı adı veya şifre hatalı!");
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