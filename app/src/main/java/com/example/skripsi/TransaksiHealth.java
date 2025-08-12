package com.example.skripsi;

public class TransaksiHealth extends Transaksi{
    String nomorPolisKesehatan;

    public TransaksiHealth(String NIK, int besarPremi, int company, String date, String jatuhTempo, String nomorPolisKesehatan) {
        super(NIK, besarPremi, company, date, jatuhTempo);
        this.nomorPolisKesehatan = nomorPolisKesehatan;
    }

    public String getNomorPolisKesehatan() {
        return nomorPolisKesehatan;
    }

    public void setNomorPolisKesehatan(String nomorPolisKesehatan) {
        this.nomorPolisKesehatan = nomorPolisKesehatan;
    }
}
