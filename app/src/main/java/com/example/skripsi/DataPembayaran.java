package com.example.skripsi;

public class DataPembayaran {
    String nik;
    String nama;
    String besarPremi;
    String status;
    String nomorPolis;
    String date;

    public DataPembayaran(){

    }

    public DataPembayaran(String nik, String nama, String besarPremi, String status, String nomorPolis, String date) {
        this.nik = nik;
        this.nama = nama;
        this.besarPremi = besarPremi;
        this.status = status;
        this.nomorPolis = nomorPolis;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNomorPolis() {
        return nomorPolis;
    }

    public void setNomorPolis(String nomorPolis) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
