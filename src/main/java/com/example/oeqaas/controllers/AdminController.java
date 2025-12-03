package com.example.oeqaas.controllers;
import com.example.oeqaas.models.Question;
import com.example.oeqaas.models.Test;
import com.example.oeqaas.models.User;
import com.example.oeqaas.utils.DataStore;
import com.example.oeqaas.utils.ScaneManager; // Use your existing utility name
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;

public class AdminController {

    // --- TAB 1: USER MANAGEMENT ---
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, String> colPass;
    @FXML private TableColumn<User, String> colPhone;
    @FXML private TextField txtName, txtPass, txtPhone;
    @FXML private Label lblUserMsg;

    // --- TAB 2: TEST MANAGEMENT ---
    @FXML private ListView<Test> quizList;
    @FXML private TextField txtQuizName;
    @FXML private Label lblSelectedQuiz;
    @FXML private TextField txtQuestion, txtOptA, txtOptB, txtOptC, txtOptD;
    @FXML private ComboBox<String> comboCorrect;
    @FXML private Button btnAddQuestion;
    @FXML private Label lblQuizMsg;

    // --- TAB 3: RESULTS MONITORING ---
    @FXML private ListView<User> resultUserList;
    @FXML private ListView<String> resultHistoryList;

    private ObservableList<User> userObservableList;
    private ObservableList<Test> quizObservableList;

