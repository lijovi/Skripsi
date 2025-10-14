package com.example.skripsi;

public class NotifikasiModel {
    private String deskripsi;
    private String waktu;
    private String tanggal;
    private String jenis;
    private String asuransi;

    public NotifikasiModel() {
        // Diperlukan Firebase
    }

    public NotifikasiModel(String deskripsi, String waktu, String tanggal, String jenis, String asuransi) {
        this.deskripsi = deskripsi;
        this.waktu = waktu;
        this.tanggal = tanggal;
        this.jenis = jenis;
        this.asuransi = asuransi;
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

    public String getJenis() {
        return jenis;
    }

    public String getAsuransi() {
        return asuransi;
    }
}
