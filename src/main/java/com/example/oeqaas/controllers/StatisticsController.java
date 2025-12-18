package com.example.oeqaas.controllers;

import com.example.oeqaas.utils.DataStore;
import com.example.oeqaas.utils.SceneManager;
import com.example.oeqaas.utils.VeritabaniBaglantisi;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart; // YENİ IMPORT
import javafx.scene.chart.XYChart;  // YENİ IMPORT
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Label;
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
    @FXML private Label lblYuzde, lblYuzde1, lblYuzde11;
    @FXML private ProgressIndicator indBasari;
    @FXML private ListView<String> gecmisListesi;
    @FXML private TextField aramaKutusu;

    // YENİ: BarChart Tanımlaması (FXML'de fx:id="barChart" verdiğinizi varsayıyorum)
    @FXML private BarChart<String, Number> barChart;

    private List<String> tumSonuclar = new ArrayList<>();

    @FXML
    public void initialize() {
        if (DataStore.aktifKullanici != null) {
            listeyiDoldur();
            grafikleriDoldur(); // BarChart ve diğerlerini doldur
        }
    }

    private void grafikleriDoldur() {
        String sql = "SELECT SUM(DogruSayisi) as ToplamDogru, SUM(YanlisSayisi) as ToplamYanlis FROM Sonuclar WHERE KullaniciID = ?";

        try (Connection conn = VeritabaniBaglantisi.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, DataStore.aktifKullanici.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int dogru = rs.getInt("ToplamDogru");
                int yanlis = rs.getInt("ToplamYanlis");
                int toplam = dogru + yanlis;

                // 1. ProgressBar ve Indicator Ayarları (Mevcut kodlar)
                double yuzde = (toplam == 0) ? 0 : (double) dogru / toplam;
                if (indBasari != null) indBasari.setProgress(yuzde);
                if (lblYuzde != null) lblYuzde.setText(String.format("%%%.0f", yuzde * 100));

                // 2. YENİ: BarChart Doldurma
                if (barChart != null) {
                    barChart.getData().clear(); // Önce temizle

                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    series.setName("Toplam Performans");

                    series.getData().add(new XYChart.Data<>("Doğru", dogru));
                    series.getData().add(new XYChart.Data<>("Yanlış", yanlis));

                    barChart.getData().add(series);
                }
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
            gecmisListesi.setItems(FXCollections.observableArrayList(tumSonuclar));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ara(ActionEvent event) {
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
    public void siralamayaGit(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "arrangement-view.fxml");
    }

    @FXML
    public void geriDon(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "user_test_selection-view.fxml");
    }
}