    @FXML
    public void initialize() {
        // 1. Setup User Table
        // Wrap DataStore list in ObservableList so UI updates automatically
        userObservableList = FXCollections.observableArrayList(DataStore.kullanicilar);
        userTable.setItems(userObservableList);
        resultUserList.setItems(userObservableList); // Share list with Results tab

        // Connect Table Columns to User Model properties
        colName.setCellValueFactory(new PropertyValueFactory<>("adSoyad"));
        colPass.setCellValueFactory(new PropertyValueFactory<>("sifre"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        // 2. Setup Test List
        quizObservableList = FXCollections.observableArrayList(DataStore.testler);
        quizList.setItems(quizObservableList);

        // 3. Setup ComboBox for Correct Answer
        comboCorrect.getItems().addAll("A", "B", "C", "D");
    }

    // ================= USER OPERATIONS =================
    @FXML
    void addUser() {
        if (!txtName.getText().isEmpty() && !txtPass.getText().isEmpty()) {
            User newUser = new User(txtName.getText(), "", txtPass.getText(), txtPhone.getText());

            // Add to BOTH the central store and the UI list
            DataStore.kullanicilar.add(newUser);
            userObservableList.setAll(DataStore.kullanicilar);

            lblUserMsg.setText("Kullanıcı başarıyla eklendi.");
            clearUserInputs();
            userTable.getSelectionModel().clearSelection(); // UX İyileştirme: Seçimi temizle
        } else {
            lblUserMsg.setText("Hata: Ad ve Şifre zorunludur!");
        }
    }

    @FXML
    void updateUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        // Güncelleme yapabilmek için hem seçili bir kullanıcı hem de zorunlu alanlar olmalı
        if (selected != null && !txtName.getText().isEmpty() && !txtPass.getText().isEmpty()) {
            // Update the object directly (Model)
            selected.setAdSoyad(txtName.getText());
            selected.setSifre(txtPass.getText());
            selected.setTelefon(txtPhone.getText());

            // Refresh UI
            userTable.refresh();
            resultUserList.refresh();

            lblUserMsg.setText("Kullanıcı başarıyla güncellendi.");
            clearUserInputs(); // DÜZELTME & UX İyileştirme: Girişleri temizle
            userTable.getSelectionModel().clearSelection(); // UX İyileştirme: Seçimi temizle
        } else if (selected == null) {
            lblUserMsg.setText("Hata: Güncellemek için bir kullanıcı seçiniz.");
        } else {
            lblUserMsg.setText("Hata: Ad ve Şifre zorunludur!");
        }
    }

    @FXML
    void deleteUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            DataStore.kullanicilar.remove(selected);
            userObservableList.setAll(DataStore.kullanicilar);
            lblUserMsg.setText("Kullanıcı silindi.");
            clearUserInputs();
            userTable.getSelectionModel().clearSelection(); // UX İyileştirme: Seçimi temizle
        } else {
            lblUserMsg.setText("Hata: Silmek için bir kullanıcı seçiniz.");
        }
    }

    // When user clicks a row in the table, fill the inputs
    @FXML
    void userSelected() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtName.setText(selected.getAdSoyad());
            txtPass.setText(selected.getSifre());
            txtPhone.setText(selected.getTelefon());
            lblUserMsg.setText(""); // Seçim yapıldığında mesajı temizle
        }
    }

    private void clearUserInputs() {
        txtName.clear(); txtPass.clear(); txtPhone.clear();
        lblUserMsg.setText(""); // UX İyileştirme: Mesajı temizle
    }

    // ================= TEST OPERATIONS =================
    @FXML
    void addQuiz() {
        if (!txtQuizName.getText().isEmpty()) {
            Test newTest = new Test(txtQuizName.getText());
            DataStore.testler.add(newTest);
            quizObservableList.setAll(DataStore.testler);
            txtQuizName.clear();
            lblQuizMsg.setText("Test başarıyla oluşturuldu."); // UX İyileştirme
        } else {
            lblQuizMsg.setText("Hata: Lütfen test adı girin."); // UX İyileştirme
        }
    }

    @FXML
    void deleteQuiz() {
        Test selected = quizList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            DataStore.testler.remove(selected);
            quizObservableList.setAll(DataStore.testler);
            lblSelectedQuiz.setText("Seçili Test: Yok");
            btnAddQuestion.setDisable(true);
            lblQuizMsg.setText(selected.getAd() + " testi silindi."); // UX İyileştirme
        } else {
            lblQuizMsg.setText("Hata: Lütfen silmek için bir test seçin."); // UX İyileştirme
        }
    }

    @FXML
    void quizSelected() {
        Test selected = quizList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblSelectedQuiz.setText("Seçili Test: " + selected.getAd());
            btnAddQuestion.setDisable(false);
            lblQuizMsg.setText("Soru eklemeye hazırsınız."); // UX İyileştirme
        }
    }

    @FXML
    void addQuestionToQuiz() {
        Test selected = quizList.getSelectionModel().getSelectedItem();

        // HATA DÜZELTME: Question constructor hatası almamak ve doğru veri girmek için
        // tüm şık alanlarının doldurulup doldurulmadığı kontrol edilir.
        boolean inputsValid = selected != null
                && comboCorrect.getValue() != null
                && !txtQuestion.getText().isEmpty()
                && !txtOptA.getText().isEmpty()
                && !txtOptB.getText().isEmpty()
                && !txtOptC.getText().isEmpty()
                && !txtOptD.getText().isEmpty(); // Kritik Kontroller Eklendi

        if (inputsValid) { // Satır 162'nin yeni karşılığı

            // Satır 166: Question constructor çağrısı, artık tüm alanlar dolu olduğu için güvenli.
            Question q = new Question(
                    txtQuestion.getText(),
                    txtOptA.getText(),
                    txtOptB.getText(),
                    txtOptC.getText(),
                    txtOptD.getText(),
                    comboCorrect.getValue()
            );

            selected.soruEkle(q);
            lblQuizMsg.setText("Soru başarıyla eklendi! (Toplam: " + selected.getSorular().size() + ")");

            // Clear Question Inputs (UX İyileştirme)
            txtQuestion.clear();
            txtOptA.clear();
            txtOptB.clear();
            txtOptC.clear();
            txtOptD.clear();
            comboCorrect.getSelectionModel().clearSelection(); // ComboBox'ı da temizle
        } else {
            // Hata mesajı düzeltildi
            lblQuizMsg.setText("Hata: Lütfen bir test seçin ve Soru metni, tüm şıklar (A, B, C, D) ve Doğru Cevap'ı doldurun.");
        }
    }

    // ================= RESULTS OPERATIONS =================
    @FXML
    void resultUserSelected() {
        User selected = resultUserList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Show that user's history in the list
            if (selected.getQuizGeçmişi() != null && !selected.getQuizGeçmişi().isEmpty()) { // Boş liste kontrolü eklendi
                resultHistoryList.setItems(FXCollections.observableArrayList(selected.getQuizGeçmişi()));
            } else {
                resultHistoryList.getItems().clear();
                resultHistoryList.getItems().add("Seçili kullanıcının test geçmişi bulunmamaktadır."); // UX İyileştirme
            }
        } else {
            resultHistoryList.getItems().clear();
        }
    }

    @FXML
    void cikisYap(ActionEvent event) throws IOException {
        ScaneManager.sahneDegistir(event, "login-view.fxml");
    }
}