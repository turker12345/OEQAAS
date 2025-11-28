package com.example.oeqaas.utils;

import com.example.oeqaas.models.Question;
import com.example.oeqaas.models.Quiz; // Ensure this imports your Quiz model
import com.example.oeqaas.models.User;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    // Shared Memory Lists
    public static List<User> kullanicilar = new ArrayList<>();
    public static List<Quiz> quizler = new ArrayList<>();

    // Initialize with some dummy data
    static {
        // Admin
        kullanicilar.add(new User("admin", "admin@email.com", "123", "555-0000"));

        // Sample Student
        User ogrenci = new User("Ahmet Yilmaz", "ahmet@mail.com", "123", "555-1234");
        // Check if skorEkle exists in User model, if not, remove this line or add the method
        // ogrenci.skorEkle("Java Testi: 2 Doğru");
        kullanicilar.add(ogrenci);

        // Sample Quiz
        Quiz javaQuiz = new Quiz("Java Temelleri");
        javaQuiz.soruEkle(new Question("Java'da hangisi döngüdür?", "if", "for", "int", "class", "B"));
        javaQuiz.soruEkle(new Question("int kaç bittir?", "8", "16", "32", "64", "C"));
        quizler.add(javaQuiz);
    }
}