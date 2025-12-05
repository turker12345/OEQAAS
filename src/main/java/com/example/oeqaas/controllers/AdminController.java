package com.example.oeqaas.controllers;

import com.example.oeqaas.models.Question;
import com.example.oeqaas.models.Test;
import com.example.oeqaas.models.User;
import com.example.oeqaas.utils.DataStore;
import com.example.oeqaas.utils.SceneManager;
import com.example.oeqaas.utils.VeritabaniBaglantisi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminController {

    // --- TAB 1: KULLANICI YÖNETİMİ ---
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, String> colPass;
    @FXML private TableColumn<User, String> colPhone;
    @FXML private TextField txtName, txtPass, txtPhone;
    @FXML private Label lblUserMsg;

    // --- TAB 2: TEST YÖNETİMİ (JSON) ---
    @FXML private ListView<Test> quizList;
    @FXML private TextField txtQuizName;
    @FXML private Label lblSelectedQuiz;
    @FXML private TextField txtQuestion, txtOptA, txtOptB, txtOptC, txtOptD;
    @FXML private ComboBox<String> comboCorrect;
    @FXML private Button btnAddQuestion;
    @FXML private Label lblQuizMsg;

    // --- TAB 3: SONUÇ İZLEME (SQL & Grafik) ---
    @FXML private ListView<User> resultUserList;
    @FXML private ListView<String> resultHistoryList;

    // Grafikler (YENİ EKLENDİ)
    @FXML private ProgressBar barDogru;
    @FXML private ProgressBar barYanlis;
    @FXML private ProgressIndicator indBasari;
    @FXML private Label lblYuzde;

    private ObservableList<User> userObservableList;
    private ObservableList<Test> quizObservableList;

    @FXML
    public void initialize() {
        // 1. Kullanıcıları SQL'den Yükle
        kullanicilariGetir();

        colName.setCellValueFactory(new PropertyValueFactory<>("adSoyad"));
        colPass.setCellValueFactory(new PropertyValueFactory<>("sifre"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        // 2. Testleri JSON'dan Yükle
        quizObservableList = FXCollections.observableArrayList(DataStore.testler);
        quizList.setItems(quizObservableList);

        // 3. ComboBox Ayarı
        comboCorrect.getItems().addAll("A", "B", "C", "D");
    }



    private void kullanicilariGetir() {
        List<User> dbUserList = new ArrayList<>();
        String sql = "SELECT * FROM Kullanicilar";

        try (Connection conn = VeritabaniBaglantisi.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User u = new User(
                        rs.getString("AdSoyad"),
                        rs.getString("Email"),
                        rs.getString("Sifre"),
                        rs.getString("Telefon")
                );
                u.setId(rs.getInt("KullaniciID"));
                dbUserList.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userObservableList = FXCollections.observableArrayList(dbUserList);
        userTable.setItems(userObservableList);
        resultUserList.setItems(userObservableList);
    }

    @FXML
    void addUser() {
        if (!txtName.getText().isEmpty() && !txtPass.getText().isEmpty()) {
            String sql = "INSERT INTO Kullanicilar (AdSoyad, Sifre, Telefon, Rol) VALUES (?, ?, ?, 'OGRENCI')";

            try (Connection conn = VeritabaniBaglantisi.baglan();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, txtName.getText());
                stmt.setString(2, txtPass.getText());
                stmt.setString(3, txtPhone.getText());
                stmt.executeUpdate();

                lblUserMsg.setText("Kullanıcı veritabanına eklendi.");
                kullanicilariGetir();
                clearUserInputs();

            } catch (SQLException e) {
                lblUserMsg.setText("Hata: " + e.getMessage());
            }
        } else {
            lblUserMsg.setText("Ad ve Şifre zorunludur!");
        }
    }

    @FXML
    void updateUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String sql = "UPDATE Kullanicilar SET AdSoyad=?, Sifre=?, Telefon=? WHERE KullaniciID=?";

            try (Connection conn = VeritabaniBaglantisi.baglan();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, txtName.getText());
                stmt.setString(2, txtPass.getText());
                stmt.setString(3, txtPhone.getText());
                stmt.setInt(4, selected.getId());
                stmt.executeUpdate();

                lblUserMsg.setText("Kullanıcı güncellendi.");
                kullanicilariGetir();
                clearUserInputs();

            } catch (SQLException e) {
                lblUserMsg.setText("Hata: " + e.getMessage());
            }
        }
    }

    @FXML
    void deleteUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String sql = "DELETE FROM Kullanicilar WHERE KullaniciID=?";

            try (Connection conn = VeritabaniBaglantisi.baglan();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, selected.getId());
                stmt.executeUpdate();

                lblUserMsg.setText("Kullanıcı silindi.");
                kullanicilariGetir();
                clearUserInputs();

            } catch (SQLException e) {
                lblUserMsg.setText("Hata: " + e.getMessage());
            }
        }
    }

    @FXML
    void userSelected() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtName.setText(selected.getAdSoyad());
            txtPass.setText(selected.getSifre());
            txtPhone.setText(selected.getTelefon());
        }
    }

    private void clearUserInputs() {
        txtName.clear(); txtPass.clear(); txtPhone.clear();
    }

    // ================= TEST İŞLEMLERİ (JSON) =================

    @FXML
    void addQuiz() {
        if (!txtQuizName.getText().isEmpty()) {
            Test newTest = new Test(txtQuizName.getText());
            DataStore.testler.add(newTest);
            DataStore.verileriKaydet();

            quizObservableList.setAll(DataStore.testler);
            txtQuizName.clear();
            lblQuizMsg.setText("Test oluşturuldu.");
        }
    }

    @FXML
    void deleteQuiz() {
        Test selected = quizList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            DataStore.testler.remove(selected);
            DataStore.verileriKaydet();

            quizObservableList.setAll(DataStore.testler);
            lblSelectedQuiz.setText("Seçili Test: Yok");
            btnAddQuestion.setDisable(true);
            lblQuizMsg.setText("Test silindi.");
        }
    }

    @FXML
    void quizSelected() {
        Test selected = quizList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblSelectedQuiz.setText("Seçili: " + selected.getAd());
            btnAddQuestion.setDisable(false);
        }
    }

    @FXML
    void addQuestionToQuiz() {
        Test selected = quizList.getSelectionModel().getSelectedItem();
        boolean inputsValid = selected != null && comboCorrect.getValue() != null && !txtQuestion.getText().isEmpty();

        if (inputsValid) {
            Question q = new Question(
                    txtQuestion.getText(), txtOptA.getText(), txtOptB.getText(),
                    txtOptC.getText(), txtOptD.getText(), comboCorrect.getValue()
            );
            selected.soruEkle(q);
            DataStore.verileriKaydet();

            lblQuizMsg.setText("Soru eklendi! (Toplam: " + selected.getSorular().size() + ")");
            txtQuestion.clear(); txtOptA.clear(); txtOptB.clear(); txtOptC.clear(); txtOptD.clear();
        }
    }

    @FXML
    void resultUserSelected() {
        User selected = resultUserList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // 1. Liste Görünümünü Doldur
            List<String> sonuclar = new ArrayList<>();
            String sqlListe = "SELECT * FROM Sonuclar WHERE KullaniciID = ? ORDER BY Tarih DESC";


            String sqlGrafik = "SELECT AVG(CAST(DogruSayisi AS FLOAT)) as OrtDogru, " +
                    "AVG(CAST(YanlisSayisi AS FLOAT)) as OrtYanlis, " +
                    "SUM(DogruSayisi) as ToplamDogru, " +
                    "SUM(YanlisSayisi) as ToplamYanlis " +
                    "FROM Sonuclar WHERE KullaniciID = ?";

            try (Connection conn = VeritabaniBaglantisi.baglan()) {

                // --- Liste İşlemleri ---
                try (PreparedStatement stmt = conn.prepareStatement(sqlListe)) {
                    stmt.setInt(1, selected.getId());
                    ResultSet rs = stmt.executeQuery();
                    while(rs.next()) {
                        String testAdi = rs.getString("TestAdi");
                        int dogru = rs.getInt("DogruSayisi");
                        int yanlis = rs.getInt("YanlisSayisi");
                        String tarih = rs.getString("Tarih");
                        sonuclar.add(testAdi + " | D:" + dogru + " Y:" + yanlis + " (" + tarih + ")");
                    }
                }

                // --- Grafik İşlemleri ---
                try (PreparedStatement stmt = conn.prepareStatement(sqlGrafik)) {
                    stmt.setInt(1, selected.getId());
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        double ortDogru = rs.getDouble("OrtDogru");
                        double ortYanlis = rs.getDouble("OrtYanlis");
                        int topDogru = rs.getInt("ToplamDogru");
                        int topYanlis = rs.getInt("ToplamYanlis");

                        // Grafikleri 10 üzerinden normalize et (Çünkü arayüzdeki çizgiler 10'a kadar)
                        // Eğer ortalama 7 doğru ise bar %70 dolar.
                        barDogru.setProgress(ortDogru / 10.0);
                        barYanlis.setProgress(ortYanlis / 10.0);

                        // Başarı Yüzdesi Hesapla
                        double toplamSoru = topDogru + topYanlis;
                        double basariYuzdesi = 0;
                        if (toplamSoru > 0) {
                            basariYuzdesi = (double) topDogru / toplamSoru;
                        }

                        indBasari.setProgress(basariYuzdesi);
                        lblYuzde.setText(String.format("%.0f", basariYuzdesi * 100)); // %85 gibi yaz
                    }
                }

                if (sonuclar.isEmpty()) {
                    sonuclar.add("Henüz çözülmüş test yok.");
                    sifirlaGrafik();
                }

            } catch (SQLException e) {
                sonuclar.add("Hata: " + e.getMessage());
            }

            resultHistoryList.setItems(FXCollections.observableArrayList(sonuclar));
        }
    }

    private void sifirlaGrafik() {
        barDogru.setProgress(0);
        barYanlis.setProgress(0);
        indBasari.setProgress(0);
        lblYuzde.setText("0");
    }

    @FXML
    void cikisYap(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "login-view.fxml");
    }
}