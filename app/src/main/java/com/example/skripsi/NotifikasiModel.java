package com.example.skripsi;

public class NotifikasiModel {
    private String deskripsi;
    private String waktu;
    private String tanggal;

    public NotifikasiModel() {
        // Diperlukan Firebase
    }

    public NotifikasiModel(String deskripsi, String waktu, String tanggal) {
        this.deskripsi = deskripsi;
        this.waktu = waktu;
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getTanggal() {
        return tanggal;
    }
}
