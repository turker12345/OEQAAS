package com.example.oeqaas.utils;

import org.flywaydb.core.Flyway;

public class DatabaseManager {

    public static void veritabaniGuncelle() {
        try {
            System.out.println("Veritabanı bağlantısı başlatılıyor (Windows Auth)...");

            // Option 2: Try Windows Authentication (Integrated Security)
            // This connects using the Windows user 'yunus' automatically without a password in the string.
            // Ensure 'COYA' is the correct server name. If it fails, try 'localhost' or 'localhost\\SQLEXPRESS'
            String url = "jdbc:sqlserver://COYA;databaseName=OEQAAS;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";

            // Configure Flyway without explicit user/pass
            Flyway flyway = Flyway.configure()
                    .dataSource(url, null, null)
                    .locations("classpath:db")
                    .baselineOnMigrate(true)
                    .load();

            // Run Migration
            flyway.migrate();
            System.out.println("✅ Veritabanı başarıyla senkronize edildi (Flyway/Windows Auth).");

        } catch (Exception e) {
            System.err.println("❌ KRİTİK HATA: Veritabanına bağlanılamadı!");
            System.err.println("Hata Detayı: " + e.getMessage());

            // Debugging hint
            if (e.getMessage().contains("integratedSecurity")) {
                System.err.println("İPUCU: 'mssql-jdbc_auth.dll' dosyası eksik olabilir. Lütfen SQL Server Authentication moduna geçin veya DLL'i ekleyin.");
            }
        }
    }
}