package com.example.skripsi;

public class TransaksiTravel {
    String NIK;
    int besarPremi;
    String nomorPolisTravel;
    int company;
    String date;
    String jatuhTempo;

    public TransaksiTravel(String NIK, int besarPremi, String nomorPolisTravel, int company, String date, String jatuhTempo) {
        this.NIK = NIK;
        this.besarPremi = besarPremi;
        this.nomorPolisTravel = nomorPolisTravel;
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

    public String getNomorPolisTravel() {
        return nomorPolisTravel;
    }

    public void setNomorPolisTravel(String nomorPolisTravel) {
        this.nomorPolisTravel = nomorPolisTravel;
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

