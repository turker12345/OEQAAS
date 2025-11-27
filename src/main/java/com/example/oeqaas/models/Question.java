package com.example.oeqaas.models;

public class Question {
    private String soruMetni;
    private String secenekA;
    private String secenekB;
    private String secenekC;
    private String secenekD;
    private String dogruCevap; // "A", "B", "C", or "D"

    public Question(String soruMetni, String secenekA, String secenekB, String secenekC, String secenekD, String dogruCevap) {
        this.soruMetni = soruMetni;
        this.secenekA = secenekA;
        this.secenekB = secenekB;
        this.secenekC = secenekC;
        this.secenekD = secenekD;
        this.dogruCevap = dogruCevap;
    }

    public String getSoruMetni() { return soruMetni; }
    public String getSecenekA() { return secenekA; }
    public String getSecenekB() { return secenekB; }
    public String getSecenekC() { return secenekC; }
    public String getSecenekD() { return secenekD; }
    public String getDogruCevap() { return dogruCevap; }
}