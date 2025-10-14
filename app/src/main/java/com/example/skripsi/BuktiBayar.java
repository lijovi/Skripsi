package com.example.skripsi;

public class BuktiBayar {
    String nik;
    String nama;
    String besarPremi;
    String nomorPolis;
    String time;
    String tanggal;

    public BuktiBayar(String nik, String nama, String besarPremi, String nomorPolis, String time, String tanggal) {
        this.nik = nik;
        this.nama = nama;
        this.besarPremi = besarPremi;
        this.nomorPolis = nomorPolis;
        this.time = time;
        this.tanggal = tanggal;
    }

    public BuktiBayar(){

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

    public String getNomorPolis() {
        return nomorPolis;
    }

    public void setNomorPolis(String nomorPolis) {
        this.nomorPolis = nomorPolis;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
