package com.example.oeqaas.utils;

import com.example.oeqaas.models.Test;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonVeriYoneticisi {

    private static final String TEST_DOSYASI = "testler.json";

    //private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public static List<Test> testleriYukle() {
        List<Test> testListesi = new ArrayList<>();
        File dosya = new File(TEST_DOSYASI);

        if (!dosya.exists()) {
            System.out.println("Bilgi: " + TEST_DOSYASI + " bulunamadı. Yeni oluşturulacak.");
            return testListesi;
        }

        try (Reader reader = new FileReader(TEST_DOSYASI)) {

//Type listType = new TypeToken<ArrayList<Test>>(){}.getType();
//testListesi = gson.fromJson(reader, listType);


            if (testListesi == null) {
                testListesi = new ArrayList<>();
            }
            System.out.println("Testler başarıyla yüklendi. Toplam Test: " + testListesi.size());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Hata: Dosya okunurken sorun oluştu.");
        }
        return testListesi;
    }



    public static void testleriKaydet(List<Test> testListesi) {
        try (Writer writer = new FileWriter(TEST_DOSYASI)) {
            //gson.toJson(testListesi, writer);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Hata: Dosya yazılırken sorun oluştu.");
        }
    }
}