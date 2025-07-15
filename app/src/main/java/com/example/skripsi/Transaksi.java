package com.example.skripsi;

public class Transaksi {
    String NIK;
    String besarPremi;
    String nomorPolisTravel;
    String nomorPolisKesehatan;
    String company;
    String date;
    String jatuhTempo;

    public Transaksi(String NIK, String besarPremi, String nomorPolisTravel, String nomorPolisKesehatan, String company, String date, String jatuhTempo) {
        this.NIK = NIK;
        this.besarPremi = besarPremi;
        this.nomorPolisTravel = nomorPolisTravel;
        this.nomorPolisKesehatan = nomorPolisKesehatan;
        this.company = company;
        this.date = date;
        this.jatuhTempo = jatuhTempo;
    }

    public String getJatuhTempo() {
        return jatuhTempo;
    }

    public void setJatuhTempo(String jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public String getBesarPremi() {
        return besarPremi;
    }

    public void setBesarPremi(String besarPremi) {
        this.besarPremi = besarPremi;
    }

    public String getNomorPolisTravel() {
        return nomorPolisTravel;
    }

    public void setNomorPolisTravel(String nomorPolisTravel) {
        this.nomorPolisTravel = nomorPolisTravel;
    }

    public String getNomorPolisKesehatan() {
        return nomorPolisKesehatan;
    }

    public void setNomorPolisKesehatan(String nomorPolisKesehatan) {
        this.nomorPolisKesehatan = nomorPolisKesehatan;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
