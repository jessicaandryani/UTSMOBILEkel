package com.jess.utsmobile;

public class TransaksiModel {
    private int id;
    private String tanggal;
    private String kategori;
    private double jumlah;
    private String jenis; // Pemasukan atau Pengeluaran

    // Konstruktor yang sudah diperbaiki
    public TransaksiModel(int id, String tanggal, String kategori, double jumlah, String jenis) {
        this.id = id;
        this.tanggal = tanggal;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.jenis = jenis;
    }

    // Getter yang sudah diperbaiki
    public int getId() {
        return id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getKategori() {
        return kategori;
    }

    public double getJumlah() {
        return jumlah;
    }

    public String getJenis() {
        return jenis;
    }
}