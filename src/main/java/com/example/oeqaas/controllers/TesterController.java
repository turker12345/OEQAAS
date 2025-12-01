package com.example.oeqaas.controllers;

import com.example.oeqaas.models.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;

public class TesterController {

    @FXML
    private Label DogruSayisiEtiketi;

    @FXML
    private Label YanlisSayisiEtiketi;

    @FXML
    private Label Quiz;

    private List<Question> soruListesi;
    private int aktifSoruIndeksi = 0;
    private int dogruSayisi = 0;
    private int yanlisSayisi = 0;

    @FXML
    public void initialize() {

        soruListesi = new ArrayList<>();
        soruListesi.add(new Question("Java'da hangisi bir döngü yapısıdır?", "if", "for", "switch", "class", "B"));
        soruListesi.add(new Question("Hangisi tam sayı veri tipidir?", "String", "boolean", "int", "double", "C"));
        soruListesi.add(new Question("SQL'de veri çekmek için hangi komut kullanılır?", "INSERT", "UPDATE", "SELECT", "DELETE", "C"));

        soruYukle();

        skorGuncelle();
    }

    private void soruYukle() {
        if (aktifSoruIndeksi < soruListesi.size()) {
            Question aktifSoru = soruListesi.get(aktifSoruIndeksi);

            if (Quiz != null) {
                Quiz.setText((aktifSoruIndeksi + 1) + ") " + aktifSoru.getSoruMetni());
            }
        } else {
            if (Quiz != null) {
                Quiz.setText("Test Bitti! Tebrikler!.");
                Quiz.setText("Sonuçlarınızı Kontrol Edin.");
            }

        }
    }

    public void cevapKontrol(String verilenCevap) {
        Question aktifSoru = soruListesi.get(aktifSoruIndeksi);

        if (aktifSoru.getDogruCevap().equals(verilenCevap)) {
            dogruSayisi++;
        } else {
            yanlisSayisi++;
        }

        skorGuncelle();
        aktifSoruIndeksi++;
        soruYukle();
    }

    private void skorGuncelle() {
        if (DogruSayisiEtiketi != null) DogruSayisiEtiketi.setText(String.valueOf(dogruSayisi));
        if (YanlisSayisiEtiketi != null) YanlisSayisiEtiketi.setText(String.valueOf(yanlisSayisi));
    }
}