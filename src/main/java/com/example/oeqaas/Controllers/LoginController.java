package com.example.oeqaas.Controllers;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

public class LoginController {

    @FXML
    public Text AdSoyadText;
    @FXML
    public PasswordField Sifre;
    @FXML
    private Label Quiz;
    @FXML
    protected void GirisButton(ActionEvent event) {
        Quiz.setText("Butona tıkladın!");
    }
    @FXML
    protected void KayitOlButton(ActionEvent event) {

    }
    @FXML
    protected void SifremiUnuttumButton(ActionEvent event) {

    }
}
//For adding the file
