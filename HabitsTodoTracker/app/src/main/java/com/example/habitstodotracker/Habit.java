package com.example.habitstodotracker;



public class Habit {
    private String nama;
    private String Kategori;
    private Long second;
    private String prioritas;
    private String start;
    private String UserId;

    public Habit() {
    }

    public Habit(String nama, String kategori, Long second, String prioritas, String start, String userId) {
        this.nama = nama;
        Kategori = kategori;
        this.second = second;
        this.prioritas = prioritas;
        this.start = start;
        UserId = userId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String kategori) {
        Kategori = kategori;
    }

    public Long getSecond() {
        return second;
    }

    public void setSecond(Long second) {
        this.second = second;
    }

    public String getPrioritas() {
        return prioritas;
    }

    public void setPrioritas(String prioritas) {
        this.prioritas = prioritas;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

}
