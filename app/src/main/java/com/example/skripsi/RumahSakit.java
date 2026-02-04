package com.example.skripsi;

public class RumahSakit {
    int company;
    String nama;
    String nomorTelp1;
    String nomorTelp2;
    String linkMap;

    public RumahSakit(){

    }

    public RumahSakit(int company, String nama, String nomorTelp1, String nomorTelp2, String linkMap) {
        this.company = company;
        this.nama = nama;
        this.nomorTelp1 = nomorTelp1;
        this.nomorTelp2 = nomorTelp2;
        this.linkMap = linkMap;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomorTelp1() {
        return nomorTelp1;
    }

    public void setNomorTelp1(String nomorTelp1) {
        this.nomorTelp1 = nomorTelp1;
    }

    public String getNomorTelp2() {
        return nomorTelp2;
    }

    public void setNomorTelp2(String nomorTelp2) {
        this.nomorTelp2 = nomorTelp2;
    }

    public String getLinkMap() {
        return linkMap;
    }

    public void setLinkMap(String linkMap) {
        this.linkMap = linkMap;
    }
}
