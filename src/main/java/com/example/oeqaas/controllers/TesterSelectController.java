package com.example.oeqaas.controllers;

import com.example.oeqaas.utils.ScaneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class TesterSelectController {

    @FXML
    protected void testBaslat(ActionEvent event) throws IOException {
        // In the future, you can detect WHICH button was clicked to load specific tests.
        // For now, we load the general test view.
        System.out.println("Test seçildi, sınava yönlendiriliyor...");
        ScaneManager.sahneDegistir(event, "user_test-view.fxml");
    }

    @FXML
    protected void cikisYap(ActionEvent event) throws IOException {
        ScaneManager.sahneDegistir(event, "login-view.fxml");
    }
}