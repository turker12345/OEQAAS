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
    @FXML private TableColumn<User, String> colRole; // YENİ: Rol Sütunu

    @FXML private TextField txtName, txtPass, txtPhone;
    @FXML private ComboBox<String> comboRole; // YENİ: Rol Seçimi için ComboBox
    @FXML private Label lblUserMsg;

    // --- TAB 2 ve 3 (Test ve Sonuçlar) ---
    // (Önceki kodlarınızın aynısı kalabilir, buraya sadece değişenleri ekliyorum)
    @FXML private ListView<Test> quizList;
    @FXML private TextField txtQuizName;
    @FXML private Label lblSelectedQuiz;
    @FXML private TextField txtQuestion, txtOptA, txtOptB, txtOptC, txtOptD;
    @FXML private ComboBox<String> comboCorrect;
    @FXML private Button btnAddQuestion;
    @FXML private Label lblQuizMsg;
    @FXML private ListView<User> resultUserList;
    @FXML private ListView<String> resultHistoryList;
    @FXML private ProgressBar barDogru;
    @FXML private ProgressBar barYanlis;
    @FXML private ProgressIndicator indBasari;
    @FXML private Label lblYuzde;

    private ObservableList<User> userObservableList;
    private ObservableList<Test> quizObservableList;

    @FXML
    public void initialize() {
        // Tablo Sütunlarını Bağla
        colName.setCellValueFactory(new PropertyValueFactory<>("adSoyad"));
        colPass.setCellValueFactory(new PropertyValueFactory<>("sifre"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("rol")); // YENİ

        // Kullanıcı Rol Seçenekleri
        if (comboRole != null) {
            comboRole.getItems().addAll("OGRENCI", "ADMIN");
            comboRole.getSelectionModel().selectFirst();
        }

        kullanicilariGetir();

        // Diğer initialize işlemleri...
        quizObservableList = FXCollections.observableArrayList(DataStore.testler);
        if (quizList != null) quizList.setItems(quizObservableList);
        if (comboCorrect != null) comboCorrect.getItems().addAll("A", "B", "C", "D");
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
                u.setRol(rs.getString("Rol")); // YENİ: Rolü veritabanından al
                dbUserList.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userObservableList = FXCollections.observableArrayList(dbUserList);
        userTable.setItems(userObservableList);
        if (resultUserList != null) resultUserList.setItems(userObservableList);
    }

    @FXML
    void addUser() {
        if (!txtName.getText().isEmpty() && !txtPass.getText().isEmpty()) {
            // YENİ: Rolü de ekle
            String sql = "INSERT INTO Kullanicilar (AdSoyad, Sifre, Telefon, Rol) VALUES (?, ?, ?, ?)";

            try (Connection conn = VeritabaniBaglantisi.baglan();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, txtName.getText());
                stmt.setString(2, txtPass.getText());
                stmt.setString(3, txtPhone.getText());

                // Seçilen rolü al, yoksa varsayılan OGRENCI yap
                String secilenRol = (comboRole != null && comboRole.getValue() != null) ? comboRole.getValue() : "OGRENCI";
                stmt.setString(4, secilenRol);

                stmt.executeUpdate();

                lblUserMsg.setText("Kullanıcı eklendi.");
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
            // YENİ: Rol güncelleme eklendi
            String sql = "UPDATE Kullanicilar SET AdSoyad=?, Sifre=?, Telefon=?, Rol=? WHERE KullaniciID=?";

            try (Connection conn = VeritabaniBaglantisi.baglan();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, txtName.getText());
                stmt.setString(2, txtPass.getText());
                stmt.setString(3, txtPhone.getText());

                String secilenRol = (comboRole != null && comboRole.getValue() != null) ? comboRole.getValue() : "OGRENCI";
                stmt.setString(4, secilenRol);

                stmt.setInt(5, selected.getId());
                stmt.executeUpdate();

                lblUserMsg.setText("Kullanıcı güncellendi.");
                kullanicilariGetir();
                clearUserInputs();

            } catch (SQLException e) {
                lblUserMsg.setText("Hata: " + e.getMessage());
            }
        }
    }

    // Diğer metodlar (deleteUser, userSelected vb.) aynı kalacak...
    @FXML
    void userSelected() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtName.setText(selected.getAdSoyad());
            txtPass.setText(selected.getSifre());
            txtPhone.setText(selected.getTelefon());
            if(comboRole != null) comboRole.setValue(selected.getRol());
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

    private void clearUserInputs() {
        txtName.clear(); txtPass.clear(); txtPhone.clear();
        if(comboRole != null) comboRole.getSelectionModel().selectFirst();
    }

    // ... Test ve Sonuç metodları (addQuiz, resultUserSelected vb.) buraya gelecek ...
    // Önceki kodlarınızdaki gibi kalabilirler.

    // --- ÖNEMLİ: BarChart kodları StatisticsController'da olmalı ---
    // AdminController'da BarChart kullanmıyorsanız bu kısım boş kalabilir.
}