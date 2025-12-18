package com.example.oeqaas.utils;

import com.example.oeqaas.models.Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonVeriYoneticisi {

    private static final String TEST_DOSYASI = "testler.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static List<Test> testleriYukle() {
        List<Test> testListesi = new ArrayList<>();
        File hariciDosya = new File(TEST_DOSYASI);

        if (hariciDosya.exists()) {
            try (Reader reader = new FileReader(hariciDosya)) {
                Type listType = new TypeToken<ArrayList<Test>>(){}.getType();
                testListesi = gson.fromJson(reader, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (InputStream is = JsonVeriYoneticisi.class.getResourceAsStream("/" + TEST_DOSYASI)) {
                if (is != null) {
                    try (Reader reader = new InputStreamReader(is)) {
                        Type listType = new TypeToken<ArrayList<Test>>(){}.getType();
                        testListesi = gson.fromJson(reader, listType);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (testListesi == null) {
            testListesi = new ArrayList<>();
        }
        return testListesi;
    }

    public static void testleriKaydet(List<Test> testListesi) {
        try (Writer writer = new FileWriter(TEST_DOSYASI)) {
            gson.toJson(testListesi, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}