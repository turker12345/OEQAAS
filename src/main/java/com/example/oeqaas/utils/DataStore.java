package com.example.oeqaas.utils;

import com.example.oeqaas.models.Question;
import com.example.oeqaas.models.Test; // CHANGED: Imported Test instead of Quiz
import com.example.oeqaas.models.User;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    // Shared Memory Lists
    public static List<User> kullanicilar = new ArrayList<>();

    // FIXED: Changed List<Quiz> to List<Test> to match your model
    public static List<Test> testler = new ArrayList<>();

    // Initialize with some dummy data
    static {
        // Admin
        kullanicilar.add(new User("admin", "admin@email.com", "123", "555-0000"));

        // Sample Student
        User ogrenci = new User("Yunus AKAY", "yunusakay44@mail.com", "123", "555-1234");
        kullanicilar.add(ogrenci);

        Test javaTest = new Test("Java Temelleri");
        javaTest.soruEkle(new Question("Java'da hangisi döngüdür?", "if", "for", "int", "class", "B"));
        javaTest.soruEkle(new Question("int kaç bittir?", "8", "16", "32", "64", "C"));
        testler.add(javaTest); // Now adding a Test object to a List<Test>
    }
}