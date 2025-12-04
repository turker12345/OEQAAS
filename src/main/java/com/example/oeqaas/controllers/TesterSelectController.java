package com.example.oeqaas.controllers;
import com.example.oeqaas.utils.SceneManager; // Using the correct English name
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class TesterSelectController {

    @FXML
    protected void testBaslat(ActionEvent event) throws IOException {
        System.out.println("Test seçildi, sınava yönlendiriliyor...");

        // Navigate to the Quiz Taking Screen
        SceneManager.sahneDegistir(event, "user_test-view.fxml");
    }

    @FXML
    protected void cikisYap(ActionEvent event) throws IOException {
        // Go back to Login Page
        SceneManager.sahneDegistir(event, "login-view.fxml");
    }
}