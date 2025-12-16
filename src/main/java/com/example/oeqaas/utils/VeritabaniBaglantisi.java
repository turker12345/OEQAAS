package com.example.oeqaas.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeritabaniBaglantisi {

    // Veritabanı ismini (OEQAAS_DB veya OEQAAS) diğer dosyayla aynı yapmayı unutma!
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=OEQAAS;encrypt=true;trustServerCertificate=true;";

    private static final String KULLANICI = "sa";
    private static final String SIFRE = "12345";

    public static Connection baglan() throws SQLException {
        try {
            // Sürücüyü manuel yükleyelim, bazen otomatik bulamayabilir.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, KULLANICI, SIFRE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("SQL Driver bulunamadı: " + e.getMessage());
        }
    }
}