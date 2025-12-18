package com.example.oeqaas.controllers;

import com.example.oeqaas.models.Test; // Test modelini import et
import com.example.oeqaas.utils.DataStore;
import com.example.oeqaas.utils.SceneManager;
import com.example.oeqaas.utils.VeritabaniBaglantisi;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TesterSelectController {

    @FXML private Label DogruSayisiEtiketi;
    @FXML private Label YanlisSayisiEtiketi;

    // FXML'e eklediğimiz liste
    @FXML private ListView<Test> testListesi;

    @FXML
    public void initialize() {
        // Özet bilgileri getir
        if (DogruSayisiEtiketi != null && DataStore.aktifKullanici != null) {
            ozetBilgiGetir();
        }

        // Testleri listeye yükle
        if (testListesi != null) {
            testListesi.setItems(FXCollections.observableArrayList(DataStore.testler));
        }
    }

    private void ozetBilgiGetir() {
        // ... (Bu kısım önceki kodla aynı kalabilir) ...
        // Kopyalamak istersen önceki cevabımdan alabilirsin.
    }

    @FXML
    protected void testBaslat(ActionEvent event) throws IOException {
        // Listeden seçili testi al
        Test secilen = testListesi.getSelectionModel().getSelectedItem();

        if (secilen != null) {
            DataStore.secilenTest = secilen; // Hafızaya kaydet
            System.out.println("Seçilen Test: " + secilen.getAd());
            SceneManager.sahneDegistir(event, "user_test-view.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen listeden bir test seçiniz!");
            alert.showAndWait();
        }
    }

    // ... Diğer metodlar (istatistiklereGit, siralamayaGit vb.) aynı kalacak ...
    @FXML
    protected void istatistiklereGit(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "statics-view.fxml");
    }

    @FXML
    protected void siralamayaGit(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "arrangement-view.fxml");
    }

    @FXML
    protected void cikisYap(ActionEvent event) throws IOException {
        DataStore.aktifKullanici = null;
        SceneManager.sahneDegistir(event, "login-view.fxml");
    }
}