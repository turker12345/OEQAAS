package com.example.oeqaas.controllers;

import com.example.oeqaas.utils.DataStore;
import com.example.oeqaas.utils.SceneManager;
import com.example.oeqaas.utils.VeritabaniBaglantisi;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsController {

    @FXML private ProgressBar barDogru;
    @FXML private ProgressBar barYanlis;
    @FXML private Label lblYuzde;      // Başarı Yüzdesi
    @FXML private Label lblYuzde1;     // Yanlış Sayısı
    @FXML private Label lblYuzde11;    // Doğru Sayısı
    @FXML private ProgressIndicator indBasari;

    @FXML private ListView<String> gecmisListesi;
    @FXML private TextField aramaKutusu;

    private List<String> tumSonuclar = new ArrayList<>();

    @FXML
    public void initialize() {
        if (DataStore.aktifKullanici != null) {
            verileriGetir(); // Artık bu metod aşağıda tanımlı
            listeyiDoldur();
        }
    }

    // EKSİK OLAN METOD BU:
    private void verileriGetir() {
        String sql = "SELECT SUM(DogruSayisi) as ToplamDogru, SUM(YanlisSayisi) as ToplamYanlis FROM Sonuclar WHERE KullaniciID = ?";

        try (Connection conn = VeritabaniBaglantisi.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, DataStore.aktifKullanici.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int dogru = rs.getInt("ToplamDogru");
                int yanlis = rs.getInt("ToplamYanlis");
                int toplam = dogru + yanlis;

                // Label'ları güncelle (Null kontrolü yapıldı)
                if (lblYuzde11 != null) lblYuzde11.setText(String.valueOf(dogru));
                if (lblYuzde1 != null) lblYuzde1.setText(String.valueOf(yanlis));

                // Oranları hesapla
                double basariOrani = (toplam == 0) ? 0 : (double) dogru / toplam;

                // Barların doluluk oranı
                if (barDogru != null) barDogru.setProgress(basariOrani);
                // Yanlış barı için opsiyonel: (double) yanlis / toplam
                if (barYanlis != null) barYanlis.setProgress((toplam == 0) ? 0 : (double) yanlis / toplam);

                if (indBasari != null) indBasari.setProgress(basariOrani);
                if (lblYuzde != null) lblYuzde.setText("%" + (int)(basariOrani * 100));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listeyiDoldur() {
        tumSonuclar.clear();
        String sql = "SELECT TestAdi, DogruSayisi, YanlisSayisi, Tarih FROM Sonuclar WHERE KullaniciID = ? ORDER BY Tarih DESC";

        try (Connection conn = VeritabaniBaglantisi.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, DataStore.aktifKullanici.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String satir = rs.getString("TestAdi") +
                        " | D: " + rs.getInt("DogruSayisi") +
                        " Y: " + rs.getInt("YanlisSayisi") +
                        " (" + rs.getDate("Tarih") + ")";
                tumSonuclar.add(satir);
            }
            if (gecmisListesi != null) {
                gecmisListesi.setItems(FXCollections.observableArrayList(tumSonuclar));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ara(ActionEvent event) {
        if (aramaKutusu == null || gecmisListesi == null) return;

        String aranan = aramaKutusu.getText().toLowerCase();
        List<String> filtrelenmis = new ArrayList<>();

        for (String sonuc : tumSonuclar) {
            if (sonuc.toLowerCase().contains(aranan)) {
                filtrelenmis.add(sonuc);
            }
        }
        gecmisListesi.setItems(FXCollections.observableArrayList(filtrelenmis));
    }

    @FXML
    public void geriDon(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "user_test_selection-view.fxml");
    }
}