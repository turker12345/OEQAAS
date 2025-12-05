package com.example.oeqaas.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class BaglantiTest {
    public static void main(String[] args) {
        // Kullandığın URL (Şifresiz)
        String url = "jdbc:sqlserver://localhost;databaseName=OEQAAS_DB;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

        try {
            System.out.println("Bağlanmaya çalışılıyor...");
            Connection conn = DriverManager.getConnection(url);
            System.out.println("BAŞARILI! Veritabanına bağlandı.");
            conn.close();
        } catch (Exception e) {
            System.out.println("--- HATA OLUŞTU ---");
            e.printStackTrace(); // Hatanın detayını yazdırır
        }
    }
}