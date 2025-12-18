package com.example.oeqaas.controllers;

import com.example.oeqaas.utils.SceneManager;
import com.example.oeqaas.utils.VeritabaniBaglantisi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ForgetPasswordController {

    @FXML private TextField AdSoyadAlani; // FXML'de telefon için bu ID kullanılmış
    @FXML private PasswordField SifreAlani;
    @FXML private PasswordField SifreAlani1; // Şifre Tekrar
    @FXML private Label DurumEtiketi;

    @FXML
    protected void SifreyiSifirla(ActionEvent event) {
        String telefon = AdSoyadAlani.getText();
        String yeniSifre = SifreAlani.getText();
        String yeniSifreTekrar = SifreAlani1.getText();

        if (telefon.isEmpty() || yeniSifre.isEmpty()) {
            DurumEtiketi.setText("Lütfen tüm alanları doldurun.");
            return;
        }

        if (!yeniSifre.equals(yeniSifreTekrar)) {
            DurumEtiketi.setText("Şifreler uyuşmuyor!");
            return;
        }

        String sql = "UPDATE Kullanicilar SET Sifre = ? WHERE Telefon = ?";

        try (Connection conn = VeritabaniBaglantisi.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, yeniSifre);
            stmt.setString(2, telefon);

            int etkilenen = stmt.executeUpdate();
            if (etkilenen > 0) {
                DurumEtiketi.setText("Şifre başarıyla güncellendi!");
            } else {
                DurumEtiketi.setText("Bu telefon numarası kayıtlı değil.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            DurumEtiketi.setText("Hata: " + e.getMessage());
        }
    }

    @FXML
    protected void GeriDon(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "login-view.fxml");
    }
}