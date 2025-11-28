package com.example.oeqaas.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String adSoyad;
    private String email;
    private String sifre;
    private String telefon;

    // NEW: Store past quiz scores for Admin Monitoring
    private List<String> quizGeçmişi = new ArrayList<>();

    public User(String adSoyad, String email, String sifre, String telefon) {
        this.adSoyad = adSoyad;
        this.email = email;
        this.sifre = sifre;
        this.telefon = telefon;
    }

    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSifre() { return sifre; }
    public void setSifre(String sifre) { this.sifre = sifre; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }

    // NEW METHODS
    public void skorEkle(String sonuc) {
        quizGeçmişi.add(sonuc);
    }

    public List<String> getQuizGeçmişi() {
        return quizGeçmişi;
    }

    @Override
    public String toString() {
        return adSoyad; // For display in ListView
    }
}