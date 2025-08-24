package com.example.skripsi;

public class NotifikasiModel {
    private String deskripsi;
    private String waktu;

    public NotifikasiModel() {
        // Diperlukan Firebase
    }

    public NotifikasiModel(String deskripsi, String waktu) {
        this.deskripsi = deskripsi;
        this.waktu = waktu;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getWaktu() {
        return waktu;
    }
}
