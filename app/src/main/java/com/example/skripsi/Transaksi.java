package com.example.skripsi;

public class Transaksi {
    String NIK;
    int besarPremi;
    int company;
    String date;
    String jatuhTempo;

    public Transaksi(String NIK, int besarPremi, int company, String date, String jatuhTempo) {
        this.NIK = NIK;
        this.besarPremi = besarPremi;
        this.company = company;
        this.date = date;
        this.jatuhTempo = jatuhTempo;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public int getBesarPremi() {
        return besarPremi;
    }

    public void setBesarPremi(int besarPremi) {
        this.besarPremi = besarPremi;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJatuhTempo() {
        return jatuhTempo;
    }

    public void setJatuhTempo(String jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }
}
