package com.example.oeqaas.utils;

import org.flywaydb.core.Flyway;

public class DatabaseManager {

    public static void veritabaniGuncelle() {
        try {
            // Configuration for Flyway
            // Make sure your database OEQAAS_DB exists in SQL Server
            String url = "jdbc:sqlserver://localhost;databaseName=OEQAAS_DB;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

            // If using SQL Server Authentication (User/Pass), remove integratedSecurity=true above and fill below:
            String user = "";
            String password = "";

            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, password)
                    .locations("classpath:db/migration") // Looks for V1__...sql in resources/db/migration
                    .load();

            // Run the migration
            flyway.migrate();
            System.out.println("✅ Veritabanı (Flyway) başarıyla güncellendi.");

        } catch (Exception e) {
            System.err.println("❌ Veritabanı güncelleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}