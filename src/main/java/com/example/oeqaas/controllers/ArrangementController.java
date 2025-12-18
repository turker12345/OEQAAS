package com.example.oeqaas.controllers;

import com.example.oeqaas.utils.SceneManager;
import com.example.oeqaas.utils.VeritabaniBaglantisi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArrangementController {

    @FXML private TableView<SiralamaVerisi> userTable;
    @FXML private TableColumn<SiralamaVerisi, Integer> colName;
    @FXML private TableColumn<SiralamaVerisi, String> colPass;
    @FXML private TableColumn<SiralamaVerisi, Integer> colPhone;
    @FXML private TableColumn<SiralamaVerisi, String> colPhone1;

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("sira"));
        colPass.setCellValueFactory(new PropertyValueFactory<>("kullaniciAdi"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("cozulenTestSayisi"));
        colPhone1.setCellValueFactory(new PropertyValueFactory<>("basariYuzdesi"));

        verileriYukle();
    }

    private void verileriYukle() {
        ObservableList<SiralamaVerisi> liste = FXCollections.observableArrayList();
        String sql = "SELECT k.AdSoyad, COUNT(s.SonucID) as TestSayisi, SUM(s.DogruSayisi) as ToplamDogru, SUM(s.YanlisSayisi) as ToplamYanlis " +
                "FROM Kullanicilar k LEFT JOIN Sonuclar s ON k.KullaniciID = s.KullaniciID " +
                "GROUP BY k.KullaniciID, k.AdSoyad ORDER BY ToplamDogru DESC";

        try (Connection conn = VeritabaniBaglantisi.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            int sira = 1;
            while (rs.next()) {
                String ad = rs.getString("AdSoyad");
                int testSayisi = rs.getInt("TestSayisi");
                int dogru = rs.getInt("ToplamDogru");
                int yanlis = rs.getInt("ToplamYanlis");
                int toplamSoru = dogru + yanlis;
                double yuzde = (toplamSoru == 0) ? 0 : ((double) dogru / toplamSoru) * 100;
                String yuzdeStr = String.format("%%%.1f", yuzde);

                liste.add(new SiralamaVerisi(sira++, ad, testSayisi, yuzdeStr));
            }
            userTable.setItems(liste);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // YENİ EKLENEN METOT: İstatistiklere Git
    @FXML
    public void istatistiklereGit(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "statics-view.fxml");
    }

    @FXML
    public void geriDon(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "user_test_selection-view.fxml");
    }

    public static class SiralamaVerisi {
        private int sira;
        private String kullaniciAdi;
        private int cozulenTestSayisi;
        private String basariYuzdesi;

        public SiralamaVerisi(int sira, String kullaniciAdi, int cozulenTestSayisi, String basariYuzdesi) {
            this.sira = sira;
            this.kullaniciAdi = kullaniciAdi;
            this.cozulenTestSayisi = cozulenTestSayisi;
            this.basariYuzdesi = basariYuzdesi;
        }

        public int getSira() { return sira; }
        public String getKullaniciAdi() { return kullaniciAdi; }
        public int getCozulenTestSayisi() { return cozulenTestSayisi; }
        public String getBasariYuzdesi() { return basariYuzdesi; }
    }
}