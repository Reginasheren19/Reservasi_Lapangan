package com.example.reservasi_lapangan;

import java.text.NumberFormat;
import java.util.Locale;

public class Lapangan {
    private int id;
    private String nama_lapangan;
    private String lokasi;
    private double harga;
    private String deskripsi;
    private String gambar;

    // Constructor
    public Lapangan(int id, String nama_lapangan, String lokasi, double harga, String deskripsi, String gambar) {
        this.id = id;
        this.nama_lapangan = nama_lapangan;
        this.lokasi = lokasi;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaLapangan() {
        return nama_lapangan;
    }

    public void setNamaLapangan(String namaLapangan) {
        this.nama_lapangan = namaLapangan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getHarga() {
        // Format harga menjadi "Rp xxx.xxx,00" dengan 2 desimal
        NumberFormat format = NumberFormat.getInstance(new Locale("id", "ID"));
        format.setMinimumFractionDigits(2);  // Menjaga 2 digit desimal
        format.setMaximumFractionDigits(2);  // Membatasi 2 digit desimal
        return format.format(harga); // Mengembalikan harga dalam format yang sesuai
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
