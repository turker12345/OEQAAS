package com.example.oeqaas.controllers;

import com.example.oeqaas.models.Question;
import com.example.oeqaas.models.Test;
import com.example.oeqaas.utils.DataStore;
import com.example.oeqaas.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class UserTestController {

    @FXML private Label Quiz; // The question text label (ensure fx:id matches FXML)
    @FXML private TextField AdSoyadAlani; // Assuming this is for input or status

    private List<Question> currentQuestions;
    private int questionIndex = 0;

    @FXML
    public void initialize() {
        // Load the first test from DataStore
        if (!DataStore.testler.isEmpty()) {
            Test activeTest = DataStore.testler.get(0);
            currentQuestions = activeTest.getSorular();
            loadQuestion();
        } else {
            if(Quiz != null) Quiz.setText("Aktif test bulunamadı.");
        }
    }

    private void loadQuestion() {
        if (currentQuestions != null && questionIndex < currentQuestions.size()) {
            Question q = currentQuestions.get(questionIndex);
            // Assuming your labels update here
            // Note: You need to map FXML buttons to check answers
            // For now, we just display the question text
            System.out.println("Soru Yükleniyor: " + q.getSoruMetni());
            // Note: You might need to update FXML to have a proper Label for Question Text
            // distinct from the Title if you haven't already.
        } else {
            System.out.println("Test Bitti");
        }
    }

    @FXML
    public void geriDon(ActionEvent event) throws IOException {
        SceneManager.sahneDegistir(event, "user_test_selection-view.fxml");
    }
}