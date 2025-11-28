package com.example.oeqaas.models;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private String ad; // Quiz Name (e.g., "Java Test 1")
    private List<Question> sorular;

    public Test(String ad) {
        this.ad = ad;
        this.sorular = new ArrayList<>();
    }

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }

    public List<Question> getSorular() { return sorular; }

    public void soruEkle(Question q) {
        sorular.add(q);
    }

    @Override
    public String toString() {
        return ad + " (" + sorular.size() + " Soru)";
    }
}