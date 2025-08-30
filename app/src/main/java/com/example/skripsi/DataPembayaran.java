package com.example.skripsi;

public class DataPembayaran {
    String nik;
    String nama;
    String besarPremi;
    String time;
    String nomorPremi;

    public DataPembayaran(String nik, String nama, String besarPremi, String time, String nomorPremi) {
        this.nik = nik;
        this.nama = nama;
        this.besarPremi = besarPremi;
        this.time = time;
        this.nomorPremi = nomorPremi;
    }

    public String getNomorPremi() {
        return nomorPremi;
    }

    public void setNomorPremi(String nomorPremi) {
        this.nomorPremi = nomorPremi;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBesarPremi() {
        return besarPremi;
    }

    public void setBesarPremi(String besarPremi) {
        this.besarPremi = besarPremi;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
