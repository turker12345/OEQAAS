package com.example.oeqaas.utils;

import org.flywaydb.core.Flyway;

public class DatabaseManager {

    public static void veritabaniGuncelle() {
        try {
            // BURAYI DÜZELTİYORUZ:
            // databaseName kısmının SSMS'teki isminle AYNI olduğundan emin ol (OEQAAS veya OEQAAS_DB)
            String url = "jdbc:sqlserver://localhost:1433;databaseName=OEQAAS;encrypt=true;trustServerCertificate=true;";

            // BURASI ÇOK ÖNEMLİ:
            // Buraya "Admin" YAZMA. Burası SQL Server'ın ana yetkilisidir.
            // Genelde "sa" olur. Şifresi de SQL Server kurarken belirlediğin şifredir (örn: 12345).
            String user = "sa";
            String password = "12345"; // Buraya SQL Server 'sa' şifreni yaz.

            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, password)
                    .locations("classpath:db")
                    .load();

            flyway.migrate();
            System.out.println("✅ Veritabanı (Flyway) başarıyla güncellendi.");

        } catch (Exception e) {
            System.err.println("❌ Veritabanı güncelleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}