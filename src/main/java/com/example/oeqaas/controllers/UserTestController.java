package com.example.oeqaas.controllers;

import com.example.oeqaas.models.Question;
import com.example.oeqaas.models.Test;
import com.example.oeqaas.utils.DataStore;
import com.example.oeqaas.utils.SceneManager;
import com.example.oeqaas.utils.VeritabaniBaglantisi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserTestController {

    @FXML private Label soruNoLabel;
    @FXML private Label soruMetniLabel;
    @FXML private Label secenekALabel;
    @FXML private Label secenekBLabel;
    @FXML private Label secenekCLabel;
    @FXML private Label secenekDLabel;

    private List<Question> currentQuestions;
    private int questionIndex = 0;
    private int dogruSayisi = 0;
    private int yanlisSayisi = 0;
    private String aktifTestAdi = "Genel Test";

    @FXML
    public void initialize() {
        if (!DataStore.testler.isEmpty()) {
            Test activeTest = DataStore.testler.get(0);
            aktifTestAdi = activeTest.getAd();
            currentQuestions = activeTest.getSorular();

            if (currentQuestions != null && !currentQuestions.isEmpty()) {
                soruYukle();
            } else {
                soruMetniLabel.setText("Bu testte hiç soru yok!");
            }
        } else {
            soruMetniLabel.setText("Sistemde yüklü test bulunamadı.");
        }
    }

    private void soruYukle() {
        if (questionIndex < currentQuestions.size()) {
            Question q = currentQuestions.get(questionIndex);
            soruNoLabel.setText((questionIndex + 1) + ".");
            soruMetniLabel.setText(q.getSoruMetni());
            secenekALabel.setText(q.getSecenekA());
            secenekBLabel.setText(q.getSecenekB());
            secenekCLabel.setText(q.getSecenekC());
            secenekDLabel.setText(q.getSecenekD());
        } else {
            testiBitir();
        }
    }

    @FXML
    public void cevabiKontrolEt(ActionEvent event) {
        Button tiklananButon = (Button) event.getSource();
        String verilenCevap = (String) tiklananButon.getUserData();
        Question mevcutSoru = currentQuestions.get(questionIndex);

        if (mevcutSoru.getDogruCevap().equalsIgnoreCase(verilenCevap)) {
            dogruSayisi++;
        } else {
            yanlisSayisi++;
        }
        questionIndex++;
        soruYukle();
    }

    private void testiBitir() {
        // SQL'e Sonuç Kaydı
        if (DataStore.aktifKullanici != null) {
            String sql = "INSERT INTO Sonuclar (KullaniciID, TestAdi, DogruSayisi, YanlisSayisi) VALUES (?, ?, ?, ?)";

            try (Connection baglanti = VeritabaniBaglantisi.baglan();
                 PreparedStatement sorgu = baglanti.prepareStatement(sql)) {

                sorgu.setInt(1, DataStore.aktifKullanici.getId());
                sorgu.setString(2, aktifTestAdi);
                sorgu.setInt(3, dogruSayisi);
                sorgu.setInt(4, yanlisSayisi);

                sorgu.executeUpdate();
                System.out.println("Sonuç SQL'e kaydedildi.");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test Bitti");
        alert.setHeaderText("Sonuçlarınız Kaydedildi");
        alert.setContentText("Doğru: " + dogruSayisi + "\nYanlış: " + yanlisSayisi);
        alert.showAndWait();

        try {
            SceneManager.sahneDegistir(null, "user_test_selection-view.fxml");
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    public void geriDon(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "user_test_selection-view.fxml");
    }
}