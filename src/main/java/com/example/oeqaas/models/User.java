package com.example.oeqaas.models;

public class User {
    private int id;
    private String adSoyad;
    private String email;
    private String sifre;
    private String telefon;

    // Constructor (Yapıcı Metot)
    public User(String adSoyad, String email, String sifre, String telefon) {
        this.adSoyad = adSoyad;
        this.email = email;
        this.sifre = sifre;
        this.telefon = telefon;
    }

    // Getters and Setters (Erişim Metotları)
    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSifre() { return sifre; }
    public void setSifre(String sifre) { this.sifre = sifre; }

    public String getTelefon() { return telefon; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
}