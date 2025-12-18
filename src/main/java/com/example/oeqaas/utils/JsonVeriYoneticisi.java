package com.example.oeqaas.utils;

import com.example.oeqaas.models.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class JsonVeriYoneticisi {

    // Artık tek bir dosya değil, bir klasör yolu belirtiyoruz
    private static final String KLASOR_YOLU = "tests";

    // Gson nesnesini oluşturuyoruz (PrettyPrinting okunaklı yazar)
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<Test> testleriYukle() {
        List<Test> testListesi = new ArrayList<>();
        File klasor = new File(KLASOR_YOLU);

        // Klasör yoksa oluştur
        if (!klasor.exists()) {
            klasor.mkdir();
            System.out.println("Bilgi: 'tests' klasörü oluşturuldu.");
            return testListesi;
        }

        // Klasördeki .json ile biten dosyaları al
        File[] dosyalar = klasor.listFiles((dir, name) -> name.endsWith(".json"));

        if (dosyalar != null) {
            // Dosyaları numaralarına göre sıralayalım (1.json, 2.json, 10.json sırasıyla gelsin)
            Arrays.sort(dosyalar, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    try {
                        int n1 = Integer.parseInt(f1.getName().replace(".json", ""));
                        int n2 = Integer.parseInt(f2.getName().replace(".json", ""));
                        return Integer.compare(n1, n2);
                    } catch (NumberFormatException e) {
                        return f1.getName().compareTo(f2.getName());
                    }
                }
            });

            // Her bir dosyayı tek tek oku
            for (File dosya : dosyalar) {
                try (Reader reader = new FileReader(dosya)) {
                    // Dosya içeriğini Test objesine çevir
                    Test test = gson.fromJson(reader, Test.class);
                    if (test != null) {
                        testListesi.add(test);
                    }
                } catch (IOException e) {
                    System.err.println("Hata: " + dosya.getName() + " okunamadı.");
                    e.printStackTrace();
                }
            }
            System.out.println("Toplam " + testListesi.size() + " test yüklendi.");
        }

        return testListesi;
    }

    public static void testleriKaydet(List<Test> testListesi) {
        File klasor = new File(KLASOR_YOLU);
        if (!klasor.exists()) {
            klasor.mkdir();
        }

        // Kaydetmeden önce eski dosyaları temizleyelim (Silinmiş testler kalmasın diye)
        // Ancak dikkatli ol, bu işlem tests klasöründeki tüm jsonları silip yeniden yazar.
        File[] eskiDosyalar = klasor.listFiles((dir, name) -> name.endsWith(".json"));
        if (eskiDosyalar != null) {
            for (File f : eskiDosyalar) {
                f.delete();
            }
        }

        // Listeyi tek tek dosyalar halinde kaydet
        for (int i = 0; i < testListesi.size(); i++) {
            // Dosya adı: 1.json, 2.json ...
            String dosyaAdi = (i + 1) + ".json";
            File dosya = new File(klasor, dosyaAdi);

            try (Writer writer = new FileWriter(dosya)) {
                gson.toJson(testListesi.get(i), writer);
                // System.out.println(dosyaAdi + " kaydedildi.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}