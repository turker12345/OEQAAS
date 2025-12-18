package com.example.oeqaas.utils;

import com.example.oeqaas.models.Test;
import com.example.oeqaas.models.User;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    public static List<Test> testler = new ArrayList<>();
    public static User aktifKullanici = null;

    static {
        System.out.println("Sistem başlatılıyor...");
        // Testleri JSON dosyasından yükle
        testler = JsonVeriYoneticisi.testleriYukle();
    }
    public static Test secilenTest = null;
    public static void verileriKaydet() {
        JsonVeriYoneticisi.testleriKaydet(testler);
    }
}