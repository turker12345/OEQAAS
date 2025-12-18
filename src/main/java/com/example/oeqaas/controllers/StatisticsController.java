package com.example.oeqaas.controllers;

import com.example.oeqaas.utils.DataStore;
import com.example.oeqaas.utils.SceneManager;
import com.example.oeqaas.utils.VeritabaniBaglantisi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    @FXML private Label lblYuzde, lblYuzde1, lblYuzde11;
    @FXML private ProgressIndicator indBasari;

    // YENİ: Liste ve Arama
    @FXML private ListView<String> gecmisListesi;
    @FXML private TextField aramaKutusu; // FXML'e TextField eklemeyi unutma!

    private List<String> tumSonuclar = new ArrayList<>();

    @FXML
    public void initialize() {
        if (DataStore.aktifKullanici != null) {
            verileriGetir();
            listeyiDoldur();
        }
    }

    // ... verileriGetir() metodu önceki cevaptaki gibi kalacak ...

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
    public void geriDon(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "user_test_selection-view.fxml");
    }
}