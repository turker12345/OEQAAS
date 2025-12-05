package com.example.oeqaas.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeritabaniBaglantisi {

    // BU SATIRDAKİ integratedSecurity KISMINI SİLDİĞİNDEN EMİN OL!
    // Sadece bunlar kalmalı:
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=OEQAAS_DB;encrypt=true;trustServerCertificate=true;";

    // Şifreyi az önce SQL'de 12345 yaptık, burası da 12345 olmalı.
    private static final String KULLANICI = "sa";
    private static final String SIFRE = "12345";

    public static Connection baglan() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, KULLANICI, SIFRE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("SQL Driver bulunamadı veya Bağlantı hatası: " + e.getMessage());
        }
    }
}