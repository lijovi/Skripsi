package com.example.skripsi;

public class DataPembayaran {
    String nik;
    String nama;
    String besarPremi;
    String time;
    String nomorPolis;
    String date;

    public DataPembayaran(){

    }

    public DataPembayaran(String nik, String nama, String besarPremi, String time, String nomorPolis, String date) {
        this.nik = nik;
        this.nama = nama;
        this.besarPremi = besarPremi;
        this.time = time;
        this.nomorPolis = nomorPolis;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNomorPremi() {
        return nomorPolis;
    }

    public void setNomorPremi(String nomorPolis) {
        this.nomorPolis = nomorPolis;
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
